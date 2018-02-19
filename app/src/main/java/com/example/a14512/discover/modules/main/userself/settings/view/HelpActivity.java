package com.example.a14512.discover.modules.main.userself.settings.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;

/**
 * @author 14512 on 2018/2/9
 */

public class HelpActivity extends BaseActivity {
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mTitle.setText("帮助");
        mLeft.setOnClickListener(v -> finish());
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
    }
}
