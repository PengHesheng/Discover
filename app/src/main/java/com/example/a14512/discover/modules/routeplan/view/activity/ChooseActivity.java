package com.example.a14512.discover.modules.routeplan.view.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.presenter.ChoosePresenterImp;
import com.example.a14512.discover.modules.routeplan.view.imp.IChooseView;
import com.example.a14512.discover.utils.DateTimePicker;
import com.example.a14512.discover.utils.KeyBoardUtil;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14512 on 2018/1/27
 */

public class ChooseActivity extends BaseActivity implements View.OnClickListener,
        IChooseView {
    private static final String TAG = "ChooseActivity";

    private SuggestionSearch mSuggestionSearch;
    private String city = null, street = null, myLocation = "我的位置";

    private LinearLayout mainLayout;
    private ImageView mBack;
    private TextView mTitle;
    private ImageView mRight;
    private Toolbar toolbar;
    private AppCompatAutoCompleteTextView mEdtStartPlace, mEdtEndPlace;
    private TextView mEdtStartTime, mEdtEndTime;
    private Button mBtnSortDistance;
    private Button mBtnLessPay;
    private Button mBtnLongPlay;
    private Button mBtnHighComment;
    private ImageView mImgIsRecommend;

    private LocationUtil locationUtil;
    private BDAbstractLocationListener listener;

    private ChoosePresenterImp mPresenter;
    private boolean isSortDistance = false, isLessPay = false, isLognPlay = false,
            isHighComment = false, isRecommend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        getLocation();
        initView();
        initToolbar();
    }

    private void getLocation() {
        locationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //获取详细地址信息
//                String addr = location.getAddrStr();
                //获取国家
//                String country = location.getCountry();
                //获取省份
//                String province = location.getProvince();
                //获取城市
                city = location.getCity();
                PLog.e(city);
                //获取区县
//                String district = location.getDistrict();
                //获取街道信息
                street = location.getStreet();
                locationUtil.unRegisterListener(listener);
            }
        };
        locationUtil.getLocation(this, listener);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mRight.setImageResource(R.mipmap.share);
        mTitle.setText("生成计划表");
        mBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        mainLayout = findViewById(R.id.choose_layout);
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        mEdtStartPlace = findViewById(R.id.edt_start_place);
        ImageView imgExchangePlace = findViewById(R.id.img_exchange_place);
        mEdtEndPlace = findViewById(R.id.edt_end_place);
        mEdtStartTime = findViewById(R.id.edt_start_time);
        ImageView imgExchangeTime = findViewById(R.id.img_exchange_Time);
        mEdtEndTime = findViewById(R.id.edt_end_time);
        mBtnSortDistance = findViewById(R.id.btn_sort_distance);
        toolbar = findViewById(R.id.toolbar);
        mBtnLessPay = findViewById(R.id.btn_less_pay);
        mBtnLongPlay = findViewById(R.id.btn_long_play);
        mBtnHighComment = findViewById(R.id.btn_high_comment);
        mImgIsRecommend = findViewById(R.id.img_is_recommend);
        Button btnBuild = findViewById(R.id.btn_build);

        //初始化背景
        Glide.with(this)
                .load(R.mipmap.choose_bg)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    mainLayout.setBackgroundResource(R.mipmap.choose_bg);
                }
            });
        mEdtStartTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mEdtEndTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mEdtStartPlace.setText(myLocation);

        mainLayout.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        toolbar.setOnClickListener(this);
        mEdtStartPlace.setOnClickListener(this);
        imgExchangePlace.setOnClickListener(this);
        mEdtEndPlace.setOnClickListener(this);
        mEdtStartTime.setOnClickListener(this);
        imgExchangeTime.setOnClickListener(this);
        mEdtEndTime.setOnClickListener(this);
        mBtnSortDistance.setOnClickListener(this);
        mBtnLessPay.setOnClickListener(this);
        mBtnLongPlay.setOnClickListener(this);
        mBtnHighComment.setOnClickListener(this);
        mImgIsRecommend.setOnClickListener(this);
        btnBuild.setOnClickListener(this);
        mEdtStartTime.setOnClickListener(this);
        mEdtEndTime.setOnClickListener(this);

        mPresenter = new ChoosePresenterImp(this, this);
        mSuggestionSearch = SuggestionSearch.newInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_layout:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                break;
            case R.id.edt_start_place:
                searchListener(mEdtStartPlace);
                break;
            case R.id.edt_end_place:
                searchListener(mEdtEndPlace);
                break;
            case R.id.edt_start_time:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                selectTime(mEdtStartTime);
                break;
            case R.id.edt_end_time:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                selectTime(mEdtEndTime);
                break;
            case R.id.btn_sort_distance:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                isSortDistance = changeButton(mBtnSortDistance, isSortDistance);
                break;
            case R.id.btn_less_pay:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                isLessPay = changeButton(mBtnLessPay, isLessPay);
                break;
            case R.id.btn_long_play:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                isLognPlay = changeButton(mBtnLongPlay, isLognPlay);
                break;
            case R.id.btn_high_comment:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                isHighComment = changeButton(mBtnHighComment, isHighComment);
                break;
            case R.id.btn_build:
                mPresenter.putData();
                break;
            case R.id.img_exchange_place:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                exchangePlace(mEdtStartPlace.getText().toString(), mEdtEndPlace.getText().toString());
                break;
            case R.id.img_exchange_Time:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                exchangeTime(mEdtStartTime.getText().toString(), mEdtEndTime.getText().toString());
                break;
            case R.id.img_is_recommend:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                isRecommend = changeImg(isRecommend);
                break;
            default:
                break;
        }
    }

    private void searchListener(AppCompatAutoCompleteTextView textView) {

        OnGetSuggestionResultListener listener = res -> {
            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }

            ArrayList<String> infos = new ArrayList<>();
            List<SuggestionResult.SuggestionInfo> list = res.getAllSuggestions();
            for (SuggestionResult.SuggestionInfo info : list) {
                if (info.key != null) {
                    Log.e(TAG, info.key);
                    infos.add(info.key);
                }
            }
            ArrayAdapter<String> sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, infos);
            textView.setThreshold(1);
            textView.setAdapter(sugAdapter);
            sugAdapter.notifyDataSetChanged();
            textView.setOnItemClickListener((parent, view, position, id) -> {
                InputMethodManager imm = (InputMethodManager) ChooseActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
            });
            //获取在线建议检索结果
        };
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, s.toString());
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(s.toString())
                        .city(city));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void selectTime(TextView textView) {
        DateTimePicker dateTimePicker = new DateTimePicker(this);
        dateTimePicker.setDatePicker(textView);
    }

    private boolean changeImg(boolean isRecommend) {
        if (!isRecommend) {
            mImgIsRecommend.setBackgroundResource(R.mipmap.check);
            isRecommend = true;
        } else {
            mImgIsRecommend.setBackgroundResource(R.mipmap.uncheck);
            isRecommend = false;
        }
        return isRecommend;
    }

    private void exchangeTime(String start, String end) {
        mEdtStartTime.setText(end);
        mEdtEndTime.setText(start);
    }

    private void exchangePlace(String start, String end) {
        mEdtStartPlace.setText(end);
        mEdtEndPlace.setText(start);
    }

    private boolean changeButton(Button button, boolean isChoosed) {
        if (!isChoosed) {
            button.setBackgroundResource(R.drawable.btn_bg_select);
            isChoosed = true;
        } else {
            button.setBackgroundResource(R.drawable.btn_bg_un_select);
            isChoosed = false;
        }
        return isChoosed;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    public String getStartPlace() {
        if (myLocation.equals(mEdtStartPlace.getText().toString()) && street != null) {
            return street;
        } else {
            return mEdtStartPlace.getText().toString();
        }
    }

    @Override
    public String getEndPlace() {
        return mEdtEndPlace.getText().toString();
    }

    @Override
    public boolean isSortDistance() {
        return isSortDistance;
    }

    @Override
    public boolean isLessPay() {
        return isLessPay;
    }

    @Override
    public boolean isLongPlay() {
        return isLognPlay;
    }

    @Override
    public boolean isHighComment() {
        return isHighComment;
    }

    @Override
    public boolean isRecommend() {
        return isRecommend;
    }

    @Override
    public String getStartTime() {
        return mEdtStartTime.getText().toString();
    }

    @Override
    public String getEndTime() {
        return mEdtEndTime.getText().toString();
    }

    @Override
    public void startActivity(boolean isHaveData, int personSelect) {
        if (isHaveData) {
            startIntentActivity(this, RoutePlanActivity.class, "personSelect", personSelect);
        } else {
            ToastUtil.show(this, "没有找到规划的数据！");
        }
    }

}
