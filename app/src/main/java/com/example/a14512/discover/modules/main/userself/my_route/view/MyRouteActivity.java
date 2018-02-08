package com.example.a14512.discover.modules.main.userself.my_route.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/27
 */

public class MyRouteActivity extends BaseSwipeBackActivity {
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText(R.string.menu_my_route);
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
    }
}
