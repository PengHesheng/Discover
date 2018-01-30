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

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
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
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.adpter.ExpandableListAdapter;
import com.example.a14512.discover.utils.maputils.overlayutil.TransitRouteOverlay;

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
    private ExpandableListView mListView;

    private RoutePlanSearch mSearch;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;

    private PlanNode stMassNode, enMassNode, lastNode;

    private PopupWindow mPopupWindow;

    private Map<String, List<String>> mMap = new HashMap<>();
    private List<String> mNodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initData();
        initView();
        search();
        popupWindow();
    }

    private void initData() {
        List<String> child1 = getChildList(1);
        List<String> child2 = getChildList(2);
        List<String> child3 = getChildList(3);
        for (int i = 0; i <5; i++) {
            String str = "parent" + i;
            mNodes.add(str);
        }
        mMap.put(mNodes.get(0), child1);
        mMap.put(mNodes.get(1), new ArrayList<>());
        mMap.put(mNodes.get(2), child2);
        mMap.put(mNodes.get(3), child3);
        mMap.put(mNodes.get(4), new ArrayList<>());

    }

    private List<String> getChildList(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            String str = "child" + n + ":" + i;
            list.add(str);
        }
        return list;
    }

    private void search() {
        mSearch = RoutePlanSearch.newInstance();

        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener(){

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

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
                Log.e(TAG, " start " + result.error);
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteLine route = result.getRouteLines().get(0);
                    Log.e(TAG, "" + route.getTitle());
                    //创建公交路线规划线路覆盖物

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

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };

        mSearch.setOnGetRoutePlanResultListener(routeListener);

        stMassNode = PlanNode.withCityNameAndPlaceName("重庆市", "邮电大学");
        enMassNode = PlanNode.withCityNameAndPlaceName("重庆市", "南坪");
        lastNode = PlanNode.withCityNameAndPlaceName("重庆市", "解放碑");

        mSearch.transitSearch(new TransitRoutePlanOption().from(stMassNode).city("重庆市").to(enMassNode));
        mSearch.transitSearch(new TransitRoutePlanOption().from(enMassNode).city("重庆市").to(lastNode));
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

                break;
            case R.id.tv_show_place:
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            default:
                break;
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
        mListView = mPopupWindow.getContentView().findViewById(R.id.expanded_list_view);
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, mMap, mNodes);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
