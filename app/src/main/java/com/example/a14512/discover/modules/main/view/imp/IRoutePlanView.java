package com.example.a14512.discover.modules.main.view.imp;

import com.example.a14512.discover.modules.main.modle.Scenic;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/29
 */

public interface IRoutePlanView {

    /**
     * @param scenics
     * @param category
     */
    void setAdapter(ArrayList<Scenic> scenics, String category);

    /**
     * @param position
     * @param scenic
     * @param category
     */
    void setOneData(int position, Scenic scenic, String category);

}
