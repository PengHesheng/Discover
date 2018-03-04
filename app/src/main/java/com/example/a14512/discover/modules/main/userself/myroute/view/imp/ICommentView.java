package com.example.a14512.discover.modules.main.userself.myroute.view.imp;

import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.ScenicScore;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/28
 */

public interface ICommentView {

    /**
     * 获取所有景点的评分
     * @return
     */
    ArrayList<ScenicScore> getScenicScore();

    /**
     * 结束活动
     */
    void finishActivity();
}
