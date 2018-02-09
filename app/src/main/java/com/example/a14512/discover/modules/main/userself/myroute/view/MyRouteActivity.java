package com.example.a14512.discover.modules.main.userself.myroute.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
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
    private TabLayout tabLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView();
        initToolbar();
        tabClick();
    }

    private void tabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
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
        mRecyclerView = findViewById(R.id.my_route_recycler_view);
    }
}
