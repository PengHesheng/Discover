package com.example.a14512.discover.modules.main.userself.myroute.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;

/**
 * @author 14512 on 2018/2/26
 */

public class CommentScenicActivity extends BaseActivity {

    private ImageView img_toolbar_left;
    private TextView tv_toolbar_title;
    private ImageView img_toolbar_right;
    private Toolbar toolbar;
    private RecyclerView comment_recycler_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_scenic);
        initView();
    }

    private void initView() {
        img_toolbar_left = (ImageView) findViewById(R.id.img_toolbar_left);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        img_toolbar_right = (ImageView) findViewById(R.id.img_toolbar_right);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        comment_recycler_view = (RecyclerView) findViewById(R.id.comment_recycler_view);
    }
}
