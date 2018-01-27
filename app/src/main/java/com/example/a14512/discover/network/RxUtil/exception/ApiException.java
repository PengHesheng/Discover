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

   /* public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(int resultCode, String msg) {
        this(handleErrorCode(resultCode,msg));
    }

    private static String handleErrorCode(int code, String s) {
        String msg;
        switch (code) {
            case 400:
                msg = "请登录后再试";
                break;
            default:
                msg = s;
                break;
        }
        return msg;
    }*/
}
