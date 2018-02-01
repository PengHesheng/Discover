package com.example.a14512.discover.modules.community.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/27
 */

public class CommunityActivity extends BaseSwipeBackActivity {
    private ImageView mImg;
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mAdd;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mBack.setOnClickListener(v -> finish());
        mTitle.setText("社区");
        Glide.with(this).load(R.mipmap.add).into(mAdd);
    }

    private void initView() {
        mImg = findViewById(R.id.img_community);
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mAdd = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.community_recycler_view);
    }
}
