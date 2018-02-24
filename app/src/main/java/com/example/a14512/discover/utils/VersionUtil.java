package com.example.a14512.discover.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author 14512 on 2018/2/24
 */

public class VersionUtil {

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static float getVersionCode(Context context) {
        float versionCode = 0;
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
