package com.example.a14512.discover.modules.main.userself.settings.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.utils.KeyBoardUtil;

/**
 * @author 14512 on 2018/2/9
 */

public class AdviceActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mainLayout;
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private EditText mEdtAdvice;
    private EditText mEdtContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText("意见反馈");
    }

    private void initView() {
        mainLayout = findViewById(R.id.layout_advice);
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mEdtAdvice = findViewById(R.id.edt_advice);
        mEdtContact = findViewById(R.id.edt_advice_contact);
        Button btnSubmit = findViewById(R.id.btn_advice_submit);

        btnSubmit.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_advice_submit:
                sendAdvice();
                finish();
                break;
            case R.id.layout_advice:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                break;
            default:
                break;
        }
    }

    private void sendAdvice() {
        mEdtAdvice.getText().clear();
        mEdtContact.getText().clear();
    }
}
