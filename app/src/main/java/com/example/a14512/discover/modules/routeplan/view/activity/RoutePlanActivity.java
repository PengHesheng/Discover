package com.example.a14512.discover.modules.routeplan.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.CustomLinearLayoutManager;
import com.example.a14512.discover.modules.routeplan.adpter.RoutePlanAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.presenter.RoutePlanPresenterImp;
import com.example.a14512.discover.modules.routeplan.view.imp.IRoutePlanView;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/28
 */

public class RoutePlanActivity extends BaseActivity implements IRoutePlanView, View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private RecyclerView mMorningRecyclerView;
    private RecyclerView mAfternoonRecyclerView;
    private RecyclerView mEveningRecyclerView;
    private RoutePlanAdapter mMorningAdapter, mAfternoonAdapter, mEveningAdapter;

    private RoutePlanPresenterImp mPresenter;

    private int personSelect, morningSize, afternoonSize, eveningSize;

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
        personSelect = getIntent().getIntExtra("personSelect", 4);
        mPresenter = new RoutePlanPresenterImp(this, this);
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
        Glide.with(this)
                .load(R.drawable.route_plan_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mMorningRecyclerView.setBackground(resource);
                    }
                });
        Glide.with(this)
                .load(R.drawable.route_plan_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mAfternoonRecyclerView.setBackground(resource);
                    }
                });
        Glide.with(this)
                .load(R.drawable.route_plan_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mEveningRecyclerView.setBackground(resource);
                    }
                });
        mEveningAdapter = new RoutePlanAdapter(this);
        mAfternoonAdapter = new RoutePlanAdapter(this);
        mMorningAdapter = new RoutePlanAdapter(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.loginSelect));
        mTitle.setText(R.string.tv_route);
        mRight.setImageResource(R.mipmap.share_detail);
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
                morningSize = scenics.size();
                break;
            case C.AFTERNOON:
                setAfternoonAdapter(scenics);
                afternoonSize = scenics.size();
                break;
            case C.EVENING:
                setEveningAdapter(scenics);
                eveningSize = scenics.size();
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
        mMorningAdapter.setRoutePlanAdapter(arrayList);
        mMorningAdapter.notifyDataSetChanged();
        mMorningRecyclerView.setAdapter(mMorningAdapter);
        mMorningAdapter.setOnItemClickListener((view, position) -> {
            if (position == 0) {
                mPresenter.deleteOneData(C.MORNING, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            } else if (afternoonSize == 0 && eveningSize == 0 && position == arrayList.size() - 1){
                mPresenter.deleteOneData(C.MORNING, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            } else {
                mPresenter.deleteOneData(C.MORNING, arrayList.get(position).name, position,
                        arrayList.get(position - 1).name, arrayList.get(position + 1).name, personSelect);
            }
        });
    }

    private void setAfternoonAdapter(ArrayList<Scenic> arrayList) {
        mAfternoonAdapter.setRoutePlanAdapter(arrayList);
        mAfternoonAdapter.notifyDataSetChanged();
        mAfternoonRecyclerView.setAdapter(mAfternoonAdapter);
        mAfternoonAdapter.setOnItemClickListener((view, position) -> {
            if (morningSize == 0 && position == 0) {
                mPresenter.deleteOneData(C.AFTERNOON, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            } else if (eveningSize == 0 && position == arrayList.size() - 1) {
                mPresenter.deleteOneData(C.AFTERNOON, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            } else {
                mPresenter.deleteOneData(C.AFTERNOON, arrayList.get(position).name, position,
                        arrayList.get(position - 1).name, arrayList.get(position + 1).name, personSelect);
            }
        });
    }

    private void setEveningAdapter(ArrayList<Scenic> arrayList) {
        mEveningAdapter.setRoutePlanAdapter(arrayList);
        mEveningAdapter.notifyDataSetChanged();
        mEveningRecyclerView.setAdapter(mEveningAdapter);
        mEveningAdapter.setOnItemClickListener((view, position) -> {
            if (afternoonSize == 0 && position == 0) {
                mPresenter.deleteOneData(C.EVENING, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            }
            if (position == arrayList.size() - 1) {
                mPresenter.deleteOneData(C.EVENING, arrayList.get(position).name, position,
                        arrayList.get(position).name, arrayList.get(position).name, personSelect);
            } else {
                mPresenter.deleteOneData(C.EVENING, arrayList.get(position).name, position,
                        arrayList.get(position - 1).name, arrayList.get(position + 1).name, personSelect);
            }
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
                ArrayList<Scenic> scenicsLast = (ArrayList<Scenic>) ACache.getDefault().getAsObject(C.SCENIC_DETAIL);
                if (scenicsLast != null) {
                    ArrayList<Scenic> scenics = new ArrayList<>();
                    scenics.add(scenicsLast.get(0));
                    if (mMorningAdapter.getItemCount() != 0) {
                        scenics.addAll(mMorningAdapter.getScenics());
                    }
                    if (mAfternoonAdapter.getItemCount() != 0) {
                        scenics.addAll(mAfternoonAdapter.getScenics());
                    }
                    if (mEveningAdapter.getItemCount() != 0) {
                        scenics.addAll(mEveningAdapter.getScenics());
                    }
                    scenics.add(scenicsLast.get(scenicsLast.size() - 1));
                    PLog.e(""+scenics.get(scenics.size() - 1).name);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(C.SCENIC_DETAIL, scenics);
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtra(C.SCENIC_DETAIL, bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
