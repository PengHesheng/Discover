package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
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
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.ExpandableListAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.mapapi.overlayutil.BikingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.DrivingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.TransitRouteOverlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 14512 on 2018/1/29
 */

public class MapActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MapActivity";

    private TextView mPlace;
    private RoutePlanSearch mSearch;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationUtil locationUtil;
    private BDAbstractLocationListener listener;
    //路线规划
    private ArrayList<BikingRouteOverlay> mBikeOverlays = new ArrayList<>();
    private ArrayList<TransitRouteOverlay> mTransitOverlays = new ArrayList<>();
    private ArrayList<DrivingRouteOverlay> mDriveOverlays = new ArrayList<>();

    private ArrayList<Scenic> mScenics;
    private ArrayList<PlanNode> mPlanNodes;

    private PopupWindow mPopupWindow;
    private ExpandableListView mListView;
    private Map<String, List<TransitRouteLine.TransitStep>> mBusMap;
    private List<String> mNodes;
    private ExpandableListAdapter adapter;
    private int position = 0, type = 1;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        getData();
        getLocation();
        searchResult();
        popupWindow();
    }

    private void getLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        locationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

                PLog.e(location.getStreet());
                //获取城市
                city = location.getCity();
                toastError(location.getLocType());

                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                MyLocationConfiguration config = new MyLocationConfiguration(null, true, null);
                mBaiduMap.setMyLocationConfiguration(config);
                locationUtil.unRegisterListener(listener);
            }
        };
        locationUtil.getLocation(this, listener);
    }

    private void toastError(int locType) {
        switch (locType) {
            case 62:
                ToastUtil.show("定位失败，请检查运营商网络或者WiFi网络是否正常开启!");
                break;
            case 63:
                ToastUtil.show("网络异常，请确认当前测试手机网络是否通畅!");
                break;
            case 167:
                ToastUtil.show("定位失败，请您检查是否禁用获取位置信息权限!");
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        mNodes = new ArrayList<>();
        mPlanNodes = new ArrayList<>();
        mScenics = (ArrayList<Scenic>) getIntent()
                .getBundleExtra(C.SCENIC_DETAIL).getSerializable(C.SCENIC_DETAIL);

        if (mScenics != null) {
            mPlace.setText(mScenics.get(0).name + "——" + mScenics.get(mScenics.size() - 1).name);
            for (Scenic scenic : mScenics) {
                mNodes.add(scenic.name);
                PlanNode planNode = PlanNode.withLocation(new LatLng(scenic.latitude, scenic.longitude));
                mPlanNodes.add(planNode);
            }
        }
    }


    private void searchResult() {
        mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener(){

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
                    //创建公交路线规划线路覆盖物
                    List<TransitRouteLine.TransitStep> steps = route.getAllStep();
                    mBusMap.put(mNodes.get(position), steps);
                    position++;
                    if (position == mNodes.size() - 1) {
                        mBusMap.put(mNodes.get(position), new ArrayList<>());
                        adapter = new ExpandableListAdapter(MapActivity.this, mBusMap, mNodes);
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                    mTransitOverlays.add(overlay);
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
                    List<DrivingRouteLine.DrivingStep> steps = route.getAllStep();
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    mDriveOverlays.add(overlay);
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
                    List<BikingRouteLine.BikingStep> steps = route.getAllStep();
                    BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                    mBikeOverlays.add(overlay);
                    overlay.setData(route);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }
        };

        mSearch.setOnGetRoutePlanResultListener(routeListener);
    }

    private void initView() {
        mPlace = findViewById(R.id.tv_show_place);
        mMapView = findViewById(R.id.texture_map);
        Button btnConfirm = findViewById(R.id.btn_confirm_plan);
        mBaiduMap = mMapView.getMap();

        setStatusBarColor(R.color.mainToolbar);
        btnConfirm.setOnClickListener(this);
        mPlace.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_plan:
                Intent intent = new Intent(this, GoGuideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.SCENIC_DETAIL, mScenics);
                intent.putExtra(C.SCENIC_DETAIL, bundle);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.tv_show_place:
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_bus:
                changeRoute(1);
                routePlanBus();
                break;
            case R.id.btn_car:
                changeRoute(2);
                routePlanCar();
                mPopupWindow.dismiss();
                break;
            case R.id.btn_bike:
                changeRoute(3);
                routePlanBike();
                mPopupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void changeRoute(int type) {
        this.type = type;
        switch (type) {
            case 1:
                clearCar();
                clearBike();
                break;
            case 2:
                clearBus();
                clearBike();
                break;
            case 3:
                clearBus();
                clearCar();
                break;
            default:
                break;
        }
    }

    private void clearBus() {
        for (TransitRouteOverlay overlay : mTransitOverlays) {
            overlay.removeFromMap();
        }
        mTransitOverlays.clear();
    }

    private void clearBike() {
        for (BikingRouteOverlay overlay : mBikeOverlays) {
            overlay.removeFromMap();
        }
        mBikeOverlays.clear();
    }

    private void clearCar() {
        for (DrivingRouteOverlay overlay : mDriveOverlays) {
            overlay.removeFromMap();
        }
        mDriveOverlays.clear();
    }

    private void routePlanBike() {
        BikingRoutePlanOption planOption = new BikingRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size()-1; i++) {
            mSearch.bikingSearch(planOption.from(mPlanNodes.get(i)).to(mPlanNodes.get(i+1)));
        }
    }

    private void routePlanCar() {
        DrivingRoutePlanOption planOption = new DrivingRoutePlanOption();
        ArrayList<PlanNode> planNodes = new ArrayList<>();
        for (int i = 1; i < mPlanNodes.size() - 1; i++) {
            planNodes.add(mPlanNodes.get(i));
        }
        mSearch.drivingSearch(planOption.from(mPlanNodes.get(0)).to(mPlanNodes.get(mPlanNodes.size()-1)).passBy(planNodes));
    }

    private void routePlanBus() {
        position = 0;
        mBusMap = new HashMap<>(mNodes.size());
        TransitRoutePlanOption planOption = new TransitRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size()-1; i++) {
            mSearch.transitSearch(planOption.from(mPlanNodes.get(i)).city(C.CHONG_QING).to(mPlanNodes.get(i+1)));
        }
//        mSearch.transitSearch(planOption.from(mPlanNodes.get(mPlanNodes.size() - 1)).to(mPlanNodes.get(0)));
    }

    private void popupWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.window_popup_map, null);
        int height = getWindowManager().getDefaultDisplay().getHeight() * 2 / 3;
        mPopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                height, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.setAnimationStyle(R.style.anim_bottom_bar);

        popupWindowView();
    }

    private void popupWindowView() {
        Button btnBus = mPopupWindow.getContentView().findViewById(R.id.btn_bus);
        Button btnDriver = mPopupWindow.getContentView().findViewById(R.id.btn_car);
        Button btnBike = mPopupWindow.getContentView().findViewById(R.id.btn_bike);
        mListView = mPopupWindow.getContentView().findViewById(R.id.expanded_list_view);

        btnBus.setOnClickListener(this);
        btnDriver.setOnClickListener(this);
        btnBike.setOnClickListener(this);

        routePlanBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationUtil.unRegisterListener(listener);
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
