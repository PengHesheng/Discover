package com.example.a14512.discover.modules.main.mode;

import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMode {

    /**
     * @param apiSubscriber
     */
    void getWeather(ApiSubscriber<WeatherData> apiSubscriber, String city);
}
