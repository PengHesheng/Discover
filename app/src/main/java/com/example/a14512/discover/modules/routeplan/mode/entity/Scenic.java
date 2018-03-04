package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/1/29
 */

public class Scenic implements Serializable {

    /**
     * place_name   String  地点名称
     * place_time    int    建议游玩时间
     * place_popularity    int     景点人气
     * place_introduce  String  景点介绍
     * place_price 	int      景点价格
     * place_photo       String 景点照片
     * times       int     -1上午，0下午，1晚上
     * place_lng   double   景点经度
     * place_lat   double   景点纬度
     * place_type（景点类型） int
     * place_adress（景点地址）String
     * place_Times（活跃时间参数） String  不用管
     */

    @SerializedName("place_photo")
    public String img;

    @SerializedName("place_name")
    public String name;

    @SerializedName("place_time")
    public int time;

    @SerializedName("place_popularity")
    public int monthAver;

    @SerializedName("place_price")
    public int peopleAver;

    @SerializedName("place_adress")
    public String location;

    @SerializedName("place_introduce")
    public String content;

    @SerializedName("place_lat")
    public double latitude;

    @SerializedName("place_lng")
    public double longitude;

    @SerializedName("times")
    public String timeType;

    @SerializedName("place_type")
    public String type;

    @SerializedName("place_Times")
    public String openTime;

}
