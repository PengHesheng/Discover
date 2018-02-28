package com.example.a14512.discover.modules.main.userself.myroute.view.imp;

import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMyRouteView {

    /**
     * 展示历史路线
     * @param routes
     */
    void setHistoricRoute(ArrayList<MyRoute> routes);

    /**
     * 展示我的收藏
     * @param routes
     */
    void setMyCollect(ArrayList<MyRoute> routes);
}
