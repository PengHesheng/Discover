package com.example.a14512.discover.modules.main.userself.personality.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.personality.presenter.PersonalityPresenterImp;

/**
 * @author 14512 on 2018/2/19
 */

public class PersonalityAdviceActivity extends BaseActivity implements IPersonalityView, View.OnClickListener {

    private PersonalityPresenterImp mPresenter;
    private String personality1, personality2, personality3, personality4, personality5;
    private CheckBox mCheckBox31;
    private CheckBox mCheckBox32;
    private CheckBox mCheckBox35;
    private CheckBox mCheckBox36;
    private CheckBox mCheckBox33;
    private CheckBox mCheckBox34;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_advice);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvNext = findViewById(R.id.btn_next);
        tvNext.setOnClickListener(this);
        mCheckBox31 = findViewById(R.id.cb_personality31);
        mCheckBox31.setOnClickListener(this);
        mCheckBox32 = findViewById(R.id.cb_personality32);
        mCheckBox32.setOnClickListener(this);
        mCheckBox35 = findViewById(R.id.cb_personality35);
        mCheckBox35.setOnClickListener(this);
        mCheckBox36 = findViewById(R.id.cb_personality36);
        mCheckBox36.setOnClickListener(this);
        mCheckBox33 = findViewById(R.id.cb_personality33);
        mCheckBox33.setOnClickListener(this);
        mCheckBox34 = findViewById(R.id.cb_personality34);
        mCheckBox34.setOnClickListener(this);
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mPresenter = new PersonalityPresenterImp(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                mPresenter.setPersonality(1);
                break;
            case R.id.btn_next:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void setPersonality1(String personality1) {

    }

    @Override
    public void setPersonality2(String personality2) {

    }

    @Override
    public void setPersonality3(String personality3) {

    }

    @Override
    public void setPersonality4(String personality4) {

    }

    @Override
    public void setPersonality5(String personality5) {

    }

    @Override
    public String getPersonality1() {
        return personality1;
    }

    @Override
    public String getPersonality2() {
        return personality2;
    }

    @Override
    public String getPersonality3() {
        if (mCheckBox31.isChecked()) {
            personality3 = "1";
        }
        if (mCheckBox32.isChecked()) {
            personality3 += "2";
        }
        if (mCheckBox33.isChecked()) {
            personality3 += "3";
        }
        if (mCheckBox34.isChecked()) {
            personality3 += "4";
        }
        if (mCheckBox35.isChecked()) {
            personality3 += "5";
        }
        if (mCheckBox36.isChecked()) {
            personality3 += "6";
        }
        return personality3;
    }

    @Override
    public String getPersonality4() {
        return personality4;
    }

    @Override
    public String getPersonality5() {
        return personality5;
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
