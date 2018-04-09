package com.example.a14512.discover.network.RxUtil.exception;

/**
 * Created by 14512 on 2017/8/15.
 */

public class ApiException extends Exception {

    private int code;
    private String displayMessage;

    public ApiException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

}
