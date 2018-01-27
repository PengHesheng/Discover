package com.example.a14512.discover.network.RxUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 14512 on 2017/8/15.
 */

public abstract class ApiSubscriber<T> implements DialogInterface.OnCancelListener, Observer<T> {

    private static final String TAG = "ApiSubscriber";

    private WeakReference<Context> contextWeakReference;
    private ProgressDialog dialog;
    private boolean cancelable = false;  //是否可以取消
    private boolean isShow;
    private Disposable disposable;

    public ApiSubscriber(Context context, boolean isShow, boolean cancelable) {
        this.contextWeakReference = new WeakReference<Context>(context);
        this.cancelable = cancelable;
        this.isShow = isShow;
        initProgressDialog();
    }

    public ApiSubscriber(Context context, boolean isShow, boolean cancelable, String message) {
        this.contextWeakReference = new WeakReference<Context>(context);
        this.cancelable = cancelable;
        this.isShow = isShow;
        initProgressDialog(message);
    }

    public ApiSubscriber(Context context, boolean cancelable, @NonNull ProgressDialog dialog) {
        this.contextWeakReference = new WeakReference<Context>(context);
        this.dialog = dialog;
        this.cancelable = cancelable;
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(this);
    }

    private void initProgressDialog() {
        Context context = this.contextWeakReference.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("加载中...");
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(this);
        }
    }


    private void initProgressDialog(String message) {
        Context context = this.contextWeakReference.get();
        if (dialog == null && context != null) {
            dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(this);
        }
    }

    private void showProgressDialog() {
        Context context = this.contextWeakReference.get();
        if (dialog == null || context == null || !isShow) {
            return;
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        showProgressDialog();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onError(Throwable e) {
//        ApiException ex = (ApiException) e;
//        Context context = this.contextWeakReference.get();
//        if (context != null) {
//            ToastUtil.show(ex.getDisplayMessage());
//        }
//        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }
}
