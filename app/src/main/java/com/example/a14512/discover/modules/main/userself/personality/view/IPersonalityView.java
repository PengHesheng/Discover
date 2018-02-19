package com.example.a14512.discover.modules.main.userself.personality.view;

/**
 * @author 14512 on 2018/2/19
 */

public interface IPersonalityView {

    /**
     * 显示第一个RadioButton
     * @param personality1
     */
    void setPersonality1(String personality1);

    /**
     * 第二个
     * @param personality2
     */
    void setPersonality2(String personality2);

    /**
     * 第三个
     * @param personality3
     */
    void setPersonality3(String personality3);

    /**
     * 第四个
     * @param personality4
     */
    void setPersonality4(String personality4);

    /**
     * 第五个
     * @param personality5
     */
    void setPersonality5(String personality5);

    /**
     * 获取第一个
     * @return
     */
    String getPersonality1();

    /**
     * 第二个
     * @return
     */
    String getPersonality2();

    /**
     * 第三个
     * @return
     */
    String getPersonality3();

    /**
     * 第四个
     * @return
     */
    String getPersonality4();

    /**
     * 第五个
     * @return
     */
    String getPersonality5();

    /**
     * 结束活动
     */
    void finishActivity();
}
