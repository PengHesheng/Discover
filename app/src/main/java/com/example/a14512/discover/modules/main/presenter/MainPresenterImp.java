package com.example.a14512.discover.modules.main.presenter;

import android.content.Context;

import com.example.a14512.discover.modules.main.mode.Mode;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.modules.main.view.IMainView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;

/**
 * @author 14512 on 2018/2/8
 */

public class MainPresenterImp implements IMainPresenter {
    private IMainView mView;
    private Mode mMode;
    private Context mContext;

    public MainPresenterImp(IMainView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getWeather(String city) {
        ApiSubscriber<WeatherData> apiSubscriber = new ApiSubscriber<WeatherData>(mContext,
                false, false) {
            @Override
            public void onNext(WeatherData value) {
                if (value.heWeather != null) {
                    PLog.e(value.heWeather.get(0).mNow.tmp);
                    mView.setWeather(value.heWeather.get(0));
                }
            }
        };
        mMode.getWeather(apiSubscriber, city);


    }

    @Override
    public void isLogin() {
        String account = ACache.getDefault().getAsString("account");
        if (account != null) {
            mView.isLogin(true);
        } else {
            mView.isLogin(false);
        }
    }
}
