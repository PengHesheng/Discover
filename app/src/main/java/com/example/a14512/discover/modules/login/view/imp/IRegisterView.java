package com.example.a14512.discover.modules.login.view.imp;

/**
 * @author 14512 on 2018/2/2
 */

public interface IRegisterView {

    /**
     * @return 电话号码
     */
    String getPhoneNum();

    /**
     * @return 获取验证码
     */
    int getCode();

    /**
     * @return 获取密码
     */
    String getPwd();

    /**
     * 判断是否注册成功
     */
    void isRegister();

    /**
     * 显示验证码倒计时
     */
    void showCodeTime();
}
