package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/1/29
 */

public class Scenic implements Serializable {
    public String img;

    @SerializedName("place_name")
    public String name;

    @SerializedName("place_time")
    public int time;

    @SerializedName("place_popularity")
    public int monthAver;

    @SerializedName("place_price")
    public int peopleAver;
    public String location;

    @SerializedName("place_introduce")
    public String content;

    @SerializedName("place_lat")
    public double latitude;

    @SerializedName("place_lng")
    public double longitude;

    @SerializedName("times")
    public String times;

}
