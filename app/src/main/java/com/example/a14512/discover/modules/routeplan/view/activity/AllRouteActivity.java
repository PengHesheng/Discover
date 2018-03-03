package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.a14512.discover.share.Share;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.BitmapUtils;
import com.example.a14512.discover.utils.DateFormatUtil;
import com.example.a14512.discover.utils.DistanceUtil;
import com.example.a14512.discover.utils.ScreenShotUtils;
import com.example.a14512.discover.utils.Time;
import com.example.a14512.discover.utils.UploadPicture;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/4
 */

public class AllRouteActivity extends BaseActivity {

    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private TextView mDate;
    private TextView mTime;
    private ImageView mImgNervous;
    private TextView mSumDistance;
    private TextView mSumTime;
    private TextView mSumPay;
    private RecyclerView mRecyclerView;
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
        mRight.setImageResource(R.mipmap.share_detail);
        mBack.setImageResource(R.mipmap.left_back);
        mBack.setOnClickListener(v -> finish());
        mRight.setOnClickListener(v -> {
                //TODO 分享
            UploadPicture.uploadPictureNoCrop(BitmapUtils.getBytes(ScreenShotUtils.takeScreenShot(this))
                    , (key, info, response) -> {
                if (info.isOK()) {
                    Share.showShare("123456", UploadPicture.imageUrl, UploadPicture.imageUrl, "");
                }
            });
        });
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

        int time = 240;
        String date;
        String timeHMS;
        if (scenics.size() <= 2) {
            date = Time.getNowYMD();
            timeHMS = Time.getNowHMSTime();
        } else {
            time = DateFormatUtil.calculateMinute(startTime, endTime) - calculatePatTime(stepTimes);
            date = startTime.substring(0, startTime.length() - 5) + "——"
                    + endTime.substring(0, endTime.length() - 5);
            timeHMS = startTime.substring(startTime.length() - 5, startTime.length()) + "——" +
                    endTime.substring(endTime.length() - 5, endTime.length());
        }
        mDate.setText(date);
        calculateNervous(time, mImgNervous);
        mTime.setText(timeHMS);
        mSumDistance.setText(DistanceUtil.transformMtoKM(sumDistance));
        mSumTime.setText(DateFormatUtil.tranceSecondToTime(sumTime(stepTimes)));
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

    private int sumTime(ArrayList<Integer> second) {
        int sum = 0;
        for (Integer dis : second) {
            sum += dis;
        }
        return sum;
    }

    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
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
