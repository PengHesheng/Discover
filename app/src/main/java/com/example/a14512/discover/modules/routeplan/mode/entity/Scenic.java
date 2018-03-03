package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/1/29
 */

public class Scenic implements Serializable {

    /**
     * place_time : 70
     * place_popularity : 62
     * place_lat : 29.509211
     * place_name : 重庆动物园
     * place_photo : null
     * place_lng : 106.512557
     * place_introduce : 4A级景区 亲子好去处
     * place_price : 20
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
