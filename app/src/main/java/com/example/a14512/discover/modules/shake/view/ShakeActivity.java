package com.example.a14512.discover.modules.shake.view;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.view.activity.MapActivity;
import com.example.a14512.discover.modules.shake.presenter.ShakePresenterImp;
import com.example.a14512.discover.utils.PLog;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/6
 */

public class ShakeActivity extends BaseActivity implements SensorEventListener, IShakeView{
    private ImageView mLeft;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private TextView mTvShow;

    private ShakePresenterImp mPresenter;

    SensorManager sensorManager = null;
    Vibrator vibrator = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shark);
        initView();
        initToolbar();
        shapeFun();
    }

    private void shapeFun() {
        mPresenter = new ShakePresenterImp(this, this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mLeft.setOnClickListener(v -> finish());
        mTitle.setText("摇一摇");
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        mTvShow = findViewById(R.id.tv_shape_show);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            if ((Math.abs(values[0]) > 11 || Math.abs(values[1]) > 11))
            {
                //摇动手机后，再伴随震动提示~~
                vibrator.vibrate(300);
                PLog.e("sensor x ", "============ values[0] = " + values[0]);
                PLog.e("sensor y ", "============ values[1] = " + values[1]);
                PLog.e("sensor z ", "============ values[2] = " + values[2]);
                mPresenter.getScenic();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void showScenic(ArrayList<Scenic> scenics) {
        mTvShow.setText(scenics.get(scenics.size() - 1).name);
        mTvShow.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(C.SCENIC_DETAIL, scenics);
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra(C.SCENIC_DETAIL, bundle);
            startActivity(intent);
        });
    }
}
