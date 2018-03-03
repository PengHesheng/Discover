package com.example.a14512.discover.modules.routeplan.view.imp;

/**
 * @author 14512 on 2018/3/2
 */

public interface IGoGuideView {

    /**
     * 获取路线名称
     * @return
     */
    String getRouteName();

    /**
     * 获取中转站个数
     * @return
     */
    int getPlaceNum();

    /**
     * 获取总距离
     * @return
     */
    int getAllDistance();

    /**
     * 获取总时间
     * @return
     */
    int getRouteTime();

    /**
     * 获取总花费
     * @return
     */
    int getAllCoast();

}
