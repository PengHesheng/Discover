package com.example.a14512.discover.modules.main.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.search.core.SearchResult;
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
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.adpter.ExpandableListAdapter;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.mapapi.overlayutil.DrivingRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.TransitRouteOverlay;
import com.example.a14512.discover.utils.mapapi.overlayutil.WalkingRouteOverlay;

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

    private ArrayList<PlanNode> mPlanNodes = new ArrayList<>();

    private PopupWindow mPopupWindow;
    private ExpandableListView mListView;
    private Button mBtnBus, mBtnDriver, mBtnWalk;
    private Map<String, List<TransitRouteLine.TransitStep>> mBusMap = new HashMap<>();
    private List<String> mNodes = new ArrayList<>();
    private ExpandableListAdapter adapter;
    private int position = 0;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initData();
        initView();
        searchResult();
        popupWindow();
    }

    private void initData() {
        BDLocation location = (BDLocation) ACache.getDefault().getAsObject(C.LOCATION);
        if (location != null) {
            city = location.getCity();
            PLog.e(city);
        } else {
            city = "重庆市";
        }
        PLog.e(city);
        mNodes.add("邮电大学");
        mNodes.add("南坪");
        mNodes.add("解放碑");
        initPlanNodes();
    }

    private void initPlanNodes() {
        for (int i = 0; i < mNodes.size(); i++) {
            PlanNode planNode = PlanNode.withCityNameAndPlaceName("重庆市", mNodes.get(i));
            mPlanNodes.add(planNode);
        }
    }

    private void searchResult() {
        mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener(){

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //未找到结果
                    return;
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }
                Log.e(TAG, "walk start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    WalkingRouteLine route = result.getRouteLines().get(0);
                    Log.e(TAG, "" + route.getTitle());
                    List<WalkingRouteLine.WalkingStep> steps = route.getAllStep();
                    WalkingRouteLine.WalkingStep step = steps.get(0);
                    Log.e(TAG, step.getName() + "\n" + step.getEntranceInstructions() + "\n"
                            + step.getExitInstructions() + "\n" + step.describeContents());
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                    overlay.setData(route);
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
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
                Log.e(TAG, "bus start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteLine route = result.getRouteLines().get(0);

                    //创建公交路线规划线路覆盖物
                    List<TransitRouteLine.TransitStep> steps = route.getAllStep();
                    mBusMap.put(mNodes.get(position), steps);
                    position++;
                    PLog.e(":"+position);
                    if (position == mNodes.size() - 1) {
                        PLog.e(":"+position);
                        mBusMap.put(mNodes.get(position), new ArrayList<>());
                        adapter = new ExpandableListAdapter(MapActivity.this, mBusMap, mNodes);
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
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
                Log.e(TAG, "drive start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteLine route = result.getRouteLines().get(0);
                    List<DrivingRouteLine.DrivingStep> steps = route.getAllStep();
                    DrivingRouteLine.DrivingStep step = steps.get(0);
                    Log.e(TAG, step.getName() + "\n" + step.getEntranceInstructions() + "\n"
                            +step.getExitInstructions() + "\n" +step.getInstructions() + "\n"
                            +step.getNumTurns());
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
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

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
        mPlace.setText("邮电大学——解放碑");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_plan:
                startIntentActivity(this, new MainActivity());
                finish();
                break;
            case R.id.tv_show_place:
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_bus:
                routePlanBus();
                break;
            case R.id.btn_car:
                routePlanCar();
                mPopupWindow.dismiss();
                break;
            case R.id.btn_walk:
                routePlanWalk();
                mPopupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void routePlanWalk() {
        PLog.e(TAG, "walk click");
        WalkingRoutePlanOption planOption = new WalkingRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size()-1; i++) {
            PLog.e(TAG, "xunhuan");
            mSearch.walkingSearch(planOption.from(mPlanNodes.get(i)).to(mPlanNodes.get(i+1)));
        }
    }

    private void routePlanCar() {
        DrivingRoutePlanOption planOption = new DrivingRoutePlanOption();
        ArrayList<PlanNode> planNodes = new ArrayList<>();
        for (int i = 1; i < mPlanNodes.size() - 1; i++) {
            planNodes.add(mPlanNodes.get(i));
        }
        mSearch.drivingSearch(planOption.from(mPlanNodes.get(0)).to(mPlanNodes.get(mPlanNodes.size()-1))
                .passBy(planNodes).currentCity(city));

    }

    private void routePlanBus() {
        position = 0;
        TransitRoutePlanOption planOption = new TransitRoutePlanOption();
        for (int i = 0; i < mPlanNodes.size()-1; i++) {
            PLog.e(TAG, "xunhuan");
            mSearch.transitSearch(planOption.from(mPlanNodes.get(i)).city(city).to(mPlanNodes.get(i+1)));
        }
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
        mBtnBus = mPopupWindow.getContentView().findViewById(R.id.btn_bus);
        mBtnDriver = mPopupWindow.getContentView().findViewById(R.id.btn_car);
        mBtnWalk = mPopupWindow.getContentView().findViewById(R.id.btn_walk);
        mListView = mPopupWindow.getContentView().findViewById(R.id.expanded_list_view);

        mBtnBus.setOnClickListener(this);
        mBtnDriver.setOnClickListener(this);
        mBtnWalk.setOnClickListener(this);

        routePlanBus();
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
