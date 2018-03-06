package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author 14512 on 2018/3/5
 */

public class ConsumeMode {

    @SerializedName("Goodsphoto")
    public String img = null;

    @SerializedName("GoodsName")
    public String name = null;

    @SerializedName("GoodsIntroduce")
    public String content = null;

    public String category = null;

    @SerializedName("GoodsPrice")
    public String money = null;
}
