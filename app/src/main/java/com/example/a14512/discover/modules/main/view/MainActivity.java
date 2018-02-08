package com.example.a14512.discover.modules.main.view;

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
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.modules.main.presenter.MainPresenterImp;
import com.example.a14512.discover.modules.main.userself.attention.view.MyAttentionActivity;
import com.example.a14512.discover.modules.main.userself.my_route.view.MyRouteActivity;
import com.example.a14512.discover.modules.main.userself.settings.view.SettingsActivity;
import com.example.a14512.discover.modules.main.userself.share.view.MyShareActivity;
import com.example.a14512.discover.modules.main.userself.travel.view.TravelKnowledgeActivity;
import com.example.a14512.discover.modules.route_plan.view.activity.ChooseActivity;
import com.example.a14512.discover.modules.shake.view.ShakeActivity;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;

/**
 * @author 14512 on 2018/1/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, IMainView {

    private LinearLayout mLogin;
    private LinearLayout mLoginOut;
    private ImageView mPortrait;
    private TextView mName;
    private TextView mTemperature;
    private ImageView mWeatherIcon;

    private SlidingView mSlidingView;

    private LocationUtil mLocationUtil;
    private BDAbstractLocationListener listener;

    private boolean mIsLogin = false;
    private MainPresenterImp mPresenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDecorView();
        initView();
        isLogin();
        getWeather();
    }

    private void isLogin() {
        mPresenter = new MainPresenterImp(this, this);
        mPresenter.isLogin();
    }

    private void getWeather() {
        mLocationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mPresenter.getWeather(bdLocation.getCity());
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

        LinearLayout attentionLayout = findViewById(R.id.layout_my_attention);
        LinearLayout routeLayout = findViewById(R.id.layout_my_route);
        LinearLayout shareLayout = findViewById(R.id.layout_my_share);
        LinearLayout travelLayout = findViewById(R.id.layout_travel);

        mTemperature = findViewById(R.id.tv_menu_temperature);
        mWeatherIcon = findViewById(R.id.img_weather_icon);
        LinearLayout settings = findViewById(R.id.layout_menu_settings);

        RelativeLayout mainLayout = findViewById(R.id.main_layout);

        ImageView menu = findViewById(R.id.main_menu_icon);
        ImageButton imgBtnRoutePlan = findViewById(R.id.img_btn_route_plan);
        ImageButton imgBtnAround = findViewById(R.id.img_btn_around);
        ImageButton imgBtnShark = findViewById(R.id.img_btn_shark);

        mainLayout.setOnClickListener(this);

        mLogin.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
        mPortrait.setOnClickListener(this);
        mName.setOnClickListener(this);

        attentionLayout.setOnClickListener(this);
        routeLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        travelLayout.setOnClickListener(this);

        settings.setOnClickListener(this);

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
                startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                break;
            case R.id.img_head_portrait:
                //TODO 点击头像处理事件
                break;
            case R.id.layout_my_attention:
                if (mIsLogin) {
                    startIntentActivity(this, MyAttentionActivity.class);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                }
                break;
            case R.id.layout_my_route:
                if (mIsLogin) {
                    startIntentActivity(this, MyRouteActivity.class);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                }
                break;
            case R.id.layout_my_share:
                if (mIsLogin) {
                    startIntentActivity(this, MyShareActivity.class);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                }
                break;
            case R.id.layout_travel:
                startIntentActivity(this, TravelKnowledgeActivity.class);
                break;
            case R.id.layout_menu_settings:
                startIntentActivity(this, SettingsActivity.class);
                break;
            case R.id.main_menu_icon:
                mSlidingView.changeMenu();
                break;
            case R.id.img_btn_route_plan:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    if (mIsLogin) {
                        startIntentActivity(this, ChooseActivity.class);
                    } else {
                        startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                    }
                }
                break;
            case R.id.img_btn_around:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    startIntentActivity(this, AroundActivity.class);
                }
                break;
            case R.id.img_btn_shark:
                if (mSlidingView.getMenu()) {
                    mSlidingView.changeMenu();
                } else {
                    startIntentActivity(this, ShakeActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == C.LOGIN) {
                mIsLogin = true;
                isLogin(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationUtil.unRegisterListener(listener);
    }

    @Override
    public void setWeather(WeatherData.Weather weather) {
        mTemperature.setText(weather.mNow.tmp);
        String icon = "file:///android_asset/cond_icon_heweather/" +
               weather.mNow.condCode +".png";
        PLog.e(icon);
        Glide.with(this).load(icon).error(R.mipmap.weather_100).into(mWeatherIcon);
    }

    @Override
    public void isLogin(boolean isLogin) {
        mIsLogin = isLogin;
        if (isLogin) {
            mLoginOut.setVisibility(View.GONE);
            mLogin.setVisibility(View.VISIBLE);
        } else {
            mLoginOut.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.GONE);
        }
    }
}
