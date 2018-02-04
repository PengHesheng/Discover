package com.example.a14512.discover.welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.view.activity.MainActivity;

/**
 * @author 14512
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        setDecorView();
        //启动页
        new Handler().postDelayed(this::justVersion, 2000);
    }

    private void justVersion() {
        float nowVersionCode = getVersionCode(WelcomeActivity.this);
        SharedPreferences sp = getSharedPreferences("welcomeInfo", MODE_PRIVATE);
        float spVersionCode = sp.getFloat("spVersionCode", 0);
        if (nowVersionCode > spVersionCode) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("spVersionCode", nowVersionCode);
            editor.apply();
            startIntentActivity(this, GuideActivity.class);
            finish();
        } else {
            //非首次启动
            startIntentActivity(this, MainActivity.class);
            finish();
        }
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private float getVersionCode(Context context) {
        float versionCode = 0;
        try {
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private void initView() {
        ImageView imgWelcome = findViewById(R.id.img_welcome);
        Glide.with(this).load(R.mipmap.ic_launcher_round).into(imgWelcome);
    }
}
