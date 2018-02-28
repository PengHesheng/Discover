package com.example.a14512.discover.modules.main.userself.myroute.view.imp;

/**
 * @author 14512 on 2018/2/28
 */

public interface ICommentView {

    /**
     * @param result
     */
    void isFollow(int result);

    /**
     * 获取景区特色评分
     * @return
     */
    int getScenicParticularStar();

    /**
     * 获取周边交通评分
     * @return
     */
    int getTrafficStar();

    /**
     * 获取综合服务评分
     * @return
     */
    int getAllServiceStar();

    /**
     * 获取历史底蕴评分
     * @return
     */
    int getHistoricStar();

    /**
     * 获取可玩度评分
     * @return
     */
    int getPlayStar();
}
