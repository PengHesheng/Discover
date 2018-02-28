package com.example.a14512.discover.modules.main.userself.myroute.presenter.imp;

/**
 * @author 14512 on 2018/2/28
 */

public interface ICommentPresenter {

    /**
     * 保存评分
     * @param placeName
     */
    void setScore(String placeName);

    /**
     * 关注
     * @param placeName
     * @param placeFollow
     */
    void followScenic(String placeName, int placeFollow);
}
