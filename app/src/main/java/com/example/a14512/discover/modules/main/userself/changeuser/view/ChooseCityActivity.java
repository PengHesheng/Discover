package com.example.a14512.discover.modules.main.userself.changeuser.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.changeuser.adapter.RecyclerAdapter;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/14
 */

public class ChooseCityActivity extends BaseActivity implements RecyclerAdapter.OnItemClickListener{

    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        initData();
        initView();
        initToolbar();
        initRecyclerView();
    }

    private void initData() {
        dataList = new ArrayList<>();
        dataList.add("重庆市");
        dataList.add("北京市");
        dataList.add("天津市");
        dataList.add("上海市");
        dataList.add("贵州省");
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setListData(dataList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mTitle.setText("选择区域");
        mLeft.setOnClickListener(v -> finish());
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.chooseRecyclerView);
    }

    @Override
    public void onItemClick(View view, int position) {
        startIntentActivity(this, ChangeUserInfoActivity.class, "chooseItem", dataList.get(position));
        setResult(RESULT_OK);
        finish();
    }
}
