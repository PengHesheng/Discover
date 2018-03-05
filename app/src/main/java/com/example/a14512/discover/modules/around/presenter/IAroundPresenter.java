package com.example.a14512.discover.modules.around.presenter;

/**
 * @author 14512 on 2018/2/8
 */

public interface IAroundPresenter {

    /**
     * 获取所有的景点信息
     */
    void getSecnics();

    /**
     * 获取一定类型的景点信息
     * @param type
     */
    void getTypeScenics(int type);
}
