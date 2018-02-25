package com.example.a14512.discover.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator on 2016/1/22.
 */
public class DateFormatUtil {

    public static final String DEFAULT_FORMAT = "yyyy年MM月dd日";


    private static final String TAG = DateFormatUtil.class.getSimpleName();

    /**
     * 将服务器返回的时间戳转化为需要的日期格式
     *
     * @param format 需要的日期格式 如 "yyyy-MM-dd HH:mm:ss"
     * @param timestamp 时间戳
     */
    public static String formatDate(String format, Long timestamp) {

        try {
            SimpleDateFormat f = new SimpleDateFormat(format == null ? DEFAULT_FORMAT : format);
            return f.format(timestamp);
        }catch (Exception e){
            PLog.e("formatDate?"+e.toString());
        }
        return "-1";
    }

    /**
     * 将字符串转化成时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    public static String getTimer(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        re_time = re_time + "000";
        return re_time;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }

    public static String getNowTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date);
        return time;
    }

    /**
     * 将秒转换为时-分-秒 此为时间长度，不为时间
     * @param duration
     * @return
     */
    public static String tranceSecondToTime(int duration) {
        String str;
        int hour = duration / 3600;
        int minute = (duration - hour * 3600) / 60;
        int second = duration - hour * 3600 - minute * 60;
        if (hour == 0) {
            if (minute == 0) {
                str = second + "秒";
            } else {
                if (second == 0) {
                    str = minute + "分";
                } else {
                    str = minute + "分" + second + "秒";
                }
            }
        } else {
            if (minute == 0) {
                if (second == 0) {
                    str = hour + "小时";
                } else {
                    str = hour + "小时" + second + "秒";
                }
            } else {
                if (second == 0) {
                    str = hour + "小时" + minute + "分";
                } else {
                    str = hour + "小时" + minute + "分" + second + "秒";
                }
            }
        }
        return str;
    }

    /**
     * 计算时间差，并以分钟数返回
     * @param startTime 2017/02/2118:30
     * @param endTime 2017/02/2220:30
     * @return minutes
     */
    public static int calculateMinute(String startTime, String endTime) {
        int value;
        String startDate = startTime.substring(0, startTime.length() - 5);
        String startT = startTime.substring(startTime.length() - 5, startTime.length());
        String endDate = endTime.substring(0, endTime.length() - 5);
        String endT = endTime.substring(endTime.length() - 5, endTime.length());
        int startDay = Integer.parseInt(startDate.substring(startDate.length() - 2, startDate.length()));
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int startMonth = Integer.parseInt(startDate.substring(5, 7));
        int startH = Integer.parseInt(startT.substring(0, 2));
        int startM = Integer.parseInt(startT.substring(startT.length() - 2, startT.length()));

        int endDay = Integer.parseInt(endDate.substring(endDate.length() - 2, endDate.length()));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        int endMonth = Integer.parseInt(endDate.substring(5, 7));
        int endH = Integer.parseInt(endT.substring(0, 2));
        int endM = Integer.parseInt(endT.substring(endT.length() - 2, endT.length()));

        if (startYear > endYear || startMonth > endMonth || startDay > endDay) {
            return -1;
        }
        if (endDay - startDay >= 3) {
            return -1;
        }
        if (endDay > startDay) {
            endH += 24;
        }
        if (endM < startM) {
            endH--;
            endM += 60;
        }
        value = (endH - startH) * 60 + endM - startM;
        return value;
    }



}
