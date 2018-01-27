package com.example.a14512.discover.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a14512.discover.C;


/**
 * @author lilei on 2017/8/14.
 */

public class SharedPreferencesUtil {

    private final static String OTHER_INFO = "other_info";

    private static Context context;



    public static void init(Context context) {
        SharedPreferencesUtil.context = context;
    }

    //-----------------OTHER_INFO--------------------------
    public static void setLogDate(String logDate) {
        SharedPreferences.Editor editor = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(C.LOG_DATE, logDate);
        editor.apply();
    }

    public static String getLogDate() {
        SharedPreferences preferences = context.getSharedPreferences(OTHER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(C.LOG_DATE, "");
    }


}
