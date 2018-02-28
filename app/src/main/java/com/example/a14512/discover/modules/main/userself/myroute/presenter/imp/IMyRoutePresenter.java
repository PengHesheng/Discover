package com.example.a14512.discover.modules.main.userself.myroute.presenter.imp;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMyRoutePresenter {

    /**
     * 获取历史路线
     */
    void getHistoricRouteFromACache();

    /**
     * 获取我的收藏
     */
    void getMyCollectFromACache();

    /**
     * 获取我的路线
     */
    void getMtRoute();

    /**
     * @return
     */
    boolean isACache();
}
