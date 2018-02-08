package com.example.a14512.discover.modules.arround.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.arround.presenter.AroundPresenterImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.view.activity.MapActivity;
import com.example.a14512.discover.utils.LocationUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/27
 */

public class AroundActivity extends BaseActivity implements IAroundView {
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private WebView mWebView;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationUtil locationUtil;
    private BDAbstractLocationListener mListener;

    private AroundPresenterImp mPresenter;
    private Scenic myLocation = new Scenic();


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

                myLocation.name = "我的位置";
                myLocation.latitude = location.getLatitude();
                myLocation.longitude = location.getLongitude();
                myLocation.location = location.getStreet();
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                MyLocationConfiguration config = new MyLocationConfiguration(null, true, null);
                mBaiduMap.setMyLocationConfiguration(config);

                MapStatus mapStatus = new MapStatus.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(12)
                        .build();
                MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(mapStatus);
                mBaiduMap.setMapStatus(update);
            }
        };
        locationUtil.getLocation(this, mListener);
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
//        mWebView = findViewById(R.id.web_view_around);

        mBaiduMap = mMapView.getMap();

       /* WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);
        mWebView.loadUrl("file:///android_asset/localmap/index.html");*/

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
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Scenic scenic = (Scenic) marker.getExtraInfo().get("scenic");
                popWindow(scenic);
                return false;
            }
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
            if (myLocation != null) {
                scenics.add(myLocation);
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
        locationUtil.unRegisterListener(mListener);
        mMapView.onDestroy();
        super.onDestroy();
    }
}
