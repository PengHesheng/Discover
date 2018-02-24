package com.example.a14512.discover.modules.main.userself.changeuser.view;

/**
 * @author 14512 on 2018/2/14
 */

public interface IChangeUserInfoView {

    /**
     * 结束活动
     */
    void finishActivity();

    /**
     * 获取用户名昵称
     * @return
     */
    String getName();

    /**
     * 获取性别
     * @return
     */
    String getSex();

    /**
     * 获取区域
     * @return
     */
    String getCity();

    /**
     * 获取签名
     * @return
     */
    String getSigned();

    /**
     * 获取邮箱
     * @return
     */
    String getEmail();

    /**
     * 显示昵称
     * @param name
     */
    void setName(String name);

    /**
     * 显示性别
     * @param sex
     */
    void setSex(String sex);

    /**
     * 显示区域
     * @param city
     */
    void setCity(String city);

    /**
     * 显示签名
     * @param signed
     */
    void setSigned(String signed);

    /**
     * 显示邮箱
     * @param email
     */
    void setEmail(String email);

    /**
     * 显示头像
     * @param url
     */
    void setPortrait(String url);
}
