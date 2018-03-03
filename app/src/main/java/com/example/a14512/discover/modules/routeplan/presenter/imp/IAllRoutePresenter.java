package com.example.a14512.discover.modules.routeplan.presenter.imp;

/**
 * @author 14512 on 2018/3/2
 */

public interface IAllRoutePresenter {

    /**
     * 路线信息
     * @param information
     */
    void addMyRoute(String information);

    /**
     * 结束路线
     * @param routeName
     */
    void endRoute(String routeName);
}
