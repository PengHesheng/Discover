package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.AllRouteAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.DateFormatUtil;
import com.example.a14512.discover.utils.DistanceUtil;
import com.example.a14512.discover.utils.JsonUtil;
import com.example.a14512.discover.utils.PLog;

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
    private FloatingActionButton mActionButton;
    private int type, sumDistance = 0, sumPay = 0;
    private ArrayList<Scenic> scenics;

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
        mBack.setImageResource(R.mipmap.left_back);
        mBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        String startTime = ACache.getDefault().getAsString("start_time");
        String endTime = ACache.getDefault().getAsString("end_time");
        scenics = (ArrayList<Scenic>) getIntent().getBundleExtra(C.SCENIC_DETAIL).getSerializable(C.SCENIC_DETAIL);
        ArrayList<String> names = new ArrayList<>();
        assert scenics != null;
        for (Scenic scenic : scenics) {
            names.add(scenic.name);
            sumPay += scenic.peopleAver;
        }
        ArrayList<Integer> stepTimes = getIntent().getIntegerArrayListExtra("time");
        int location = getIntent().getIntExtra("location", 0);
        sumDistance = getIntent().getIntExtra("sum_distance", 0);
        type = getIntent().getIntExtra("type", 1);

        String date = startTime.substring(0, startTime.length() - 6) + "——"
                + endTime.substring(0, endTime.length() - 6);
        mDate.setText(date);
        int time = DateFormatUtil.calculateMinute(startTime, endTime) - calculatePatTime(stepTimes);
        calculateNervous(time, mImgNervous);
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

    private void calculateNervous(int time, ImageView nervous) {
        if (time >= 4*60) {
            setStar(0, nervous);
        } else if (time >= 3*60 && time < 4*60) {
            setStar(1, nervous);
        } else if (time >= 2*60 && time < 3*60) {
            setStar(2, nervous);
        } else if (time >= 60 && time < 2*60) {
            setStar(3, nervous);
        } else if (time >= 30 && time < 60) {
            setStar(4, nervous);
        } else {
            setStar(5, nervous);
        }

    }

    private int calculatePatTime(ArrayList<Integer> second) {
        int sum = 0;
        for (int i : second) {
            sum += i;
        }
        sum = sum / 60;
        return sum;
    }

    private void setStar(int star, ImageView imageView) {
        switch (star) {
            case 0:
                Glide.with(this).load(R.drawable.nervous0).into(imageView);
                break;
            case 1:
                Glide.with(this).load(R.drawable.nervous1).into(imageView);
                break;
            case 2:
                Glide.with(this).load(R.drawable.nervous2).into(imageView);
                break;
            case 3:
                Glide.with(this).load(R.drawable.nervous3).into(imageView);
                break;
            case 4:
                Glide.with(this).load(R.drawable.nervous4).into(imageView);
                break;
            case 5:
                Glide.with(this).load(R.drawable.nervous5).into(imageView);
                break;
            default:
                break;
        }
    }

    private String sumTime(ArrayList<Integer> second) {
        int sum = 0;
        for (Integer dis : second) {
            sum += dis;
        }
        return DateFormatUtil.tranceSecondToTime(sum);
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
        mActionButton = findViewById(R.id.floating_btn_all_route);

        mActionButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("保存路线")
                    .setCancelable(false)
                    .setPositiveButton("是", (dialog, id) -> {
                        //TODO  保存逻辑
                        PLog.e(JsonUtil.toJSONString(scenics));
                    })
                    .setNegativeButton("否", (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        });
    }
}
