package com.example.a14512.discover.modules.main.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;
import com.example.a14512.discover.modules.main.adpter.CustomLinearLayoutManager;
import com.example.a14512.discover.modules.main.adpter.RoutePlanAdapter;
import com.example.a14512.discover.modules.main.mode.entity.Scenic;
import com.example.a14512.discover.modules.main.presenter.RoutePlanPresenterImp;
import com.example.a14512.discover.modules.main.view.imp.IRoutePlanView;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/28
 */

public class RoutePlanActivity extends BaseSwipeBackActivity implements IRoutePlanView, View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private RecyclerView mMorningRecyclerView;
    private RecyclerView mAfternoonRecyclerView;
    private RecyclerView mEveningRecyclerView;
    private RoutePlanAdapter mMorningAdapter, mAfternoonAdapter, mEveningAdapter;

    private RoutePlanPresenterImp mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        initView();
        initToolbar();
        initRecyclerView();
        getData();
    }

    private void getData() {
        mPresenter = new RoutePlanPresenterImp(this);
        mPresenter.getData();
    }

    private void initRecyclerView() {
        CustomLinearLayoutManager layoutManager1 = new CustomLinearLayoutManager(this);
        layoutManager1.setScrollEnabled(false);
        CustomLinearLayoutManager layoutManager2 = new CustomLinearLayoutManager(this);
        layoutManager2.setScrollEnabled(false);
        CustomLinearLayoutManager layoutManager3 = new CustomLinearLayoutManager(this);
        layoutManager3.setScrollEnabled(false);
        mMorningRecyclerView.setLayoutManager(layoutManager1);
        mAfternoonRecyclerView.setLayoutManager(layoutManager2);
        mEveningRecyclerView.setLayoutManager(layoutManager3);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainToolbar));
        mBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mMorningRecyclerView = findViewById(R.id.morning_recycler_view);
        mAfternoonRecyclerView = findViewById(R.id.afternoon_recycler_view);
        mEveningRecyclerView = findViewById(R.id.evening_recycler_view);


        Button btnBuild = findViewById(R.id.btn_build);
        btnBuild.setOnClickListener(this);
    }

    @Override
    public void setAdapter(ArrayList<Scenic> scenics, String category) {
        switch (category) {
            case C.MORNING:
                setMorningAdapter(scenics);
                break;
            case C.AFTERNOON:
                setAfternoonAdapter(scenics);
                break;
            case C.EVENING:
                setEveningAdapter(scenics);
                break;
            default:
                break;
        }
    }

    @Override
    public void setOneData(int position, Scenic scenic, String category) {
        switch (category) {
            case C.MORNING:
                setOneMorningData(position, scenic);
                break;
            case C.AFTERNOON:
                setOneAfternoonData(position, scenic);
                break;
            case C.EVENING:
                setOneEveningData(position, scenic);
                break;
            default:
                break;
        }
    }

    private void setMorningAdapter(ArrayList<Scenic> arrayList) {
        mMorningAdapter = new RoutePlanAdapter(this, arrayList);
        mMorningAdapter.notifyDataSetChanged();
        mMorningRecyclerView.setAdapter(mMorningAdapter);
        mMorningAdapter.setOnItemClickListener((view, position) -> {
            mPresenter.deleteOneData(C.MORNING, position);
        });
    }

    private void setAfternoonAdapter(ArrayList<Scenic> arrayList) {
        mAfternoonAdapter = new RoutePlanAdapter(this, arrayList);
        mAfternoonAdapter.notifyDataSetChanged();
        mAfternoonRecyclerView.setAdapter(mAfternoonAdapter);
        mAfternoonAdapter.setOnItemClickListener((view, position) -> {
            mPresenter.deleteOneData(C.AFTERNOON, position);
        });
    }

    private void setEveningAdapter(ArrayList<Scenic> arrayList) {
        mEveningAdapter = new RoutePlanAdapter(this, arrayList);
        mEveningAdapter.notifyDataSetChanged();
        mEveningRecyclerView.setAdapter(mEveningAdapter);
        mEveningAdapter.setOnItemClickListener((view, position) -> {
            mPresenter.deleteOneData(C.EVENING, position);
        });
    }

    private void setOneMorningData(int position, Scenic scenic) {
        mMorningAdapter.changeOneData(position, scenic);
    }

    private void setOneAfternoonData(int position, Scenic scenic) {
        mAfternoonAdapter.changeOneData(position, scenic);
    }

    private void setOneEveningData(int position, Scenic scenic) {
        mEveningAdapter.changeOneData(position, scenic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_build:
                ArrayList<Scenic> scenics = new ArrayList<>();
                scenics.addAll(mMorningAdapter.getScenics());
                scenics.addAll(mAfternoonAdapter.getScenics());
                scenics.addAll(mEveningAdapter.getScenics());
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.SCENIC_DETAIL, scenics);
                Intent intent = new Intent(this, MapActivity.class);
                intent.putExtra(C.SCENIC_DETAIL, bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
