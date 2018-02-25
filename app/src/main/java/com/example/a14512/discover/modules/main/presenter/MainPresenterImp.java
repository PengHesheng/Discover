package com.example.a14512.discover.modules.main.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.mode.Mode;
import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.modules.main.view.IMainView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;

/**
 * @author 14512 on 2018/2/8
 */

public class MainPresenterImp implements IMainPresenter {
    private IMainView mView;
    private Mode mMode;
    private Context mContext;
    private String account;

    public MainPresenterImp(IMainView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
        account = ACache.getDefault().getAsString(C.ACCOUNT);
    }

    @Override
    public void getWeather(String city) {
        ApiSubscriber<WeatherData> apiSubscriber = new ApiSubscriber<WeatherData>(mContext,
                false, false) {
            @Override
            public void onNext(WeatherData value) {
                if (value.heWeather != null && value.heWeather.get(0) != null) {
                    mView.setWeather(value.heWeather.get(0));
                }
            }
        };
        mMode.getWeather(apiSubscriber, city);
    }

    @Override
    public void isLogin() {
        if (!C.ACCOUNT.equals(account)) {
            mView.isLogin(true);
        } else {
            mView.isLogin(false);
        }
    }

    @Override
    public void getUserInfo() {
        ApiSubscriber<UserInfo> apiSubscriber = new ApiSubscriber<UserInfo>(mContext, false, false) {
            @Override
            public void onNext(UserInfo value) {
                if (value != null) {
                    mView.setUserInfo(value);
                    ACache.getDefault().put("user_info", value);
                }
            }
        };
        mMode.getUserInfo(apiSubscriber, account);
    }
}
