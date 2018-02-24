package com.example.a14512.discover.modules.routeplan.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.mapapi.overlayutil.BikingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.DrivingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.TransitRouteOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 14512 on 2018/2/3
 */

public class GoGuideActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mLeft;
    private Toolbar toolbar;
    private LinearLayout mGoGuide;

    private RoutePlanSearch mSearch;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationUtil locationUtil;
    private BDAbstractLocationListener listener;
    private ArrayList<PlanNode> mPlanNodes;
    private ArrayList<Scenic> mScenics;
    private ArrayList<LatLng> mLatLngs;
    private ArrayList<Integer> mTimes = new ArrayList<>();
    private int position = 1, type, location = 0, sumDistance = 0;
    //骑行导航
    private BikeNavigateHelper mNaviHelper;
    BikeNaviLaunchParam param;
    private LatLng startPt, locationPt;

    //导航
    public static List<Activity> activityList = new LinkedList<>();

    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    private static final String APP_FOLDER_NAME = "Discover";

    private String mSDCardPath = null;

    private final static String authBaseArr[] =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION };
    private final static String authComArr[] = { Manifest.permission.READ_PHONE_STATE };
    private final static int authBaseRequestCode = 1;
    private final static int authComRequestCode = 2;

    private boolean hasInitSuccess = false;
    private boolean hasRequestComAuth = false;
    String authinfo = null;

    private BNRoutePlanNode.CoordinateType mCoordinateType = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_guide);
        getData();
        initView();
        initToolbar();
        getLocation();
        searchResult();
        guideInit();
        bikeGuideInit();
    }

    private void guideInit() {
        activityList.add(this);
        BNOuterLogUtil.setLogSwitcher(true);
        if (initDirs()) {
            initNavi();
        }
    }

    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                GoGuideActivity.this.runOnUiThread(() -> ToastUtil.show(GoGuideActivity.this, authinfo));
            }

            @Override
            public void initSuccess() {
                ToastUtil.show(GoGuideActivity.this, "百度导航引擎初始化成功");
                hasInitSuccess = true;
                initSetting();
            }

            @Override
            public void initStart() {
                ToastUtil.show(GoGuideActivity.this, "百度导航引擎初始化开始");
            }

            @Override
            public void initFailed() {
                ToastUtil.show(GoGuideActivity.this, "百度导航引擎初始化失败");
            }

        }, null, null, null);

    }

    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9354030");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    private boolean hasBasePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            Toast.makeText(this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        List<BNRoutePlanNode> list = new ArrayList<>();
        switch (coType) {
            case GCJ02: {
                break;
            }
            case WGS84: {
                break;
            }
            case BD09_MC: {
                break;
            }
            case BD09LL: {
                list = initNode(coType);
                break;
            }
            default:
                break;
        }

        if (list != null) {
            // 开发者可以使用旧的算路接口，也可以使用新的算路接口,可以接收诱导信息等
            // BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1,
                    true, new DemoRoutePlanListener(list.get(0)),
                    eventListerner);
        }
    }

    private List<BNRoutePlanNode> initNode(BNRoutePlanNode.CoordinateType coType) {
        List<BNRoutePlanNode> planNodes = new ArrayList<>();
        for (Scenic scenic : mScenics) {
            BNRoutePlanNode node = new BNRoutePlanNode(scenic.longitude, scenic.latitude,
                    scenic.name, null, coType);
            planNodes.add(node);
        }
        return planNodes;
    }

    BaiduNaviManager.NavEventListener eventListerner = new BaiduNaviManager.NavEventListener() {

        @Override
        public void onCommonEventCall(int what, int arg1, int arg2, Bundle bundle) {

        }
    };

    private boolean hasCompletePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
             */

            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("NavGuideActivity")) {
                    return;
                }
            }
            Intent intent = new Intent(GoGuideActivity.this, NavGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(GoGuideActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(null);
        mLeft.setOnClickListener(v -> finish());
    }

    private void getLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        locationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                locationPt = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                LatLng latLng = SpatialRelationUtil.getNearestPointFromLine(mLatLngs, locationPt);
                location = mLatLngs.indexOf(latLng);
                locationUtil.unRegisterListener(listener);

                switch (type) {
                    case 1:
                        routePlanBus();
                        break;
                    case 2:
                        routePlanCar();
                        mGoGuide.setOnClickListener(v -> {
                            if (BaiduNaviManager.isNaviInited()) {
                                PLog.e("inited");
                                routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL);
                            }
                        });
                        break;
                    case 3:
                        routePlanBike();
                        mGoGuide.setOnClickListener(v -> startBikeNavi());
                        break;
                    default:
                        break;
                }
            }
        };
        locationUtil.getLocation(this, listener);
    }

    private void getData() {
        mPlanNodes = new ArrayList<>();
        mLatLngs = new ArrayList<>();
        mScenics = (ArrayList<Scenic>) getIntent().getBundleExtra(C.SCENIC_DETAIL).getSerializable(C.SCENIC_DETAIL);
        type = getIntent().getIntExtra("type", 1);
        for (Scenic scenic : mScenics) {
            LatLng latLng = new LatLng(scenic.latitude, scenic.longitude);
            mPlanNodes.add(PlanNode.withLocation(latLng));
            mLatLngs.add(latLng);
        }
    }

    /**
     * 路线规划结果回调
     */
    private void searchResult() {
        mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener() {

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult result) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                //在公交线路规划回调方法中添加TransitRouteOverlay用于展示换乘信息
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteLine route = result.getRouteLines().get(0);
                    mTimes.add(route.getDuration());
                    sumDistance += route.getDistance();
                    TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                    //设置公交路线规划数据
                    overlay.setData(route);
                    //将公交路线规划覆盖物添加到地图中
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
                //获取跨城综合公共交通线路规划结果

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteLine route = result.getRouteLines().get(0);
                    mTimes.add(route.getDuration());
                    sumDistance += route.getDistance();
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    overlay.setData(route);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    PLog.e("bike result1" + result.error);
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    BikingRouteLine route = result.getRouteLines().get(0);
                    mTimes.add(route.getDuration());
                    sumDistance += route.getDistance();
                    BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                    overlay.setData(route);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }
        };

        mSearch.setOnGetRoutePlanResultListener(routeListener);
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        toolbar = findViewById(R.id.toolbar);
        ImageView pre = findViewById(R.id.img_pre_scenic);
        ImageView next = findViewById(R.id.img_next_scenic);
        LinearLayout allRoute = findViewById(R.id.layout_all_route);
        mGoGuide = findViewById(R.id.layout_go_guide);
        LinearLayout finish = findViewById(R.id.layout_finish);
        mMapView = findViewById(R.id.texture_map_go_guide);
        mBaiduMap = mMapView.getMap();
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        allRoute.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    /**
     * 导航初始化
     */
    private void bikeGuideInit() {
        try {
            mNaviHelper = BikeNavigateHelper.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        navigateTo();
    }

    /**
     * 开始骑行导航
     */
    private void startBikeNavi() {
        Log.d("View", "startBikeNavi");
        try {
            mNaviHelper.initNaviEngine(this, new IBEngineInitListener() {
                @Override
                public void engineInitSuccess() {
                    Log.d("View", "engineInitSuccess");
                    routePlanWithParam();
                }

                @Override
                public void engineInitFail() {
                    Log.d("View", "engineInitFail");
                }
            });
        } catch (Exception e) {
            Log.d("Exception", "startBikeNavi");
            e.printStackTrace();
        }
    }

    /**
     * 导航回调接口，进入诱导界面
     */
    private void routePlanWithParam() {
        mNaviHelper.routePlanWithParams(param, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d("View", "onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d("View", "onRoutePlanSuccess");
//                Intent intent = new Intent();
//                intent.setClass(MapActivity.this, BikeGuideActivity.class);
//                startActivity(intent);
                startIntentActivity(GoGuideActivity.this, BikeGuideActivity.class);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d("View", "onRoutePlanFail");
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pre_scenic:
                if (position > 0) {
                    position--;
                    navigateTo();
                } else {
                    ToastUtil.show(this, "已经到头了！");
                }
                break;
//            case R.id.btn_back_home:
//                position = 0;
//                navigateTo();
//                break;
            case R.id.img_next_scenic:
                if (position < mLatLngs.size() - 1) {
                    position++;
                    navigateTo();
                } else {
                    ToastUtil.show(this, "已经到头了！");
                }
                break;
            case R.id.layout_finish:
                startIntentActivity(this, CommentScoreActivity.class);
                break;
            case R.id.layout_all_route:
                startActivity();
                break;
            default:
                break;
        }
    }

    private void startActivity() {
        Intent intent = new Intent(this, AllRouteActivity.class);
        intent.putExtra("sum_distance", sumDistance);
        intent.putIntegerArrayListExtra("time", mTimes);
        Bundle bundle = new Bundle();
        bundle.putSerializable(C.SCENIC_DETAIL, mScenics);
        intent.putExtra(C.SCENIC_DETAIL, bundle);
        intent.putExtra("location", location);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 地图移动到指定的位置
     */
    private void navigateTo() {
        LatLng chooseScenic = mLatLngs.get(position);
        MapStatus mapStatus = new MapStatus.Builder()
                .target(chooseScenic)
                .zoom(15)
                .build();
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(update);
        startPt = new LatLng(40.047416, 116.312143);

        param = new BikeNaviLaunchParam().stPt(mLatLngs.get(0)).endPt(chooseScenic);
    }

    /**
     * 骑行路线规划和展示
     */
    private void routePlanBike() {
        BikingRoutePlanOption planOption = new BikingRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size() - 1; i++) {
            mSearch.bikingSearch(planOption.from(mPlanNodes.get(i)).to(mPlanNodes.get(i + 1)));
        }
//        mSearch.bikingSearch(planOption.from(mPlanNodes.get(0)).to(mPlanNodes.get(1)));
    }

    /**
     * 自驾路线规划和展示
     */
    private void routePlanCar() {
        DrivingRoutePlanOption planOption = new DrivingRoutePlanOption();
        ArrayList<PlanNode> planNodes = new ArrayList<>();
        for (int i = 1; i < mPlanNodes.size() - 1; i++) {
            planNodes.add(mPlanNodes.get(i));
        }
        mSearch.drivingSearch(planOption.from(mPlanNodes.get(0)).to(mPlanNodes.get(mPlanNodes.size() - 1)));
    }

    /**
     * 公交路线规划和展示
     */
    private void routePlanBus() {
        TransitRoutePlanOption planOption = new TransitRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size() - 1; i++) {
            mSearch.transitSearch(planOption.from(mPlanNodes.get(i)).city("重庆市").to(mPlanNodes.get(i + 1)));
        }
//        mSearch.transitSearch(planOption.from(mPlanNodes.get(mPlanNodes.size() - 1)).to(mPlanNodes.get(0)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi(mCoordinateType);
        }

    }
}
