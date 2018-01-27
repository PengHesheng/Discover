package com.example.a14512.discover.base;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * @author 14512 on 2018/1/26
 */

public class BaseFragment extends Fragment {

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param activity 目标活动
     */
    public void startIntentActivity(Fragment fragment1, BaseActivity activity){
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(),activity.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param activity 目标活动
     */
    public void startIntentActivity(Fragment fragment1, BaseActivity activity, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(fragment1.getContext(), activity.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param fragment2 目标碎片
     */
    public void startIntentActivity(Fragment fragment1, Fragment fragment2){
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(), fragment2.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param fragment2 目标碎片
     * @param name
     * @param value
     */
    public void startIntentActivity(Fragment fragment1, Fragment fragment2, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(fragment1.getContext(), fragment2.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param context 当前上下文
     * @param activity 目标活动
     */
    public void startIntentActivity(Context context, BaseActivity activity){
        Intent intent = new Intent();
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param context 当前上下文
     * @param activity 目标活动
     * @param name
     * @param value
     */
    public void startIntentActivity(Context context, BaseActivity activity, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param activity 目标活动
     * @param requestCode
     */
    public void startIntentActivityForResult(Fragment fragment1, BaseActivity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(),activity.getClass());
        startActivityForResult(intent, requestCode);
    }

    /**
     * 活动跳转
     * @param fragment1 当前碎片
     * @param fragment2 目标碎片
     * @param requestCode
     */
    public void startIntentActivityForResult(Fragment fragment1, Fragment fragment2, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(), fragment2.getClass());
        startActivityForResult(intent, requestCode);
    }
}
