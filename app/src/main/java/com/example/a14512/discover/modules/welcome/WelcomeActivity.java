package com.example.a14512.discover.modules.welcome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.view.MainActivity;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.VersionUtil;

/**
 * @author 14512
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDecorView();
        setContentView(R.layout.activity_welcome);
        //启动页
        new Handler().postDelayed(this::justVersion, 2000);
    }

    private void justVersion() {
        float nowVersionCode = VersionUtil.getVersionCode(this);
        SharedPreferences sp = getSharedPreferences("welcomeInfo", MODE_PRIVATE);
        float spVersionCode = sp.getFloat("spVersionCode", 0);
        if (nowVersionCode > spVersionCode) {
            ACache.getDefault().put(C.ACCOUNT, C.ACCOUNT);
            startIntentActivity(this, GuideActivity.class);
            finish();
        } else {
            //非首次启动
            startIntentActivity(this, MainActivity.class);
            finish();
        }
    }

}
