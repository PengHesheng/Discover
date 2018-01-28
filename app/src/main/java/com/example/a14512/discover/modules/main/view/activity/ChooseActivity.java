package com.example.a14512.discover.modules.main.view.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseSwipeBackActivity;
import com.example.a14512.discover.modules.main.presenter.ChoosePresenterImp;
import com.example.a14512.discover.modules.main.view.IChooseView;
import com.example.a14512.discover.utils.DateTimePicker;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/27
 */

public class ChooseActivity extends BaseSwipeBackActivity implements View.OnClickListener, IChooseView {
    private static final String TAG = "ChooseActivity";

    private SuggestionSearch mSuggestionSearch;
    private ImageView mBack;
    private TextView mTitle;
    private Toolbar toolbar;
    private EditText mEdtStartPlace;
    private EditText mEdtEndPlace;
    private EditText mEdtStartTime;
    private EditText mEdtEndTime;
    private Button mBtnSortDistance;
    private Button mBtnLessPay;
    private Button mBtnLongPlay;
    private Button mBtnHighComment;
    private ImageView mImgIsRecommend;

    private ChoosePresenterImp mPresenter;
    private boolean isSortDistance = false, isLessPay = false, isLognPlay = false,
            isHighComment = false, isRecommend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_choose);
        initView();
        initToolbar();
//        search();
    }

    private void search() {
        mSuggestionSearch = SuggestionSearch.newInstance();

        // 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
//        searchListener(mSearchLeft);
//        searchListener(mSearchRight);
//        searchListener(mSearchMustGoing);

    }

    private void searchListener(SearchView searchView) {
        mSuggestionSearch.setOnGetSuggestionResultListener(mPresenter.suggestionPoi(searchView));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtil.showLong(ChooseActivity.this, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(newText)
                        .city("重庆"));
                return false;
            }
        });
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mTitle.setText("生成行程计划表");
        mBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        mBack = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
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

        mPresenter = new ChoosePresenterImp(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sort_distance:
                changeButton(mBtnSortDistance, isSortDistance);
                break;
            case R.id.btn_less_pay:
                changeButton(mBtnLessPay, isLessPay);
                break;
            case R.id.btn_long_play:
                changeButton(mBtnLongPlay, isLognPlay);
                break;
            case R.id.btn_high_comment:
                changeButton(mBtnHighComment, isHighComment);
                break;
            case R.id.btn_build:
                startIntentActivity(this, new RoutePlanActivity());
                mPresenter.putData();
                break;
            case R.id.edt_start_time:
                selectTime(mEdtStartTime);
                break;
            case R.id.edt_end_time:
                selectTime(mEdtEndTime);
                break;
            case R.id.img_exchange_place:
                exchangePlace(mEdtStartPlace.getText().toString(), mEdtEndPlace.getText().toString());
                break;
            case R.id.img_exchange_Time:
                exchangeTime(mEdtStartTime.getText().toString(), mEdtEndTime.getText().toString());
                break;
            case R.id.img_is_recommend:
                changeImg(isRecommend);
                break;
            default:
                break;
        }
    }

    private void changeImg(boolean isRecommend) {
        if (!isRecommend) {
            isRecommend = true;
        } else {
            mImgIsRecommend.setBackgroundResource(R.mipmap.check);
            isRecommend = false;
        }
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

    private void selectTime(EditText edtTime) {
        DateTimePicker dateTimePicker = new DateTimePicker(this);
        dateTimePicker.setDatePicker(edtTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    public String getStartPlace() {
        return null;
    }

    @Override
    public String getEndPlace() {
        return null;
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
    public void setList(SearchView searchView, ArrayList<String> names) {
        AutoCompleteTextView completeText = searchView.findViewById(R.id.search_src_text);
        completeText.setThreshold(1);
        completeText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));

    }

}
