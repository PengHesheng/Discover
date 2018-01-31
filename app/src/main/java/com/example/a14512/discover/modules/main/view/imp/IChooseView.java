package com.example.a14512.discover.modules.main.view.imp;

/**
 * @author 14512 on 2018/1/28
 */

public interface IChooseView {


    /**
     * @return string
     */
    String getStartPlace();

    /**
     * @return string
     */
    String getEndPlace();

    /**
     * @return true or false
     */
    boolean isSortDistance();

    /**
     * @return true or false
     */
    boolean isLessPay();

    /**
     * @return true or false
     */
    boolean isLongPlay();

    /**
     * @return true or false
     */
    boolean isHighComment();

    /**
     * @return true or false
     */
    boolean isRecommend();

    /**
     * @return string
     */
    String getStartTime();

    /**
     * @return string
     */
    String getEndTime();

}