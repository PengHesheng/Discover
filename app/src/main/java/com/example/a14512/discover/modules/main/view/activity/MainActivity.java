package com.example.a14512.discover.modules.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.custom.SlidingView;
import com.example.a14512.discover.modules.arround.view.AroundActivity;
import com.example.a14512.discover.modules.login.view.LoginActivity;
import com.example.a14512.discover.modules.main.mode.entity.weather.WeatherData;
import com.example.a14512.discover.modules.userself.modules.attention.MyAttentionActivity;
import com.example.a14512.discover.modules.userself.modules.route.view.RouteActivity;
import com.example.a14512.discover.modules.shark.SharkActivity;
import com.example.a14512.discover.modules.userself.modules.travel.view.TravelKnowledgeActivity;
import com.example.a14512.discover.network.ApiService;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.network.RxUtil.SchedulerTransformer;
import com.example.a14512.discover.network.RxUtil.interceptor.HttpResponseFunc;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;

/**
 * @author 14512 on 2018/1/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLogin;
    private LinearLayout mLoginOut;
    private ImageView mPortrait;
    private TextView mName;
    private LinearLayout mAttentionLayout;
    private LinearLayout mRouteLayout;
    private LinearLayout mShareLayout;
    private LinearLayout mTravelLayout;
    private TextView mTemperature;
    private ImageView mWeatherIcon;

    private LinearLayout mSettings;
    private RelativeLayout mainLayout;
    private SlidingView mSlidingView;

    private LocationUtil mLocationUtil;
    private BDAbstractLocationListener listener;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        isLogin();
        getWeather();
    }

    private void isLogin() {
        String account = ACache.getDefault().getAsString("account");
        if (account != null) {
            mLoginOut.setVisibility(View.GONE);
            mLogin.setVisibility(View.VISIBLE);
        } else {
            mLoginOut.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.GONE);
        }
    }

    private void getWeather() {
        mLocationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                RetrofitHelper.createApi(ApiService.WEATHER, ApiService.class)
                        .getWeather(bdLocation.getCity(), C.WEATHER_KEY)
                        .compose(SchedulerTransformer.transformer())
                        .onErrorResumeNext(new HttpResponseFunc<>())
                        .subscribe(new ApiSubscriber<WeatherData>(MainActivity.this,
                                false, false) {
                            @Override
                            public void onNext(WeatherData value) {
                                if (value.heWeather != null) {
                                    PLog.e(value.heWeather.get(0).mNow.tmp);
                                    mTemperature.setText(value.heWeather.get(0).mNow.tmp);
                                    String icon = "https://cdn.heweather.com/cond_icon/" +
                                            value.heWeather.get(0).mNow.condCode +".png";
                                    PLog.e(icon);
                                    //TODO 图片加载问题，Url加载不了 https的原因
                                    Glide.with(MainActivity.this).load(icon)
                                            .error(R.mipmap.weather_100).into(mWeatherIcon);
                                }
                            }
                        });
            }
        };
        mLocationUtil.getLocation(this, listener);
    }


    private void initView() {
        mSlidingView = findViewById(R.id.sliding_view);

        mLogin = findViewById(R.id.layout_login);
        mLoginOut = findViewById(R.id.layout_login_out);
        mPortrait = findViewById(R.id.img_head_portrait);
        mName = findViewById(R.id.tv_head_name);

        mAttentionLayout = findViewById(R.id.layout_my_attention);
        mRouteLayout = findViewById(R.id.layout_my_route);
        mShareLayout = findViewById(R.id.layout_my_share);
        mTravelLayout = findViewById(R.id.layout_travel);

        mTemperature = findViewById(R.id.tv_menu_temperature);
        mWeatherIcon = findViewById(R.id.img_weather_icon);
        mSettings = findViewById(R.id.layout_menu_settings);

        mainLayout = findViewById(R.id.main_layout);

        ImageView menu = findViewById(R.id.main_menu_icon);
        ImageButton imgBtnRoutePlan = findViewById(R.id.img_btn_route_plan);
        ImageButton imgBtnAround = findViewById(R.id.img_btn_around);
        ImageButton imgBtnShark = findViewById(R.id.img_btn_shark);

        mainLayout.setOnClickListener(this);

        mLogin.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
        mPortrait.setOnClickListener(this);
        mName.setOnClickListener(this);

        mAttentionLayout.setOnClickListener(this);
        mRouteLayout.setOnClickListener(this);
        mShareLayout.setOnClickListener(this);
        mTravelLayout.setOnClickListener(this);

        mSettings.setOnClickListener(this);


        menu.setOnClickListener(this);
        imgBtnRoutePlan.setOnClickListener(this);
        imgBtnAround.setOnClickListener(this);
        imgBtnShark.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_layout:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    return;
                }
                break;
            case R.id.layout_login_out:
                startActivityForResult(new Intent(this, LoginActivity.class), 10);
                break;
            case R.id.img_head_portrait:
                //TODO 点击头像处理事件
                break;
            case R.id.layout_my_attention:
                startIntentActivity(this, MyAttentionActivity.class);
                break;
            case R.id.layout_travel:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    startIntentActivity(this, TravelKnowledgeActivity.class);
                }
                break;
            case R.id.layout_around:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    startIntentActivity(this, AroundActivity.class);
                }
                break;
            case R.id.layout_my_route:
                startIntentActivity(this, RouteActivity.class);
                break;


            case R.id.img_btn_route_plan:
                startIntentActivity(this, ChooseActivity.class);
                break;
            case R.id.img_btn_around:
                startIntentActivity(this, AroundActivity.class);
                break;
            case R.id.img_btn_shark:
                startIntentActivity(this, SharkActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationUtil.unRegisterListener(listener);
    }
}
