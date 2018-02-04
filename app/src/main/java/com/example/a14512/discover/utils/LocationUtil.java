package com.example.a14512.discover.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * @author 14512 on 2018/2/4
 */

public class LocationUtil {
    private LocationClient mLocationClient;
    private static LocationUtil sLocationUtil;

    public static LocationUtil getInstance() {
        if (sLocationUtil == null) {
            synchronized (LocationUtil.class) {
                sLocationUtil = new LocationUtil();
            }
        }
        return sLocationUtil;
    }

    public void getLocation(Context context, BDAbstractLocationListener listener) {
        mLocationClient = new LocationClient(context);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //可选，设置是否使用gps，默认false
        option.setLocationNotify(true);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(listener);
        mLocationClient.start();
    }

    public void unRegisterListener(BDAbstractLocationListener listener) {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(listener);
    }
}
