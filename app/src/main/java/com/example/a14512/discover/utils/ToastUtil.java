package com.example.a14512.discover.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.a14512.discover.DiscoverApplication;

/**
 * @author lilei on 2017/8/14.
 */

public class ToastUtil {

    public static void show(String content) {
        Toast.makeText(DiscoverApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int res) {
        Toast.makeText(DiscoverApplication.getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String content) {
        Toast.makeText(DiscoverApplication.getContext(), content, Toast.LENGTH_LONG).show();
    }

    public static void showLong(@StringRes int res) {
        Toast.makeText(DiscoverApplication.getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    public static void showLong(Context context, @StringRes int res) {
        Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
    }
}

