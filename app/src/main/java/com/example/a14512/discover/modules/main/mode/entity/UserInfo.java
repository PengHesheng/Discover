package com.example.a14512.discover.modules.main.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 14512 on 2018/2/14
 */

public class UserInfo implements Serializable {

    @SerializedName("userName")
    public String name;

    @SerializedName("userPhoto")
    public String portrait;

    @SerializedName("userSex")
    public String sex;

    public String city;

    @SerializedName("userSign")
    public String signed;

    @SerializedName("userEmail")
    public String email;

    @SerializedName("userSchool")
    public String school;

    @SerializedName("userPersonal")
    public String personal;

    @SerializedName("userBirthday")
    public String birthday;
}
