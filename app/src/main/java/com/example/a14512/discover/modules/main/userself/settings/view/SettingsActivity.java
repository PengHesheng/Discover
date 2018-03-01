package com.example.a14512.discover.modules.main.userself.settings.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/6
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mLeft.setImageResource(R.mipmap.left_back);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText(R.string.tv_menu_setting);
        mTitle.setTextColor(getResources().getColor(R.color.titleBlack));
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        RelativeLayout advice = findViewById(R.id.layout_setting_advice);
        RelativeLayout about = findViewById(R.id.layout_setting_about);
        RelativeLayout help = findViewById(R.id.layout_setting_help);
        RelativeLayout joinUs = findViewById(R.id.layout_setting_join_us);
        TextView loginOut = findViewById(R.id.tv_setting_login_out);

        advice.setOnClickListener(this);
        about.setOnClickListener(this);
        help.setOnClickListener(this);
        joinUs.setOnClickListener(this);
        loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_setting_advice:
                popupWindowAdvice();
                mPopupWindow.showAtLocation(v, Gravity.CENTER, 0 ,0);
                break;
            case R.id.layout_setting_about:
                popupWindowAbout();
                mPopupWindow.showAtLocation(v, Gravity.CENTER, 0 ,0);
                break;
            case R.id.layout_setting_help:
                startIntentActivity(this, HelpActivity.class);
                break;
            case R.id.layout_setting_join_us:
                popupWindowJoinUs();
                mPopupWindow.showAtLocation(v, Gravity.CENTER, 0 ,0);
                break;
            case R.id.tv_setting_login_out:
                loginOut();
                break;
            default:
                break;
        }
    }

    private void popupWindowJoinUs() {
        View joinUs = getLayoutInflater().inflate(R.layout.window_popup_join_us, null);
        int height = getWindowManager().getDefaultDisplay().getHeight() / 3;
        int width = getWindowManager().getDefaultDisplay().getWidth() * 5 / 8;
        mPopupWindow = new PopupWindow(joinUs, width,
                height, true);
        popupWindow();
    }

    private void popupWindowAbout() {
        View joinUs = getLayoutInflater().inflate(R.layout.window_popup_about, null);
        int height = getWindowManager().getDefaultDisplay().getHeight() * 2 / 3;
        int width = getWindowManager().getDefaultDisplay().getWidth() * 5 / 8;
        mPopupWindow = new PopupWindow(joinUs, width, height, true);
        popupWindow();
    }

    private void popupWindowAdvice() {
        View joinUs = getLayoutInflater().inflate(R.layout.window_popup_advice, null);
        int height = getWindowManager().getDefaultDisplay().getHeight() / 4;
        int width = getWindowManager().getDefaultDisplay().getWidth() * 5 / 8;
        mPopupWindow = new PopupWindow(joinUs, width, height, true);
        popupWindow();
    }

    private void popupWindow() {
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    private void loginOut() {
        if (C.ACCOUNT.equals(ACache.getDefault().getAsString(C.ACCOUNT))) {
            ToastUtil.show(this, "已经退出了！");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确定退出吗？")
                    .setCancelable(false)
                    .setPositiveButton("是", (dialog, id) -> {
                        ACache.getDefault().put(C.ACCOUNT, C.ACCOUNT);
                        ToastUtil.show(this, "退出登录成功！");
                        setResult(RESULT_OK);
                    })
                    .setNegativeButton("否", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
