package com.example.a14512.discover.modules.travel.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/27
 */

public class TravelKnowledgeActivity extends BaseSwipeBackActivity {

    private ImageView mImgBack;
    private ImageView mImgRight;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_knowledge);
        initView();
        onClick();
    }

    private void onClick() {
        mImgBack.setOnClickListener(v -> finish());

    }

    private void initView() {
        mImgBack = findViewById(R.id.img_toolbar_left);
        TextView title = findViewById(R.id.tv_toolbar_title);
        mImgRight = findViewById(R.id.img_toolbar_right);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mWebView = findViewById(R.id.travel_web_view);

        setSupportActionBar(toolbar);
        title.setText("旅游小知识");
        mWebView.loadUrl("www.baidu.com");
    }
}
