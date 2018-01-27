package com.example.a14512.discover.network.RxUtil.exception;

/**
 * Created by 14512 on 2017/8/15.
 */

public class ServiceException extends RuntimeException {

    private int code;
    private String message;

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }
}
