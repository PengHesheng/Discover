package com.example.a14512.discover.modules.main.view.activity;

import android.os.Bundle;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;

/**
 * @author 14512 on 2018/1/31
 */

public class ScenicActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic);

        initToolbar();
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
    }
}
