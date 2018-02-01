package com.example.a14512.discover.modules.main.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;
import com.example.a14512.discover.modules.main.adpter.CustomLinearLayoutManager;
import com.example.a14512.discover.modules.main.adpter.ScenicCommentAdapter;
import com.example.a14512.discover.modules.main.modle.entity.Scenic;
import com.example.a14512.discover.modules.main.modle.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.main.presenter.ScenicPresenterImp;
import com.example.a14512.discover.modules.main.view.imp.IScenicView;
import com.example.a14512.discover.share.Share;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/31
 */

public class ScenicActivity extends BaseSwipeBackActivity implements View.OnClickListener, IScenicView{

    private ImageView imgScenic;
    private ImageView mBack;
    private ImageView mShare;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private  ScenicCommentAdapter adapter;
    private Scenic mScenic;
    private ScenicPresenterImp mPresenter;

    private boolean isZan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic);
        initView();
        getData();
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {
        adapter = new ScenicCommentAdapter(this);
        mPresenter = new ScenicPresenterImp(this);
        mPresenter.getData();
        mScenic = (Scenic) getIntent().getBundleExtra(C.SCENIC_DETAIL).get(C.SCENIC_DETAIL);
        if (mScenic != null) {
            adapter.setScenicsAdapter(mScenic);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Glide.with(this).load(R.mipmap.ic_launcher_round).into(imgScenic);
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setDecorView();
        mBack.setOnClickListener(v -> finish());
        mShare.setBackgroundResource(R.mipmap.share_detail);
        mShare.setOnClickListener(v -> Share.showShare("nihao", "www.baidu.com", ""));
    }

    private void initView() {
        imgScenic = findViewById(R.id.img_scenic_details);
        mBack = findViewById(R.id.img_toolbar_left);
        mShare = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.scenic_details_recycler_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_details_attention:
                break;
            default:
                break;
        }
    }

    @Override
    public void setAdapter(ArrayList<ScenicCommentUser> users) {
        adapter.setCommentAdapter(users);
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            if (!isZan) {
                view.setBackgroundResource(R.drawable.btn_bus_bg);
                isZan = true;
            } else {
                view.setBackgroundResource(R.drawable.layout_bg_white);
                isZan = false;
            }
        });
    }
}
