package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.ViewPagerAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;
import com.example.a14512.discover.modules.routeplan.presenter.ScenicPresenterImp;
import com.example.a14512.discover.modules.routeplan.view.fragment.CommentFragment;
import com.example.a14512.discover.modules.routeplan.view.fragment.ConsumeFragment;
import com.example.a14512.discover.modules.routeplan.view.fragment.IntroduceFragment;
import com.example.a14512.discover.modules.routeplan.view.fragment.StrategyFragment;
import com.example.a14512.discover.modules.routeplan.view.fragment.TicketFragment;
import com.example.a14512.discover.modules.routeplan.view.imp.IScenicView;
import com.example.a14512.discover.share.Share;
import com.example.a14512.discover.utils.BitmapUtils;
import com.example.a14512.discover.utils.ScreenShotUtils;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.UploadPicture;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/31
 */

public class ScenicActivity extends BaseActivity implements IScenicView, View.OnClickListener {

    private ImageView imgScenic;
    private ImageView mBack;
    private ImageView mShare;
    private Toolbar toolbar;
    private TextView scenicName;
    private TextView scenicMonthAver;
    private TextView scenicPeopleAver;
    private ImageView mImgAttention;
    private TextView mTvAttention;
    private TextView scenicLocation;
    private TextView scenicOpenTime;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mActionBtn;

    private Scenic mScenic;
    private ScenicPresenterImp mPresenter;
    private CommentFragment commentFragment;
    private IntroduceFragment introduceFragment;
    private TicketFragment ticketFragment;
    private ConsumeFragment consumeFragment;
    private StrategyFragment strategyFragment;
    public static final ArrayList<String> TAB_TITLE = new ArrayList<>();

    private boolean isFollow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic);
        initView();
        initToolbar();
        initTabLayout();
        getData();
    }

    private void initTabLayout() {
        TAB_TITLE.add("简介");
        TAB_TITLE.add("购票");
        TAB_TITLE.add("旅购");
        TAB_TITLE.add("攻略");
        TAB_TITLE.add("评价");
        introduceFragment = new IntroduceFragment();
        ticketFragment = new TicketFragment();
        consumeFragment = new ConsumeFragment();
        strategyFragment = new StrategyFragment();
        commentFragment = new CommentFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(introduceFragment);
        fragments.add(ticketFragment);
        fragments.add(consumeFragment);
        fragments.add(strategyFragment);
        fragments.add(commentFragment);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        int type = getIntent().getIntExtra("type", 0);
        if (type == C.SCENIC_TYPE) {
            ArrayList<Scenic> scenics = (ArrayList<Scenic>) getIntent().getBundleExtra(C.SCENIC_DETAIL)
                    .getSerializable(C.SCENIC_DETAIL);
            assert scenics != null;
            mScenic = scenics.get(1);
            mActionBtn.setVisibility(View.VISIBLE);
            mActionBtn.setOnClickListener(v -> {
                Intent intent = new Intent(this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(C.SCENIC_DETAIL, scenics);
                intent.putExtra(C.SCENIC_DETAIL, bundle);
                startActivity(intent);
            });
        } else {
            mScenic = (Scenic) getIntent().getBundleExtra(C.SCENIC_DETAIL).get(C.SCENIC_DETAIL);
        }
        mPresenter = new ScenicPresenterImp(this, this);
        if (type == 1) {
            isFollow = true;
            isFollow(1);
        }
        if (mScenic != null) {
            mPresenter.getData(mScenic.name);
            if (mScenic.img != null) {
                Glide.with(this).load(mScenic.img.replaceAll("\\\\", ""))
                        .error(R.mipmap.ic_launch).into(imgScenic);
            }
            scenicName.setText(mScenic.name);
            scenicMonthAver.setText("人气" + mScenic.monthAver);
            scenicPeopleAver.setText("人均" + mScenic.peopleAver);
            scenicLocation.setText("地址：" + mScenic.location);
            scenicOpenTime.setText("营业时间：" + mScenic.openTime);

            Bundle bundle = new Bundle();
            bundle.putSerializable("scenic", mScenic);
            introduceFragment.setArguments(bundle);
            ticketFragment.setArguments(bundle);
            consumeFragment.setArguments(bundle);
            strategyFragment.setArguments(bundle);
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setDecorView();
        mBack.setOnClickListener(v -> finish());
        mShare.setImageResource(R.mipmap.share_detail);
        mShare.setOnClickListener(v -> {
            //TODO 分享
            UploadPicture.uploadPictureNoCrop(BitmapUtils.getBytes(ScreenShotUtils.takeScreenShot(this)),
                    (key, info, response) -> {
                if (info.isOK()) {
                    Share.showShare(mScenic.name, UploadPicture.imageUrl, UploadPicture.imageUrl, "");
                }
            });
        });
    }

    private void initView() {
        imgScenic = findViewById(R.id.img_scenic_details);
        mBack = findViewById(R.id.img_toolbar_left);
        mShare = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        scenicName = findViewById(R.id.tv_details_scenic_name);
        scenicMonthAver = findViewById(R.id.tv_scenic_details_people);
        scenicPeopleAver = findViewById(R.id.tv_scenic_details_money);
        mImgAttention = findViewById(R.id.img_attention);
        mTvAttention = findViewById(R.id.tv_attention);
        LinearLayout attention = findViewById(R.id.layout_details_attention);
        scenicLocation = findViewById(R.id.tv_scenic_details_location);
        scenicOpenTime = findViewById(R.id.tv_scenic_details_time);
        mTabLayout = findViewById(R.id.tab_layout_scenic);
        mViewPager = findViewById(R.id.view_pager_scenic);
        mActionBtn = findViewById(R.id.floating_action_btn);

        mActionBtn.setVisibility(View.GONE);
        attention.setOnClickListener(this);
    }

    @Override
    public void setCommentAdapter(ArrayList<ScenicCommentUser> users) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("comment_users", users);
        commentFragment.setArguments(bundle);
    }

    @Override
    public void setConsumeAdapter(ArrayList<ConsumeMode> consumeModes) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("consume", consumeModes);
        consumeFragment.setArguments(bundle);
    }

    @Override
    public void setStrategyAdapter(ArrayList<StrategyMode> strategyModes) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("strategy", strategyModes);
        strategyFragment.setArguments(bundle);
    }


    @Override
    public void isFollow(int result) {
        if (result == 1) {
            mImgAttention.setVisibility(View.GONE);
            mTvAttention.setText("已关注");
            isFollow = true;
        } else {
            mImgAttention.setVisibility(View.VISIBLE);
            mTvAttention.setText("关注");
            isFollow = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_details_attention:
                if (!isFollow) {
                    mPresenter.followScenic(mScenic.name, 1);
                    ToastUtil.show(ScenicActivity.this, "以关注");
                } else {
                    mPresenter.followScenic(mScenic.name, 0);
                    ToastUtil.show(ScenicActivity.this, "取消关注");
                }
                break;
            default:
                break;
        }
    }
}
