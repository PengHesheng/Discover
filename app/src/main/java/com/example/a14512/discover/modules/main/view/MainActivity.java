package com.example.a14512.discover.modules.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.custom.SlidingView;
import com.example.a14512.discover.modules.arround.view.AroundActivity;
import com.example.a14512.discover.modules.login.view.LoginActivity;
import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.modules.main.presenter.MainPresenterImp;
import com.example.a14512.discover.modules.main.userself.attention.view.MyAttentionActivity;
import com.example.a14512.discover.modules.main.userself.changeuser.view.ChangeUserInfoActivity;
import com.example.a14512.discover.modules.main.userself.myroute.view.MyRouteActivity;
import com.example.a14512.discover.modules.main.userself.personality.view.PersonalityActivity;
import com.example.a14512.discover.modules.main.userself.personality.view.PersonalityAdviceActivity;
import com.example.a14512.discover.modules.main.userself.settings.view.SettingsActivity;
import com.example.a14512.discover.modules.main.userself.share.view.MyShareActivity;
import com.example.a14512.discover.modules.main.userself.travel.view.TravelKnowledgeActivity;
import com.example.a14512.discover.modules.routeplan.view.activity.ChooseActivity;
import com.example.a14512.discover.modules.shake.view.ShakeActivity;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.VersionUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author 14512 on 2018/1/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, IMainView {

    private static final int CAMERA = 1;
    private static final int ACCESS_COARSE_LOCATION = 2;
    private static final int ACCESS_FINE_LOCATION = 3;
    private static final int READ_PHONE_STATE = 4;
    private static final int WRITE_EXTERNAL_STORAGE = 5;

    private LinearLayout mLogin;
    private LinearLayout mLoginOut;
    private ImageView mPortrait;
    private TextView mName;
    private TextView mSigned;
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
        justVersion();
        getPermission();
        setDecorView();
        initView();
        isLogin();
        getWeather();
    }

    private void justVersion() {
        float nowVersionCode = VersionUtil.getVersionCode(this);
        SharedPreferences sp = getSharedPreferences("welcomeInfo", MODE_PRIVATE);
        float spVersionCode = sp.getFloat("spVersionCode", 0);
        if (nowVersionCode > spVersionCode) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("spVersionCode", nowVersionCode);
            editor.apply();
            startIntentActivity(this, PersonalityAdviceActivity.class);
        } else {
            //非首次启动
        }
    }

    private void getPermission() {
        MPermissions.requestPermissions(this, CAMERA, Manifest.permission.CAMERA);
        MPermissions.requestPermissions(this, ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        MPermissions.requestPermissions(this, ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        MPermissions.requestPermissions(this, READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        MPermissions.requestPermissions(this, WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(CAMERA)
    public void requestSdcardSuccess() {
    }

    @PermissionDenied(CAMERA)
    public void requestSdcardFailed() {
        ToastUtil.show(this, "获取权限失败!");
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
                mLocationUtil.unRegisterListener(listener);
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
        mSigned = findViewById(R.id.tv_head_signed);

        LinearLayout attentionLayout = findViewById(R.id.layout_my_attention);
        LinearLayout routeLayout = findViewById(R.id.layout_my_route);
        LinearLayout shareLayout = findViewById(R.id.layout_my_share);
        LinearLayout travelLayout = findViewById(R.id.layout_travel);
        LinearLayout personalityLayout = findViewById(R.id.layout_personality);

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
        personalityLayout.setOnClickListener(this);

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
                startActivityForResult(new Intent(this, ChangeUserInfoActivity.class),
                        C.UPDATE_INFO);
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
                startIntentActivity(this, MyShareActivity.class);
                break;
            case R.id.layout_travel:
                startIntentActivity(this, TravelKnowledgeActivity.class);
                break;
            case R.id.layout_personality:
                if (mIsLogin) {
                    startIntentActivity(this, PersonalityActivity.class);
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
                }
                break;
            case R.id.layout_menu_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class),
                        C.LOGIN_OUT);
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
                        startActivityForResult(new Intent(this, LoginActivity.class),
                                C.LOGIN);
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
            switch (requestCode) {
                case C.LOGIN:
                    mIsLogin = true;
                    isLogin(true);
                    break;
                case C.LOGIN_OUT:
                    mIsLogin = false;
                    isLogin(false);
                    break;
                case C.UPDATE_INFO:
                    mPresenter.getUserInfo();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setWeather(WeatherData.Weather weather) {
        mTemperature.setText(weather.mNow.tmp);
        String icon = "file:///android_asset/cond_icon_heweather/" +
               weather.mNow.condCode +".png";
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

    @Override
    public void setUserInfo(UserInfo userInfo) {
        Glide.with(this).load(userInfo.portrait)
                .bitmapTransform(new CropCircleTransformation(this), new FitCenter(this))
                .priority(Priority.HIGH)
                .error(R.mipmap.default_portrait).into(mPortrait);
        /*Glide.with(this)
                .load(R.mipmap.center_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        userLoginLayout.setBackgroundResource(R.mipmap.center_bg);
                    }
                });*/
        if (userInfo.name == null) {
            mName.setText("用户");
        } else {
            mName.setText(userInfo.name);
        }
        mSigned.setText(userInfo.signed);
    }

}
