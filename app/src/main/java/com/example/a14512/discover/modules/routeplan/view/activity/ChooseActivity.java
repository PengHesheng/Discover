package com.example.a14512.discover.modules.routeplan.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.routeplan.presenter.ChoosePresenterImp;
import com.example.a14512.discover.modules.routeplan.view.imp.IChooseView;
import com.example.a14512.discover.utils.DateTimePicker;
import com.example.a14512.discover.utils.KeyBoardUtil;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14512 on 2018/1/27
 */

public class ChooseActivity extends BaseActivity implements View.OnClickListener,
        IChooseView {
    private ProgressDialog mProgressDialog;
    private SuggestionSearch mSuggestionSearch;
    private String myLocation = "我的位置";

    private FrameLayout mainLayout;
    private ImageView imgBg;
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
    private ImageView mImgIsOpen;
    private LinearLayout mLayoutPriority;


    private ChoosePresenterImp mPresenter;
    private boolean isSortDistance = false, isLessPay = false, isLognPlay = false,
            isHighComment = false, isRecommend = false, isRecommend2 = false,
            isOpen = true, isFirst = true, isFirstLaunch = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initView();
        initToolbar();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initToolbar() {
        setDecorView();
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mBack.setOnClickListener(v -> finish());
        mRight.setOnClickListener(v -> ToastUtil.show(this, "share"));
    }

    private void initView() {
        mainLayout = findViewById(R.id.choose_layout);
        imgBg = findViewById(R.id.img_choose_bg);
        mBack = findViewById(R.id.img_toolbar_left);
        mRight = findViewById(R.id.img_toolbar_right);
        mEdtStartPlace = findViewById(R.id.edt_start_place);
        ImageView imgExchangePlace = findViewById(R.id.img_exchange_place);
        mEdtEndPlace = findViewById(R.id.edt_end_place);
        mEdtStartTime = findViewById(R.id.edt_start_time);
        ImageView imgExchangeTime = findViewById(R.id.img_exchange_Time);
        mEdtEndTime = findViewById(R.id.edt_end_time);
        mBtnSortDistance = findViewById(R.id.btn_sort_distance);
        toolbar = findViewById(R.id.toolbar);
        ImageView imgPriority = findViewById(R.id.img_choose_priority);
        mLayoutPriority = findViewById(R.id.layout_choose_priority);
        mBtnLessPay = findViewById(R.id.btn_less_pay);
        mBtnLongPlay = findViewById(R.id.btn_long_play);
        mBtnHighComment = findViewById(R.id.btn_high_comment);
        mImgIsRecommend = findViewById(R.id.img_is_recommend);
        mImgIsOpen = findViewById(R.id.img_is_recommend2);
        Button btnBuild = findViewById(R.id.btn_build);

        mEdtStartTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mEdtEndTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mEdtStartPlace.setText(myLocation);

        mainLayout.setOnClickListener(this);
        mBack.setOnClickListener(this);
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
        imgPriority.setOnClickListener(this);
        mImgIsOpen.setOnClickListener(this);

        Glide.with(this).load(R.drawable.choose_bg).into(imgBg);
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
                if (isFirst) {
                    mEdtStartPlace.setText("");
                    isFirst = false;
                }
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
            case R.id.img_choose_priority:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                if (isOpen) {
                    mLayoutPriority.setVisibility(View.GONE);
                    isOpen = false;
                } else {
                    mLayoutPriority.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
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
                isFirstLaunch = true;
                mPresenter.putData();
                showDialog();
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
                isRecommend = changeImg(mImgIsRecommend, isRecommend);
                break;
            case R.id.img_is_recommend2:
                isRecommend2 = changeImg(mImgIsOpen, isRecommend2);
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中...");
        mProgressDialog.show();
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
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(s.toString())
                        .city(C.CHONG_QING));
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

    private boolean changeImg(ImageView imageView, boolean isRecommend) {
        if (!isRecommend) {
            Glide.with(this).load(R.mipmap.check).into(imageView);
            isRecommend = true;
        } else {
            Glide.with(this).load(R.mipmap.uncheck).into(imageView);
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
            button.setTextColor(getResources().getColor(R.color.white));
            isChoosed = true;
        } else {
            button.setBackgroundResource(R.drawable.btn_bg_un_select);
            button.setTextColor(getResources().getColor(R.color.unchooseColor));
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
        if (myLocation.equals(mEdtStartPlace.getText().toString())) {
            return myLocation;
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
            if (isFirstLaunch) {
                startIntentActivity(this, RoutePlanActivity.class, "personSelect", personSelect);
                isFirstLaunch = false;
            }
        } else {
            ToastUtil.show(this, "没有找到规划的数据！");
        }
    }

    @Override
    public void dismissDialog() {
        mProgressDialog.dismiss();
    }

}
