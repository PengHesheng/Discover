package com.example.a14512.discover.modules.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.presenter.LoginPresenterImp;
import com.example.a14512.discover.modules.login.view.imp.ILoginView;

/**
 * @author 14512 on 2018/2/1
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    private EditText mAccount;
    private EditText mPwd;

    private boolean isRemember = false;

    private LoginPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mAccount = findViewById(R.id.edt_login_account);
        mPwd = findViewById(R.id.edt_login_pwd);
        CheckBox rememberPwd = findViewById(R.id.remember_pwd);
        Button login = findViewById(R.id.btn_login);
        TextView goingRegister = findViewById(R.id.tv_going_register);

        login.setOnClickListener(this);
        goingRegister.setOnClickListener(this);
        rememberPwd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isRemember = isChecked;
        });
        mPresenter = new LoginPresenterImp(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        setResult(RESULT_OK);
        finish();
    }
}
