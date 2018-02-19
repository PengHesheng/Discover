package com.example.a14512.discover.modules.main.userself.attention.presenter;

/**
 * @author 14512 on 2018/2/8
 */

public interface IMyAttentionPresenter {

    /**
     * 获取我的关注
     */
    void getMyFollow();

    /**
     * 判断是否有缓存
     * @return
     */
    boolean getMyFollowFromACache();
}
