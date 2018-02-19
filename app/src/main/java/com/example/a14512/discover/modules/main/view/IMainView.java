package com.example.a14512.discover.modules.main.view;

import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMainView {

    /**
     * 返回天气数据
     * @param weather
     */
    void setWeather(WeatherData.Weather weather);

    /**
     * 判断是否登录
     * @param isLogin
     */
    void isLogin(boolean isLogin);

    /**
     * 展示个人信息
     * @param userInfo
     */
    void setUserInfo(UserInfo userInfo);
}
