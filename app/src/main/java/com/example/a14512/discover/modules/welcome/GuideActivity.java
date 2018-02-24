package com.example.a14512.discover.modules.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14512 on 2018/2/2
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setDecorView();
        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.guide_view_pager);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> viewList = new ArrayList<>();
        viewList.add(inflater.inflate(R.layout.view_pager_guide_first, null));
        viewList.add(inflater.inflate(R.layout.view_pager_guide_second, null));
        viewList.add(inflater.inflate(R.layout.view_pager_guide_third, null));
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(this, viewList);
        viewPager.setAdapter(adapter);
        ImageView img1 = viewList.get(0).findViewById(R.id.img_view_pager1);
        Glide.with(this).load(R.drawable.view_pager1).into(img1);
        ImageView img2 = viewList.get(1).findViewById(R.id.img_view_pager2);
        Glide.with(this).load(R.drawable.view_pager2).into(img2);
        ImageView img3 = viewList.get(2).findViewById(R.id.img_view_pager3);
        Glide.with(this).load(R.drawable.view_pager3).into(img3);
        TextView tvGo = viewList.get(2).findViewById(R.id.tv_view_pager_go);
        tvGo.setOnClickListener(v -> {
            startIntentActivity(this, LoginActivity.class);
            finish();
        });
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 3:
                startIntentActivity(this, LoginActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
