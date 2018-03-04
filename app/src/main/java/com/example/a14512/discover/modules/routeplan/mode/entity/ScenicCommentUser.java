package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicCommentUser implements Serializable {
    public String portrait;

    @SerializedName("user_name")
    public String name;

    @SerializedName("allScore")
    public String star;

    @SerializedName("userPhone")
    public String phone;

    @SerializedName("firstScore")
    public String firstStar;

    @SerializedName("secondScore")
    public String sencondStar;

    @SerializedName("thirdScore")
    public String thirdStar;

    @SerializedName("fourthScore")
    public String fourthStar;
}
