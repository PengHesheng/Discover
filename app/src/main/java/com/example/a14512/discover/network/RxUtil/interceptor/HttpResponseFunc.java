package com.example.a14512.discover.network.RxUtil.interceptor;


import com.example.a14512.discover.network.RxUtil.exception.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by 14512 on 2017/8/15.
 */

public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandle.handleException(throwable));
    }
}
