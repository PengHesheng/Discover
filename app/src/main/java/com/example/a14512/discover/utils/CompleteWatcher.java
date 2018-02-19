package com.example.a14512.discover.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 为填写资料的activity创建一个抽象类,用于监听用户是否完成应该填写的资料,填写完成才能提交
 * 抽象方法中写上activity的检测方法,这样就能实时监控用户是否完成
 * Created by 14512 on 2017/8/3.
 */

public abstract class CompleteWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ifCompleteWatcher();
    }

    public abstract void ifCompleteWatcher();
}