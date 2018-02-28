package com.example.a14512.discover.modules.login.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.presenter.RegisterPresenterImp;
import com.example.a14512.discover.modules.login.view.imp.IRegisterView;
import com.example.a14512.discover.utils.KeyBoardUtil;
import com.example.a14512.discover.utils.ObservableTimer;


/**
 * @author 14512 on 2018/2/1
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterView {

    private LinearLayout mainLayout;
    private Toolbar mToolbar;
    private ImageView mLeft;
    private TextView mTitle;
    private EditText mPhoneNum;
    private EditText mCode;
    private EditText mPwd;
    private Button btnGetCode;

    private boolean isShowPwd = false;
    private RegisterPresenterImp mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initToolbar();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackground(null);
        mTitle.setText(R.string.btn_register);
        mLeft.setOnClickListener(v -> finish());
    }

    private void initView() {
        mainLayout = findViewById(R.id.layout_register);
        mToolbar = findViewById(R.id.toolbar);
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mPhoneNum = findViewById(R.id.edt_register_number);
        mPwd = findViewById(R.id.edt_register_pwd);
        mCode = findViewById(R.id.edt_code);
        btnGetCode = findViewById(R.id.btn_get_code);
        ImageView showPwd = findViewById(R.id.img_register_pwd_show);
        Button btnRegister = findViewById(R.id.btn_register);
        TextView toLogin = findViewById(R.id.tv_register_login);

        mainLayout.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        showPwd.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        toLogin.setOnClickListener(this);

        mPresenter = new RegisterPresenterImp(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_register:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                break;
            case R.id.btn_get_code:
                mPresenter.getCode();
                break;
            case R.id.btn_register:
                mPresenter.register();
                break;
            case R.id.img_register_pwd_show:
                isShowPwd();
                break;
            case R.id.tv_register_login:
                finish();
                break;
            default:
                break;
        }
    }

    private void isShowPwd() {
        if (!isShowPwd) {
            mPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isShowPwd = true;
        } else {
            mPwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isShowPwd = false;
        }
    }

    @Override
    public String getPhoneNum() {
        return mPhoneNum.getText().toString();
    }

    @Override
    public String getCode() {
        return mCode.getText().toString();
    }

    @Override
    public String getPwd() {
        return mPwd.getText().toString();
    }

    @Override
    public void isRegister() {
//        startIntentActivity(this, MainActivity.class);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showCodeTime() {
        new ObservableTimer(btnGetCode).startTimer();
    }
}
