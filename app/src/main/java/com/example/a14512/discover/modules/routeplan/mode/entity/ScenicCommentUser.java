package com.example.a14512.discover.modules.routeplan.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicCommentUser implements Serializable {
    public String portrait;

    @SerializedName("userName")
    public String name;

    @SerializedName("comment")
    public int star;

    @SerializedName("userPhone")
    public String phone;
}
