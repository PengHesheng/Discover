package com.example.a14512.discover.modules.login.mode;

import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/2
 */

public interface Imode {

    /**
     * 判断是否登录
     * @param apiSubscriber
     * @param phone
     * @param pwd
     */
    void igLogin(ApiSubscriber<String> apiSubscriber, String phone, String pwd);

    /**
     * 判断是否注册
     * @param apiSubscriber
     * @param phone
     * @param pwd
     */
    void register(ApiSubscriber<Integer> apiSubscriber, String phone, String pwd);

    /**
     * 获取验证码
     * @param apiSubscriber
     * @param phone
     * @param pwd
     */
    void getCode(ApiSubscriber<RegisterData> apiSubscriber, String phone, String pwd);
}
