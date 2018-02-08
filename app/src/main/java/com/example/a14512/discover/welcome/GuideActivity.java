package com.example.a14512.discover.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.login.view.LoginActivity;
import com.example.a14512.discover.modules.main.view.MainActivity;
import com.example.a14512.discover.utils.ToastUtil;

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
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == C.LOGIN) {
                ToastUtil.show("login success");
                startIntentActivity(this, MainActivity.class);
                finish();
            }
        }
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.guide_view_pager);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> viewList = new ArrayList<>();
        viewList.add(inflater.inflate(R.layout.view_pager_guide_first, null));
        viewList.add(inflater.inflate(R.layout.view_pager_guide_second, null));
        viewList.add(inflater.inflate(R.layout.view_pager_guide_third, null));
        viewList.add(inflater.inflate(R.layout.view_pager_guide_fourth, null));
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(this, viewList);
        viewPager.setAdapter(adapter);
        TextView tvGo = viewList.get(3).findViewById(R.id.tv_view_pager_go);
        tvGo.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, LoginActivity.class), C.LOGIN);
        });
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
