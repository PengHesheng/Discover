package com.example.a14512.discover.modules.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.mode.entity.Scenic;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.mapapi.overlayutil.BikingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.DrivingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.TransitRouteOverlay;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/3
 */

public class GoGuideActivity extends BaseActivity implements View.OnClickListener {

    private Button pre;
    private Button back;
    private Button next;
    private Button mStart;
    private Button mAllRoute;
    private RoutePlanSearch mSearch;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private ArrayList<PlanNode> mPlanNodes;
    private ArrayList<Scenic> mScenics;
    private ArrayList<LatLng> mLatLngs;
    private ArrayList<Integer> mTimes = new ArrayList<>();
    private int position = 1, type, location = 0, sumDistance = 0;
    //导航
    private BikeNavigateHelper mNaviHelper;
    BikeNaviLaunchParam param;
    private LatLng startPt, locationPt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_guide);
        getData();
        initView();
        getLocation();
        searchResult();
        guideInit();
    }

    private void getLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        LocationUtil locationUtil = LocationUtil.getInstance();
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                locationPt = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                LatLng latLng = SpatialRelationUtil.getNearestPointFromLine(mLatLngs, locationPt);
                location = mLatLngs.indexOf(latLng);
            }
        };
        locationUtil.getLocation(this, listener);
        if (locationPt != null) {
            locationUtil.unRegisterListener(listener);
        }
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

        switch (type) {
            case 1:
                routePlanBus();
                mStart.setEnabled(false);
                break;
            case 2:
                routePlanCar();
                mStart.setEnabled(false);
                break;
            case 3:
                routePlanBike();
                break;
            default:
                break;
        }
    }

    private void initView() {
        pre = findViewById(R.id.btn_pre_scenic);
        back = findViewById(R.id.btn_back_home);
        next = findViewById(R.id.btn_next_scenic);
        mAllRoute = findViewById(R.id.btn_all_route);
        Button finish = findViewById(R.id.btn_finish_route);
        mStart = findViewById(R.id.btn_start_guide);
        mMapView = findViewById(R.id.texture_map_go_guide);
        mBaiduMap = mMapView.getMap();

        pre.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        finish.setOnClickListener(this);
        mAllRoute.setOnClickListener(this);
        mStart.setOnClickListener(this);
    }

    /**
     * 导航初始化
     */
    private void guideInit() {

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
//                intent.setClass(MapActivity.this, MapGuideActivity.class);
//                startActivity(intent);
                startIntentActivity(GoGuideActivity.this, MapGuideActivity.class);
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
            case R.id.btn_pre_scenic:
                if (position > 0) {
                    position--;
                    navigateTo();
                } else {
                    ToastUtil.show(this, "已经到头了！");
                }
                break;
            case R.id.btn_back_home:
                position = 0;
                navigateTo();
                break;
            case R.id.btn_next_scenic:
                if (position < mLatLngs.size() - 1) {
                    position++;
                    navigateTo();
                } else {
                    ToastUtil.show(this, "已经到头了！");
                }
                break;
            case R.id.btn_start_guide:
                startBikeNavi();
                break;
            case R.id.btn_finish_route:
                startIntentActivity(this, MainActivity.class);
                break;
            case R.id.btn_all_route:
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
}
