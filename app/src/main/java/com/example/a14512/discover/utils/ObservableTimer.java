package com.example.a14512.discover.utils;

import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xcc on 2016/1/21 0021.
 * 验证码倒数
 */
public class ObservableTimer {
    private Button button;
    private String TAG;
    public ObservableTimer(Button button, String TAG) {
        this.button = button;
        this.TAG = TAG;
    }
    public ObservableTimer(Button button) {
        this.button = button;
    }

    public void startTimer() {
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(60).map(aLong -> 60-aLong)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        button.setClickable(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        button.setText(String.format("重新获取(%ss)", value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        PLog.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        button.setClickable(true);
                        button.setText("重新发送验证码");
                    }
                });
    }
}