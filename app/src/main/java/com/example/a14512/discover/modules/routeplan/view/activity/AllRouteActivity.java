package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.AllRouteAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.DistanceUtil;
import com.example.a14512.discover.utils.Time;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/4
 */

public class AllRouteActivity extends BaseActivity {

    private ImageView mBack;
    private TextView mTitle;
    private ImageView mShare;
    private Toolbar toolbar;
    private TextView mDate;
    private TextView mTime;
    private ImageView mImgNervous;
    private TextView mSumDistance;
    private TextView mSumTime;
    private TextView mSumPay;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_route);
        initView();
        initToolbar();
        getData();
    }

    private void initToolbar() {
        mTitle.setText("总览表");
        mTitle.setTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mShare.setImageResource(R.mipmap.exchange_direction);
        mBack.setImageResource(R.mipmap.all_route_back);
        mBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        String date = ACache.getDefault().getAsString("date");
        String startTime = ACache.getDefault().getAsString("start_time");
        String endTime = ACache.getDefault().getAsString("end_time");
        int staToEnd = Time.calculateMinute(startTime, endTime);
        int sumPay = 0;

        ArrayList<Scenic> scenics = (ArrayList<Scenic>) getIntent().getBundleExtra(C.SCENIC_DETAIL).getSerializable(C.SCENIC_DETAIL);
        ArrayList<String> names = new ArrayList<>();
        assert scenics != null;
        for (Scenic scenic : scenics) {
            names.add(scenic.name);
            sumPay += scenic.peopleAver;
        }
        ArrayList<Integer> stepTimes = getIntent().getIntegerArrayListExtra("time");
        int location = getIntent().getIntExtra("location", 0);
        int sumDistance = getIntent().getIntExtra("sum_distance", 0);

        mDate.setText(date);
        mTime.setText(startTime + "——" + endTime);
        mSumDistance.setText(DistanceUtil.transformMtoKM(sumDistance));
        mSumTime.setText(sumTime(stepTimes));
        mSumPay.setText(sumPay + "元");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        AllRouteAdapter adapter = new AllRouteAdapter(this);
        adapter.setAdapterData(names, location, stepTimes);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private String sumTime(ArrayList<Integer> second) {
        int sum = 0;
        for (Integer dis : second) {
            sum += dis;
        }
        return Time.tranceSecondToTime(sum);
    }

    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mShare = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mDate = findViewById(R.id.tv_all_route_day);
        mTime = findViewById(R.id.tv_all_route_time);
        mImgNervous = findViewById(R.id.img_all_route_nervous);
        mSumDistance = findViewById(R.id.tv_all_route_sum_path);
        mSumTime = findViewById(R.id.tv_all_route_sum_time);
        mSumPay = findViewById(R.id.tv_all_route_sum_pay);
        mRecyclerView = findViewById(R.id.all_route_recycler_view);
    }
}
