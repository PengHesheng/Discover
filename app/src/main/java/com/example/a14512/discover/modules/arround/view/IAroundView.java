package com.example.a14512.discover.modules.arround.view;

import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public interface IAroundView {


    /**
     * 返回所有的景点信息
     * @param scenics
     */
    void setScenics(ArrayList<Scenic> scenics);
}
