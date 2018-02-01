package com.example.a14512.discover;

/**
 * @author 14512 on 2018/1/27
 */

public class C {

    /**
     * 注意： 所有Sp以及Acache的键值全部分类放在此处，并且 可清除缓存 必须放在/Data文件夹下
     *      可清缓存示例  public static final String LOG_DATE = "Data/log_date";
     *      同时，退出登陆的缓存需存在/Login文件夹下
     */

    // 其他信息
    // 日志文件的日期信息
    public static final String LOG_DATE = "log_date";
    /**
     * startActivityForResult 常量
     */
    public static final int COMPLETED = 0;
    public static final int PICK_FROM_CAMERA = 1;
    public static final int CROP_FROM_CAMERA = 2;
    public static final int CROP_FROM_FILE = 3;
    public static final int PICK_FROM_FILE = 4;
    public static final int CHOOSE_CROPED_PICTURE = 5;
    public static final int UPLOAD_PICTURE = 6;

    /**
     * route plan time
     */
    public static final String MORNING = "morning";
    public static final String AFTERNOON = "afternoon";
    public static final String EVENING = "evening";

    /**
     * ChooseActivity location
     */
    public static final String LOCATION = "location";

    /**
     * RoutePlanAdapter 传递Bundle数据
     */
    public static final String SCENIC_DETAIL = "scenic_detail";

}
