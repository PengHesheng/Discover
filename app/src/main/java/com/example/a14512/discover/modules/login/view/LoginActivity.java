package com.example.a14512.discover.modules.login.view;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.presenter.LoginPresenterImp;
import com.example.a14512.discover.modules.login.view.imp.ILoginView;
import com.example.a14512.discover.modules.main.view.MainActivity;
import com.example.a14512.discover.utils.KeyBoardUtil;
import com.example.a14512.discover.utils.VersionUtil;

/**
 * @author 14512 on 2018/2/1
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    private LinearLayout mainLayout;
    private ImageView mLeft;
    private TextView mTitle;
    private Toolbar mToolbar;
    private ImageView mPortrait;
    private EditText mAccount;
    private EditText mPwd;
    private TextView rememberPwd;
    private TextView forgetPwd;


    private boolean isRemember = false, isForget = false;

    private LoginPresenterImp mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initToolbar();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackground(null);
        mTitle.setText(R.string.tv_login);
        mLeft.setOnClickListener(v -> justVersion());
    }

    private void justVersion() {
        float nowVersionCode = VersionUtil.getVersionCode(this);
        SharedPreferences sp = getSharedPreferences("welcomeInfo", MODE_PRIVATE);
        float spVersionCode = sp.getFloat("spVersionCode", 0);
        if (nowVersionCode > spVersionCode) {
            startIntentActivity(this, MainActivity.class);
            finish();
        } else {
            //非首次启动
            finish();
        }
    }

    private void initView() {
        mainLayout = findViewById(R.id.layout_login);
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mToolbar = findViewById(R.id.toolbar);

        mPortrait = findViewById(R.id.img_login_portrait);
        mAccount = findViewById(R.id.edt_login_account);
        mPwd = findViewById(R.id.edt_login_pwd);
        rememberPwd = findViewById(R.id.remember_pwd);
        forgetPwd = findViewById(R.id.forget_pwd);
        Button login = findViewById(R.id.btn_login);
        TextView goingRegister = findViewById(R.id.tv_going_register);

        mainLayout.setOnClickListener(this);
        login.setOnClickListener(this);
        goingRegister.setOnClickListener(this);
        rememberPwd.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        mPresenter = new LoginPresenterImp(this, this);
        mPresenter.getPortrait();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_login:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                break;
            case R.id.remember_pwd:
                functionIsRemember();
                break;
            case R.id.forget_pwd:
                functionIsForget();
                break;
            case R.id.btn_login:
                mPresenter.login(isRemember);
                break;
            case R.id.tv_going_register:
                startIntentActivity(this, RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    private void functionIsForget() {
        if (!isForget) {
            forgetPwd.setTextColor(getResources().getColor(R.color.loginSelect));
            isForget = true;
        } else {
            forgetPwd.setTextColor(getResources().getColor(R.color.loginUnSelect));
            isForget = false;
        }
    }

    private void functionIsRemember() {
        if (!isRemember) {
            rememberPwd.setTextColor(getResources().getColor(R.color.loginSelect));
            isRemember = true;
        } else {
            rememberPwd.setTextColor(getResources().getColor(R.color.loginUnSelect));
            isRemember = false;
        }
    }

    @Override
    public String getAccount() {
        return mAccount.getText().toString();
    }

    @Override
    public String getPwd() {
        return mPwd.getText().toString();
    }

    @Override
    public void isLogin() {
        startIntentActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void setPortrait(String portrait) {
        Glide.with(this).load(portrait).error(R.mipmap.default_portrait).into(mPortrait);
    }
}
