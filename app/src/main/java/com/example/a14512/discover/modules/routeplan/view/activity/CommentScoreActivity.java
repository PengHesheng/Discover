package com.example.a14512.discover.modules.routeplan.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.view.MainActivity;

/**
 * @author 14512 on 2018/2/14
 */

public class CommentScoreActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private Button mSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_score);
        initView();
        initToolbar();
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mTitle.setText("评分");
        mLeft.setOnClickListener(v -> finish());
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mSubmit = findViewById(R.id.btn_comment_score_submit);

        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_score_submit:
                startIntentActivity(this, MainActivity.class);
                break;
            default:
                break;
        }
    }
}
