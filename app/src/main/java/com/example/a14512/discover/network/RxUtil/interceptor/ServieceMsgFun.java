package com.example.a14512.discover.network.RxUtil.interceptor;

import android.support.annotation.NonNull;

import com.example.a14512.discover.network.Result;

import io.reactivex.functions.Function;


/**
 * @author 14512 on 2017/11/7
 */

public class ServieceMsgFun<T> implements Function<Result<T>, String> {
    @Override
    public String apply(@NonNull Result<T> tResult) throws Exception {
        return tResult.getInfo();
    }
}
