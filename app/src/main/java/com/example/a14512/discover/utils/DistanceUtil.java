package com.example.a14512.discover.utils;

/**
 * @author 14512 on 2018/2/5
 */

public class DistanceUtil {
    public static final int DISTANCE = 1000;

    public static String transformMtoKM(int distance) {
        String valueDis;
        if (distance > DISTANCE) {
            valueDis = distance / DISTANCE + "公里" + (distance - distance / DISTANCE * DISTANCE) + "米";
        } else {
            valueDis = distance + "米";
        }
        return valueDis;
    }
}
