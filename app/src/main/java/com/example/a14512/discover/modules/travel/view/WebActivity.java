package com.example.a14512.discover.modules.travel.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/28
 */

public class WebActivity extends BaseSwipeBackActivity {

    private String title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        ImageView back = findViewById(R.id.img_toolbar_left);
        TextView title1 = findViewById(R.id.tv_toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        WebView webView = findViewById(R.id.web_view);

        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.mainToolbar));
        back.setOnClickListener(v -> finish());

        title1.setText(title);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadsImagesAutomatically(true);
//        webView.clearCache(true);
        webView.loadUrl(url);

    }
}
