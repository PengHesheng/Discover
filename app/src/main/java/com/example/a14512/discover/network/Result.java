package com.example.a14512.discover.network;

/**
 * 请求数据返回类型
 * @author  14512 on 2017/8/15.
 */

public class Result<T> {

    private int status;
    private String info;
    private T data;

    public Result() {
    }

    public Result(int status, String info, T data) {
        this.status = status;
        this.info = info;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
