package com.example.a14512.discover.modules.routeplan.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.adpter.CustomLinearLayoutManager;
import com.example.a14512.discover.modules.routeplan.adpter.ScenicCommentAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.presenter.ScenicPresenterImp;
import com.example.a14512.discover.modules.routeplan.view.imp.IScenicView;
import com.example.a14512.discover.share.Share;
import com.example.a14512.discover.utils.BitmapUtils;
import com.example.a14512.discover.utils.ScreenShotUtils;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.UploadPicture;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/31
 */

public class ScenicActivity extends BaseActivity implements IScenicView{

    private ImageView imgScenic;
    private ImageView mBack;
    private ImageView mShare;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private  ScenicCommentAdapter adapter;
    private Scenic mScenic;
    private ScenicPresenterImp mPresenter;
    private LinearLayout attention;

    private boolean isFollow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic);
        initView();
        initToolbar();
        getData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void getData() {
        mScenic = (Scenic) getIntent().getBundleExtra(C.SCENIC_DETAIL).get(C.SCENIC_DETAIL);
        int type = getIntent().getIntExtra("type", 0);
        adapter = new ScenicCommentAdapter(this, type);
        mPresenter = new ScenicPresenterImp(this, this);
        isFollow = type == 1;
        if (mScenic != null) {
            mPresenter.getData(mScenic.name);
            adapter.setScenicAdapter(mScenic);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener((view, position) -> {
                attention = (LinearLayout) view;
                if (!isFollow) {
                    mPresenter.followScenic(mScenic.name, 0);
                    ToastUtil.show(ScenicActivity.this, "以关注");
                } else {
                    mPresenter.followScenic(mScenic.name, 1);
                    ToastUtil.show(ScenicActivity.this, "取消关注");
                }
            });
            Glide.with(this).load(mScenic.img).error(R.mipmap.ic_launcher_round).into(imgScenic);
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setDecorView();
        mBack.setOnClickListener(v -> finish());
        mShare.setImageResource(R.mipmap.share_detail);
        mShare.setOnClickListener(v -> {
            //TODO 分享
            UploadPicture.uploadPictureNoCrop(BitmapUtils.getBytes(ScreenShotUtils.takeScreenShot(this)), new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.isOK()) {
                        Share.showShare("123456", UploadPicture.imageUrl, UploadPicture.imageUrl, "");
                    }
                }
            });
        });
    }

    private void initView() {
        imgScenic = findViewById(R.id.img_scenic_details);
        mBack = findViewById(R.id.img_toolbar_left);
        mShare = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.scenic_details_recycler_view);
    }


    @Override
    public void setAdapter(ArrayList<ScenicCommentUser> users) {
        adapter.setCommentAdapter(users);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void isFollow(int result) {
        @SuppressLint("CutPasteId") ImageView img = attention.findViewById(R.id.img_attention);
        @SuppressLint("CutPasteId") TextView tv = attention.findViewById(R.id.tv_attention);
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
}
