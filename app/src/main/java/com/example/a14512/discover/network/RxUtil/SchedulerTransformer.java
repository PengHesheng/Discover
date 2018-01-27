package com.example.a14512.discover.network.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 14512 on 2017/8/15.
 */

public class SchedulerTransformer {

    public static <T> ObservableTransformer<T,T> transformer() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }



//以下逻辑暂时不用，测试网络接口如果出现问题，再使用
    /*public static <T> ObservableTransformer<Result<T>, T> handleResult() {
        return upstream -> upstream.flatMap(tResult -> {
            if (tResult.getStatus() == 1) {
                return createData(tResult.getGankFromNet());
            } else {
                return Observable.error(new ApiException(tResult.getStatus() ,tResult.getInfo()));
            }
        });
    }

    public static <T> ObservableTransformer<Result<T>,String> handleResultToMsg() {
        return upstream -> upstream.flatMap(tResult -> {
            if (tResult.getStatus() == 1) {
                return createData(tResult.getInfo());
            } else {
                return Observable.error(new ApiException(tResult.getStatus() ,tResult.getInfo()));
            }
        });
    }*/

    private static <T> Observable<T> createData(final T data){
        return Observable.create(e -> {
            try {
                e.onNext(data);
                e.onComplete();
            } catch (Exception a) {
                e.onError(a);
            }
        });
    }

    private static Observable<String> createData(final String data) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            /**
             * Called for each Observer that subscribes.
             *
             * @param e the safe emitter instance, never null
             * @throws Exception on error
             */
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception a) {
                    e.onError(a);
                }
            }
        });
    }

}
