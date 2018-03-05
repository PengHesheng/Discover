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


    /**
     * 其他信息
     * 日志文件的日期信息
     */
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
    public static final int PERSONALITY_ADVICE = 6;
    public static final int LOGIN = 7;
    public static final int LOGIN_OUT = 8;
    public static final int UPDATE_INFO = 9;
    public static final int CHOOSE_CITY = 10;

    /**
     * 用于从不同的地方跳转到景点活动
     */
    public static final int SCENIC_TYPE = 99;

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

    /**
     * 城市
     */
    public static final String CHONG_QING = "重庆市";

    /**
     * 天气接口APIkey
     */
    public static final String WEATHER_KEY = "ba017b1a8a1f4f0fb5912bb2295d21a9";

    /**
     * 存储账户信息-电话号码
     */
    public static final String ACCOUNT = "account";

    /**
     * 缓存区的头像
     */
    public static final String USER_INFO = "user_info";

    /**
     * 我的路线本地缓存，保存
     */
    public static final String MY_ROUTE = "my_route";
}
