package com.example.a14512.discover.modules.main.mode;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.network.ApiService;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.network.RxUtil.SchedulerTransformer;
import com.example.a14512.discover.network.RxUtil.interceptor.HttpResponseFunc;

/**
 * @author 14512 on 2018/2/8
 */

public class Mode implements IMode {

    @Override
    public void getWeather(ApiSubscriber<WeatherData> apiSubscriber, String city) {
        RetrofitHelper.createApi(ApiService.WEATHER, ApiService.class)
                .getWeather(city, C.WEATHER_KEY)
                .compose(SchedulerTransformer.transformer())
                .onErrorResumeNext(new HttpResponseFunc<>())
                .subscribe(apiSubscriber);
    }

    @Override
    public void getUserInfo(ApiSubscriber<UserInfo> apiSubscriber, String phone) {
        RetrofitHelper.getInstance().getUserInfo(phone).subscribe(apiSubscriber);
    }

}
