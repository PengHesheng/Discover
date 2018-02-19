package com.example.a14512.discover.modules.main.userself.attention.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.attention.presenter.MyAttentionPresenterImp;
import com.example.a14512.discover.modules.routeplan.adpter.RoutePlanAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/6
 */

public class MyAttentionActivity extends BaseActivity implements IMyAttentionView {
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
        initToolbar();
        getData();
    }

    private void getData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        MyAttentionPresenterImp presenter = new MyAttentionPresenterImp(this, this);
        if (!presenter.getMyFollowFromACache()) {
            presenter.getMyFollow();
        }
        mRefreshLayout.setOnRefreshListener(presenter::getMyFollow);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mLeft.setImageResource(R.mipmap.left_back);
        mRight.setImageResource(R.mipmap.edit_pen);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText(R.string.menu_my_attention);
        mTitle.setTextColor(getResources().getColor(R.color.titleBlack));
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mRefreshLayout = findViewById(R.id.my_attention_swipe_refresh);
        mRecyclerView = findViewById(R.id.my_attention_recycler_view);
    }

    @Override
    public void setAdapter(ArrayList<Scenic> myFollows) {
        RoutePlanAdapter adapter = new RoutePlanAdapter(this);
        adapter.setMyFollowAdapter(myFollows, 1);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener((view, position) -> {
            //TODO 处理删除
        });
        mRefreshLayout.setRefreshing(false);
    }
}
