package com.example.a14512.discover.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author 14512 on 2018/1/26
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置状态栏颜色
     * Android4.4以上可用
     */
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintResource(color);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * 设置透明状态栏
     */
    public void setDecorView() {
        //透明式状态栏以及导航栏
        if (Build.VERSION.SDK_INT >= 21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //将状态栏设置成透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 活动跳转
     * @param activity1 当前活动
     * @param cls 目标活动
     */
    public void  startIntentActivity(BaseActivity activity1, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity1, cls);
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param activity1 当前活动
     * @param cls 目标活动
     * @param name
     * @param value
     */
    public void startIntentActivity(BaseActivity activity1,Class<?> cls, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(activity1, cls);
        startActivity(intent);
    }

    /**
     * @param activity1
     * @param cls
     * @param name
     * @param value
     */
    public void startIntentActivity(BaseActivity activity1,Class<?> cls, String name, int value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(activity1, cls);
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param activity1 当前活动
     * @param cls 目标活动
     * @param name1
     * @param value1
     * @param name2
     * @param value2
     */
    public void startIntentActivity(BaseActivity activity1,Class<?> cls, String name1, String value1, String name2, String value2){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, value2);
        intent.setClass(activity1, cls);
        startActivity(intent);
    }

}
