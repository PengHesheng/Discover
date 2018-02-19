package com.example.a14512.discover.modules.main.presenter;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMainPresenter {

    /**
     * 获取天气数据信息
     */
    void getWeather(String city);

    /**
     * 判断是否登录
     */
    void isLogin();

    /**
     * 获取个人信息
     */
    void getUserInfo();
}
