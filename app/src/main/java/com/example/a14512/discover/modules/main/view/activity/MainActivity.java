package com.example.a14512.discover.modules.main.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.custom.SlidingView;
import com.example.a14512.discover.modules.arround.view.AroundActivity;
import com.example.a14512.discover.modules.community.view.CommunityActivity;
import com.example.a14512.discover.modules.main.mode.entity.weather.WeatherData;
import com.example.a14512.discover.modules.route.view.RouteActivity;
import com.example.a14512.discover.modules.travel.view.TravelKnowledgeActivity;
import com.example.a14512.discover.network.ApiService;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.network.RxUtil.SchedulerTransformer;
import com.example.a14512.discover.network.RxUtil.interceptor.HttpResponseFunc;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;

/**
 * @author 14512 on 2018/1/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mPortrait;
    private TextView mName;
    private TextView mSignature;
    private LinearLayout mAttention;
    private TextView mTemperature;
    private ImageView mWeatherIcon;
    private LinearLayout mSettings;
    private LinearLayout mainLayout;
    private SlidingView mSlidingView;

    private LocationUtil mLocationUtil;
    private BDAbstractLocationListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getWeather();
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
        mPortrait = findViewById(R.id.img_head_portrait);
        mName = findViewById(R.id.tv_head_name);
        mSignature = findViewById(R.id.tv_head_signature);
        mAttention = findViewById(R.id.layout_my_attention);
        mTemperature = findViewById(R.id.tv_menu_temperature);
        mWeatherIcon = findViewById(R.id.img_weather_icon);
        mSettings = findViewById(R.id.layout_menu_settings);
        mainLayout = findViewById(R.id.main_layout);
        mSlidingView = findViewById(R.id.sliding_view);
        ImageButton imgBtnRoutePlan = findViewById(R.id.img_btn_route_plan);
        TextView tv = findViewById(R.id.tv_route_plan);
        LinearLayout layoutTravel = findViewById(R.id.layout_travel);
        LinearLayout layoutCommunity = findViewById(R.id.layout_community);
        LinearLayout layoutAround = findViewById(R.id.layout_around);
        LinearLayout layoutMyRoute = findViewById(R.id.layout_my_route);

        Glide.with(this).load("http://oyojokgx1.bkt.clouddn.com/2017-11-20_22:32:19:066").into(imgBtnRoutePlan);

        mPortrait.setOnClickListener(this);
        mName.setOnClickListener(this);
        mSignature.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mTemperature.setOnClickListener(this);
        mSettings.setOnClickListener(this);
        mainLayout.setOnClickListener(this);

        tv.setText("路线规划");
        imgBtnRoutePlan.setBackgroundResource(R.mipmap.ic_launcher_round);
        imgBtnRoutePlan.setOnClickListener(this);
        layoutTravel.setOnClickListener(this);
        layoutCommunity.setOnClickListener(this);
        layoutAround.setOnClickListener(this);
        layoutMyRoute.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_route_plan:
                startIntentActivity(this, ChooseActivity.class);
                break;
            case R.id.layout_travel:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    startIntentActivity(this, TravelKnowledgeActivity.class);
                }
                break;
            case R.id.layout_community:
                startIntentActivity(this, CommunityActivity.class);
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
            case R.id.layout_my_attention:
                mSlidingView.changeMenu();
                break;
            case R.id.main_layout:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    return;
                }
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
