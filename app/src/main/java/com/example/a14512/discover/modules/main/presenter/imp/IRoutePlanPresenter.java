package com.example.a14512.discover.modules.main.presenter.imp;

/**
 * @author 14512 on 2018/1/29
 */

public interface IRoutePlanPresenter {

    /**
     *
     */
    void getData();

    /**
     * @param category
     * @param changePlace
     * @param position
     * @param last
     * @param next
     * @param personSelect
     */
    void deleteOneData(String category, String changePlace, int position,
                       String last, String next, int personSelect);
}
