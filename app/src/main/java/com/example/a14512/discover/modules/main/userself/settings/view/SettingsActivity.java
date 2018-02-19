package com.example.a14512.discover.modules.main.userself.settings.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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
    private RelativeLayout mAdvice;
    private RelativeLayout mAbout;
    private RelativeLayout mHelp;
    private RelativeLayout mJoinUs;
    private TextView mLoginOut;

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
        mAdvice = findViewById(R.id.layout_setting_advice);
        mAbout = findViewById(R.id.layout_setting_about);
        mHelp = findViewById(R.id.layout_setting_help);
        mJoinUs = findViewById(R.id.layout_setting_join_us);
        mLoginOut = findViewById(R.id.tv_setting_login_out);

        mAdvice.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mJoinUs.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_setting_advice:
                startIntentActivity(this, AdviceActivity.class);
                break;
            case R.id.layout_setting_about:
                startIntentActivity(this, AboutActivity.class);
                break;
            case R.id.layout_setting_help:
                startIntentActivity(this, HelpActivity.class);
                break;
            case R.id.layout_setting_join_us:
                startIntentActivity(this, JoinUsActivity.class);
                break;
            case R.id.tv_setting_login_out:
                loginOut();
                break;
            default:
                break;
        }
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
