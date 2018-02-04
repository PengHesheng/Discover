package com.example.a14512.discover.modules.community.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/31
 */

public class CommunityDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mAdd;
    private Toolbar toolbar;
    private ImageView mPortrait;
    private TextView mUserName;
    private TextView mCommentTime;
    private TextView mContent;
    private TextView mTvZan;
    private TextView mTvComment;
    private TextView mTvTransmit;
    private RecyclerView mRecyclerView;
    private LinearLayout mZan;
    private LinearLayout mComment;
    private LinearLayout mTransmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detial);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mBack.setOnClickListener(v -> finish());
        mTitle.setText("详情");
        mAdd.setImageResource(R.mipmap.add);
    }

    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mAdd = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mPortrait = findViewById(R.id.img_community_detail_portrait);
        mUserName = findViewById(R.id.tv_community_detail_user_name);
        mCommentTime = findViewById(R.id.tv_community_detail_comment_time);
        mContent = findViewById(R.id.tv_community_detail_comment_content);
        mTvZan = findViewById(R.id.tv_community_detail_zan);
        mTvComment = findViewById(R.id.tv_community_detail_comment);
        mTvTransmit = findViewById(R.id.tv_community_detail_transmit);
        mRecyclerView = findViewById(R.id.community_detail_recycler_view);
        mZan = findViewById(R.id.layout_community_detail_zan);
        mComment = findViewById(R.id.layout_community_detail_comment);
        mTransmit = findViewById(R.id.layout_Community_details_transmit);

        mZan.setOnClickListener(this);
        mComment.setOnClickListener(this);
        mTransmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_community_detail_zan:
                break;
            case R.id.layout_community_detail_comment:
                break;
            case R.id.layout_Community_details_transmit:
                break;
            default:
                break;
        }
    }
}
