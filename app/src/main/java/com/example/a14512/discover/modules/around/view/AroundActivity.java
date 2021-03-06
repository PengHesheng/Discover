package com.example.a14512.discover.modules.around.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.around.presenter.AroundPresenterImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.view.activity.ScenicActivity;
import com.example.a14512.discover.utils.LocationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14512 on 2018/1/27
 */

public class AroundActivity extends BaseActivity implements IAroundView {
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TextView walkTime, distance;

    private LocationUtil locationUtil;
    private BDAbstractLocationListener mListener;
    private Scenic mScenic = new Scenic();
    private RoutePlanSearch mSearch;
    private AroundPresenterImp presenter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around);
        initView();
        initToolbar();
        getLocation();
    }

    private void getLocation() {
        mBaiduMap.setMyLocationEnabled(true);
        locationUtil = LocationUtil.getInstance();
        mListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (!location.getStreet().isEmpty()) {
                    mScenic.name = location.getStreet() + location.getStreetNumber();
                } else {
                    mScenic.name = "我的位置";
                }
                mScenic.latitude = location.getLatitude();
                mScenic.longitude = location.getLongitude();
                mScenic.location = location.getStreet();
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(location.getRadius())
//                        // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .direction(100).latitude(location.getLatitude())
//                        .longitude(location.getLongitude()).build();
//                mBaiduMap.setMyLocationData(locData);
//                MyLocationConfiguration config = new MyLocationConfiguration(null, true, null);
//                mBaiduMap.setMyLocationConfiguration(config);
//
//                MapStatus mapStatus = new MapStatus.Builder()
//                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
//                        .zoom(12)
//                        .build();
//                MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
//                mBaiduMap.setMapStatus(update);
                overlayDistrict(C.CHONG_QING);
                locationUtil.unRegisterListener(mListener);
            }
        };
        locationUtil.getLocation(this, mListener);
    }

    private void overlayDistrict(String county) {
        DistrictSearch mDistrictSearch = DistrictSearch.newInstance();
        DistrictSearchOption districtSearchOption = new DistrictSearchOption();
        districtSearchOption.cityName(C.CHONG_QING);
        districtSearchOption.districtName(county);

        OnGetDistricSearchResultListener listener = districtResult -> {
            if (districtResult == null) {
                return;
            }
            if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
                List<List<LatLng>> polyLines = districtResult.getPolylines();
                if (polyLines == null) {
                    return;
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (List<LatLng> polyline : polyLines) {
//                    if (county.equals(C.CHONG_QING)) {
//                        OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
//                                .points(polyline).dottedLine(true).color(lineColor);
//                        mBaiduMap.addOverlay(ooPolyline11);
//                    } else {
//                        OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
//                                .stroke(new Stroke(5, R.color.aroundLineGray)).fillColor(areaColor);
//                        mBaiduMap.addOverlay(ooPolygon);
//                    }
                    for (LatLng latLng : polyline) {
                        builder.include(latLng);
                    }
                }
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));

                //设置区域背景
                BitmapDescriptor bdGround = BitmapDescriptorFactory
                        .fromResource(R.drawable.around_cq_bg);
                OverlayOptions ooGround = new GroundOverlayOptions()
                        .positionFromBounds(builder.build())
                        .image(bdGround)
                        .transparency(0.8f);

                mBaiduMap.addOverlay(ooGround);
            }
        };
        mDistrictSearch.setOnDistrictSearchListener(listener);
        mDistrictSearch.searchDistrict(districtSearchOption);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainToolbar));
        mTitle.setText("周边");
        mRight.setImageResource(R.mipmap.share);
        mBack.setOnClickListener(v -> finish());
        mRight.setOnClickListener(this::popupWindow);
    }

    private void popupWindow(View v) {
        View menu = getLayoutInflater().inflate(R.layout.window_popup_around_menu, null);
        TextView tvNauture = menu.findViewById(R.id.tv_personality31);
        tvNauture.setOnClickListener(v1 -> {
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            presenter.getTypeScenics(1);
            popupWindow.dismiss();
        });
        TextView tvPeople = menu.findViewById(R.id.tv_personality32);
        tvPeople.setOnClickListener(v1 -> {
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            presenter.getTypeScenics(2);
            popupWindow.dismiss();
        });
        TextView tvQuite = menu.findViewById(R.id.tv_personality35);
        tvQuite.setOnClickListener(v1 -> {
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            presenter.getTypeScenics(3);
            popupWindow.dismiss();
        });
        TextView tvNoise = menu.findViewById(R.id.tv_personality36);
        tvNoise.setOnClickListener(v1 -> {
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            presenter.getTypeScenics(4);
            popupWindow.dismiss();
        });
        TextView tvNationality = menu.findViewById(R.id.tv_personality33);
        tvNationality.setOnClickListener(v1 -> {
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            presenter.getTypeScenics(5);
            popupWindow.dismiss();
        });
        TextView tvTradional = menu.findViewById(R.id.tv_personality34);
        tvTradional.setOnClickListener(v1 -> {
            presenter.getTypeScenics(6);
            mBaiduMap.clear();
            overlayDistrict(C.CHONG_QING);
            popupWindow.dismiss();
        });
        popupWindow = new PopupWindow(menu, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAsDropDown(v);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mMapView = findViewById(R.id.texture_map_around);

        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
        mSearch = RoutePlanSearch.newInstance();
//        mBaiduMap.showMapPoi(false);
//        MapStatus mapStatus = new MapStatus.Builder()
//                        .target(new LatLng(29.569389, 106.557978))
//                        .zoom(12)
//                        .build();
//        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
//        mBaiduMap.setMapStatus(update);

        presenter = new AroundPresenterImp(this, this);
        presenter.getSecnics();
    }

    @Override
    public void setScenics(ArrayList<Scenic> scenics) {
        ArrayList<OverlayOptions> optionsArrayList = new ArrayList<>();
        for (Scenic scenic : scenics) {
            LatLng latLng = new LatLng(scenic.latitude, scenic.longitude);
            Bundle bundle = new Bundle();
            bundle.putSerializable("scenic", scenic);
            OverlayOptions options = new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_icon))
                    .extraInfo(bundle);
            optionsArrayList.add(options);
        }
        mBaiduMap.addOverlays(optionsArrayList);
        mBaiduMap.setOnMarkerClickListener(marker -> {
            Scenic scenic = (Scenic) marker.getExtraInfo().get("scenic");
            popWindow(scenic);
            getWalkDistance(scenic);
            return false;
        });
    }

    private void getWalkDistance(Scenic scenic) {
        PlanNode start = PlanNode.withLocation(new LatLng(mScenic.latitude, mScenic.longitude));
        PlanNode end = PlanNode.withLocation(new LatLng(scenic.latitude, scenic.longitude));
        mSearch.walkingSearch(new WalkingRoutePlanOption().from(start).to(end));
        OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener() {

            @SuppressLint("SetTextI18n")
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
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    WalkingRouteLine route = result.getRouteLines().get(0);
                    walkTime.setText(String.valueOf(route.getDuration() / 60) + "min");
                    distance.setText(String.valueOf(route.getDistance()) + "m");
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {

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
            public void onGetBikingRouteResult(BikingRouteResult result) {
            }
        };
        mSearch.setOnGetRoutePlanResultListener(routeListener);
    }

    @SuppressLint("SetTextI18n")
    private void popWindow(Scenic scenic) {
        LatLng pt = new LatLng(scenic.latitude, scenic.longitude);

        View scenicView = LayoutInflater.from(this).inflate(R.layout.item_around_scenic, null);
        LinearLayout go = scenicView.findViewById(R.id.layout_around);
        ImageView imgScenic = scenicView.findViewById(R.id.img_scenic_around);
        TextView name = scenicView.findViewById(R.id.tv_scenic_name_around);
        TextView scenicFlag = scenicView.findViewById(R.id.tv_flag_around);
        TextView openTime = scenicView.findViewById(R.id.tv_open_time_around);
        TextView location = scenicView.findViewById(R.id.tv_location_around);
        TextView peopleAver = scenicView.findViewById(R.id.tv_people_aver_around);
        TextView monthAver = scenicView.findViewById(R.id.tv_month_aver_around);
        distance = scenicView.findViewById(R.id.tv_distance_around);
        walkTime = scenicView.findViewById(R.id.tv_walk_time_around);

        //Glide加载监听器，错误调试
//        RequestListener mRequestListener = new RequestListener() {
//            @Override
//            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                PLog.e("onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
//                return false;
//            }
//        };
        Glide.with(this).load(scenic.img.replaceAll("\\\\", ""))
                .error(R.drawable.ic_launcher_background).into(imgScenic);
        name.setText(scenic.name);
        scenicFlag.setText("景点标签：" + scenic.content);
        openTime.setText("开放时间：" + scenic.openTime);
        location.setText("景点地址：" + scenic.location);
        peopleAver.setText(String.valueOf(scenic.peopleAver));
        monthAver.setText(String.valueOf(scenic.monthAver));
        go.setOnClickListener(v -> {
            ArrayList<Scenic> scenics = new ArrayList<>();
            if (mScenic != null) {
                scenics.add(mScenic);
                scenics.add(scenic);
                Intent intent = new Intent(this, ScenicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.SCENIC_DETAIL, scenics);
                intent.putExtra(C.SCENIC_DETAIL, bundle);
                intent.putExtra("type", C.SCENIC_TYPE);
                startActivity(intent);
            }
        });

        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(scenicView, pt, 0);

        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

}