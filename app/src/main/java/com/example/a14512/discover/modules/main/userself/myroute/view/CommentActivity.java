package com.example.a14512.discover.modules.main.userself.myroute.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.CommentPresenterImp;
import com.example.a14512.discover.modules.main.userself.myroute.view.imp.ICommentView;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/26
 */

public class CommentActivity extends BaseActivity implements ICommentView {

    private ImageView mImageView;
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private TextView mName;
    private TextView mMonthAver;
    private TextView mPeopleAver;
    private LinearLayout mLayoutAttention;
    private TextView mLocation;
    private TextView mTime;
    private TextView mAbout;
    private RatingBar mRatingBar1;
    private RatingBar mRatingBar2;
    private RatingBar mRatingBar3;
    private RatingBar mRatingBar4;
    private RatingBar mRatingBar5;

    private boolean isFollow = false;
    private CommentPresenterImp mPresenter;
    private Scenic mScenic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
        getData();
        initToolbar();
    }

    private void getData() {
        mScenic = (Scenic) getIntent().getBundleExtra(C.SCENIC_DETAIL).get(C.SCENIC_DETAIL);
        if (mScenic != null) {
            Glide.with(this).load(mScenic.img).error(R.mipmap.ic_launcher_round).into(mImageView);
            mName.setText(mScenic.name);
            mTime.setText("" + mScenic.time);
            mAbout.setText(mScenic.content);
            mLocation.setText(mScenic.location);
            mPeopleAver.setText("人均" + mScenic.peopleAver);
            mMonthAver.setText("人气" + mScenic.monthAver);
            mLayoutAttention.setOnClickListener(v -> {
                if (!isFollow) {
                    mPresenter.followScenic(mScenic.name, 0);
                    ToastUtil.show(this, "以关注");
                } else {
                    mPresenter.followScenic(mScenic.name, 1);
                    ToastUtil.show(this, "取消关注");
                }
            });
        }
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mLeft.setOnClickListener(v -> finish());
        mRight.setOnClickListener(v -> mPresenter.setScore(mScenic.name));
    }

    private void initView() {
        mImageView = findViewById(R.id.img_scenic_details);
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mName = findViewById(R.id.tv_details_scenic_name);
        mMonthAver = findViewById(R.id.tv_scenic_details_people);
        mPeopleAver = findViewById(R.id.tv_scenic_details_money);
        mLayoutAttention = findViewById(R.id.layout_details_attention);
        mLocation = findViewById(R.id.tv_scenic_details_location);
        mTime = findViewById(R.id.tv_scenic_details_time);
        mAbout = findViewById(R.id.tv_scenic_details_about);
        TextView comment = findViewById(R.id.tv_item_comment);
        mRatingBar1 = findViewById(R.id.rating_bar_1);
        mRatingBar2 = findViewById(R.id.rating_bar_2);
        mRatingBar3 = findViewById(R.id.rating_bar_3);
        mRatingBar4 = findViewById(R.id.rating_bar_4);
        mRatingBar5 = findViewById(R.id.rating_bar_5);

        comment.setText("综合评分");

        mPresenter = new CommentPresenterImp(this, this);
    }

    @Override
    public void isFollow(int result) {
        ImageView img = mLayoutAttention.findViewById(R.id.img_attention);
        TextView tv = mLayoutAttention.findViewById(R.id.tv_attention);
        if (result == 1) {
            img.setVisibility(View.GONE);
            tv.setText("已关注");
            isFollow = true;
        } else {
            img.setVisibility(View.VISIBLE);
            tv.setText("关注");
            isFollow = false;
        }
    }

    @Override
    public int getScenicParticularStar() {
        return mRatingBar1.getNumStars();
    }

    @Override
    public int getTrafficStar() {
        return mRatingBar2.getNumStars();
    }

    @Override
    public int getAllServiceStar() {
        return mRatingBar3.getNumStars();
    }

    @Override
    public int getHistoricStar() {
        return mRatingBar4.getNumStars();
    }

    @Override
    public int getPlayStar() {
        return mRatingBar5.getNumStars();
    }
}
