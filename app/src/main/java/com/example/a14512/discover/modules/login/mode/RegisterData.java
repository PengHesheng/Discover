package com.example.a14512.discover.modules.login.mode;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author 14512 on 2018/3/3
 */

public class RegisterData implements Serializable {

    /**
     * message : 短信发送成功
     * textCode : 832251
     */

    private String message;
    private int textCode;

    public static RegisterData objectFromData(String str) {

        return new Gson().fromJson(str, RegisterData.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTextCode() {
        return textCode;
    }

    public void setTextCode(int textCode) {
        this.textCode = textCode;
    }
}
