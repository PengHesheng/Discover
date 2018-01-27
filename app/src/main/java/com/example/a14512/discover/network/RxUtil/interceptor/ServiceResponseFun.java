package com.example.a14512.discover.network.RxUtil.interceptor;

import android.support.annotation.NonNull;

import com.example.a14512.discover.network.Result;
import com.example.a14512.discover.network.RxUtil.exception.ServiceException;

import io.reactivex.functions.Function;


/**
 * 剥离对请求参数的判断，直接返回数据
 * @author by 14512 on 2017/8/15.
 */

public class ServiceResponseFun<T> implements Function<Result<T>, T> {

    @Override
    public T apply(@NonNull Result<T> tResult) throws Exception {
        if (tResult.getStatus() != 200) {
            throw new ServiceException(tResult.getStatus(), tResult.getInfo());
        }
        return tResult.getData();
    }
}
