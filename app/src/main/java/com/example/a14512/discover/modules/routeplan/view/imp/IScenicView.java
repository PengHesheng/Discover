package com.example.a14512.discover.modules.routeplan.view.imp;

import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public interface IScenicView {

    /**
     * 评分
     * @param users
     */
    void setCommentAdapter(ArrayList<ScenicCommentUser> users);

    /**
     * 旅行购物
     * @param consumeModes
     */
    void setConsumeAdapter(ArrayList<ConsumeMode> consumeModes);

    /**
     * 攻略
     * @param strategyModes
     */
    void setStrategyAdapter(ArrayList<StrategyMode> strategyModes);

    /**
     * 是否关注成功
     * @param result
     */
    void isFollow(int result);
}
