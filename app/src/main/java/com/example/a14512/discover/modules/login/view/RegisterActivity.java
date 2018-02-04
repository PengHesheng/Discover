package com.example.a14512.discover.modules.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.presenter.RegisterPresenterImp;
import com.example.a14512.discover.modules.login.view.imp.IRegisterView;
import com.example.a14512.discover.modules.main.view.activity.MainActivity;
import com.example.a14512.discover.utils.ObservableTimer;


/**
 * @author 14512 on 2018/2/1
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterView {

    private EditText mPhoneNum;
    private EditText mCode;
    private EditText mPwd;
    private Button btnGetCode;
    private RegisterPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mPhoneNum = findViewById(R.id.edt_register_number);
        mPwd = findViewById(R.id.edt_register_pwd);
        mCode = findViewById(R.id.edt_code);
        btnGetCode = findViewById(R.id.btn_get_code);
        Button btnRegister = findViewById(R.id.btn_register);

        btnGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        mPresenter = new RegisterPresenterImp(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_code:
                mPresenter.getCode();
                break;
            case R.id.btn_register:
                mPresenter.register();
                break;
            default:
                break;
        }
    }

    @Override
    public String getPhoneNum() {
        return mPhoneNum.getText().toString();
    }

    @Override
    public int getCode() {
        return Integer.parseInt(mCode.getText().toString());
    }

    @Override
    public String getPwd() {
        return mPwd.getText().toString();
    }

    @Override
    public void isRegister() {
        startIntentActivity(this, MainActivity.class);
        finish();
    }

    @Override
    public void showCodeTime() {
        new ObservableTimer(btnGetCode).startTimer();
    }
}
