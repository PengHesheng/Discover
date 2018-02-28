package com.example.a14512.discover.modules.arround.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.arround.presenter.AroundPresenterImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.view.activity.MapActivity;
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
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationUtil locationUtil;
    private BDAbstractLocationListener mListener;

    private AroundPresenterImp mPresenter;
    private Scenic mScenic = new Scenic();


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

                mScenic.name = location.getStreet();
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
                overlayDistrict(location.getCity());
                locationUtil.unRegisterListener(mListener);
            }
        };
        locationUtil.getLocation(this, mListener);
    }

    private void overlayDistrict(String city) {
        DistrictSearch mDistrictSearch = DistrictSearch.newInstance();
        DistrictSearchOption districtSearchOption = new DistrictSearchOption();
        districtSearchOption.cityName(C.CHONG_QING);
        districtSearchOption.districtName(C.CHONG_QING);

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
                    OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
                            .points(polyline).dottedLine(true).color(0xAA00FF00);
                    mBaiduMap.addOverlay(ooPolyline11);
                    OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                            .stroke(new Stroke(5, 0xAA00FF88)).fillColor(0xAAFFFF00);
                    mBaiduMap.addOverlay(ooPolygon);
                    for (LatLng latLng : polyline) {
                        builder.include(latLng);
                    }
                }
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));

            }
        };
        mDistrictSearch.setOnDistrictSearchListener(listener);
        mDistrictSearch.searchDistrict(districtSearchOption);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainToolbar));
        mTitle.setText("周边");
        mBack.setOnClickListener(v -> finish());
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
        mPresenter = new AroundPresenterImp(this, this);
        mPresenter.getSecnics();
    }

    @Override
    public void setScenics(ArrayList<Scenic> scenics) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        ArrayList<OverlayOptions> optionsArrayList = new ArrayList<>();
        for (Scenic scenic : scenics) {
            LatLng latLng = new LatLng(scenic.latitude, scenic.longitude);
            Bundle bundle = new Bundle();
            bundle.putSerializable("scenic" ,scenic);
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
            return false;
        });
    }

    private void popWindow(Scenic scenic) {
        LatLng pt = new LatLng(scenic.latitude, scenic.longitude);

        View scenicView = LayoutInflater.from(this).inflate(R.layout.item_around_scenic, null);
        TextView name = scenicView.findViewById(R.id.tv_scenic_name_around);
        TextView location = scenicView.findViewById(R.id.tv_location_around);
        TextView peopleAver = scenicView.findViewById(R.id.tv_people_aver_around);
        TextView monthAver = scenicView.findViewById(R.id.tv_month_aver_around);
        Button go = scenicView.findViewById(R.id.btn_go_around);
        name.setText(scenic.name);
        location.setText(scenic.location);
        peopleAver.setText(String.valueOf(scenic.peopleAver));
        monthAver.setText(String.valueOf(scenic.monthAver));
        go.setOnClickListener(v -> {
            ArrayList<Scenic> scenics = new ArrayList<>();
            if (mScenic != null) {
                scenics.add(mScenic);
                scenics.add(scenic);
                Intent intent = new Intent(this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.SCENIC_DETAIL, scenics);
                intent.putExtra(C.SCENIC_DETAIL, bundle);
                startActivity(intent);
            }
        });

        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(scenicView, pt, -47);

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
        mMapView.onDestroy();
        super.onDestroy();
    }
}
