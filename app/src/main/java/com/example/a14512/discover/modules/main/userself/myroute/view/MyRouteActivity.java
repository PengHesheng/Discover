package com.example.a14512.discover.modules.main.userself.myroute.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.myroute.adapter.MyRouteAdapter;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.MyRoutePresenterImp;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/27
 */

public class MyRouteActivity extends BaseActivity implements IMyRouteView{
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyRoutePresenterImp mPresenter;
    private MyRouteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView();
        initToolbar();
        tabClick();
    }

    private void tabClick() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mPresenter = new MyRoutePresenterImp(this, this);
        mPresenter.getMtRoute();
        mAdapter = new MyRouteAdapter(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mPresenter.getMyCollectFromACache();
                        break;
                    case 1:
                        mPresenter.getHistoricRouteFromACache();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mRefreshLayout.setOnRefreshListener(() -> {
//            mPresenter.getMtRoute();
            mRefreshLayout.setRefreshing(false);
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mTitle.setTextColor(getResources().getColor(R.color.titleBlack));
        mLeft.setImageResource(R.mipmap.left_back);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText(R.string.menu_my_route);
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout_my_route);
        mRefreshLayout = findViewById(R.id.my_route_swipe_refresh);
        mRecyclerView = findViewById(R.id.my_route_recycler_view);
    }

    @Override
    public void setHistoricRoute(ArrayList<MyRoute> routes) {
        mAdapter.setAdapter(routes, 1);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setMyCollect(ArrayList<MyRoute> routes) {
        mAdapter.setAdapter(routes, 0);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
