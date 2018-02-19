package com.example.a14512.discover.modules.routeplan.presenter.imp;

/**
 * @author 14512 on 2018/2/1
 */

public interface IScenicPresenter {

    /**
     *获取评论
     * @param place
     */
    void getData(String place);

    /**
     * 关注景点
     * @param placeName
     * @param placeFollow
     */
    void followScenic(String placeName, int placeFollow);
}
