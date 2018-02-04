package com.example.a14512.discover.modules.login.view.imp;

/**
 * @author 14512 on 2018/2/2
 */

public interface ILoginView {

    /**
     * @return account
     */
    String getAccount();

    /**
     * @return pwd
     */
    String getPwd();

    /**
     * 判断是否登录成功
     */
    void isLogin();
}
