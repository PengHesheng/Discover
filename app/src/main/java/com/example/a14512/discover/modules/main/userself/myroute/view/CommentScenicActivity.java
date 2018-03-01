package com.example.a14512.discover.modules.main.userself.myroute.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.myroute.adapter.CommentScenicAdapter;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.ScenicScore;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.CommentPresenterImp;
import com.example.a14512.discover.modules.main.userself.myroute.view.imp.ICommentView;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/26
 */

public class CommentScenicActivity extends BaseActivity implements ICommentView {

    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private CommentPresenterImp mPresenter;
    private ArrayList<ScenicScore> mScores;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_scenic);
        initView();
        initToolbar();
        getData();
    }

    private void getData() {
        mScores = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        CommentScenicAdapter adapter = new CommentScenicAdapter(mScores);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mTitle.setText("评分");
        mLeft.setOnClickListener(v -> finish());
        mRight.setImageResource(R.mipmap.save);
        mRight.setOnClickListener(v -> mPresenter.setScore());
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.comment_recycler_view);

        mPresenter = new CommentPresenterImp(this, this);
    }

    @Override
    public ArrayList<ScenicScore> getScenicScore() {
        return mScores;
    }
}
