package com.example.a14512.discover.modules.main.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.arround.view.AroundActivity;
import com.example.a14512.discover.modules.community.view.CommunityActivity;
import com.example.a14512.discover.modules.route.view.RouteActivity;
import com.example.a14512.discover.modules.travel.view.TravelKnowledgeActivity;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author 14512 on 2018/1/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private NavigationView navView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNavigation();
    }

    private void initNavigation() {
        setStatusBarColor(R.color.mainToolbar);
        navView.setCheckedItem(R.id.nav_item_my);
        ImageView headPortrait = navView.getHeaderView(0).findViewById(R.id.img_head_portrait);
        TextView name = navView.getHeaderView(0).findViewById(R.id.tv_head_name);
        name.setText("PHS");
        headPortrait.setOnClickListener(this);
        Glide.with(this)
                .load(R.mipmap.ic_launcher_round)
                .bitmapTransform(new CropCircleTransformation(this), new FitCenter(this))
                .priority(Priority.HIGH)
                .into(headPortrait);
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_item_my:
                    break;
                default:
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    private void initView() {
        ImageButton imgBtnRoutePlan = findViewById(R.id.img_btn_route_plan);
        TextView tv = findViewById(R.id.tv_route_plan);
        LinearLayout layoutTravel = findViewById(R.id.layout_travel);
        LinearLayout layoutCommunity = findViewById(R.id.layout_community);
        LinearLayout layoutAround = findViewById(R.id.layout_around);
        LinearLayout layoutMyRoute = findViewById(R.id.layout_my_route);
        navView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        tv.setText("路线规划");
        imgBtnRoutePlan.setBackgroundResource(R.mipmap.ic_launcher_round);
        imgBtnRoutePlan.setOnClickListener(this);
        layoutTravel.setOnClickListener(this);
        layoutCommunity.setOnClickListener(this);
        layoutAround.setOnClickListener(this);
        layoutMyRoute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_route_plan:
                startIntentActivity(this, new ChooseActivity());
                break;
            case R.id.layout_travel:
                startIntentActivity(this, new TravelKnowledgeActivity());
                break;
            case R.id.layout_community:
                startIntentActivity(this, new CommunityActivity());
                break;
            case R.id.layout_around:
                startIntentActivity(this, new AroundActivity());
                break;
            case R.id.layout_my_route:
                startIntentActivity(this, new RouteActivity());
                break;
            default:
                break;
        }
    }
}
