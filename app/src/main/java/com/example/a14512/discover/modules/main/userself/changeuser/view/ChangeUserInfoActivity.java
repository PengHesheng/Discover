package com.example.a14512.discover.modules.main.userself.changeuser.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.changeuser.presenter.ChangeInfoPresenterImp;
import com.example.a14512.discover.utils.CompleteWatcher;
import com.example.a14512.discover.utils.KeyBoardUtil;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.UploadPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 14512 on 2018/2/13
 */

public class ChangeUserInfoActivity extends BaseActivity implements View.OnClickListener, IChangeUserInfoView {

    private PopupWindow popupWindow;
    private LinearLayout mainLayout;
    private ImageView mLeft;
    private TextView mTitle;
    private TextView mRight;
    private Toolbar toolbar;
    private ImageView mImgPortrait;
    private EditText mEdtName;
    private TextView mTvSex;
    private TextView mTvCity;
    private EditText mEdtSigned;
    private AutoCompleteTextView mAutoEmail;
    private ProgressDialog progressDialog;

    private ChangeInfoPresenterImp mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        initView();
        initToolbar();
        popWindow();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        setStatusBarColor(R.color.mainToolbar);
        mTitle.setText("修改信息");
        mLeft.setOnClickListener(v -> finish());
        mRight.setOnClickListener(v -> checkInfo());
    }

    private void checkInfo() {
        String email = mAutoEmail.getText().toString();
        PLog.e(""+ isEmail(email));
        if (!email.isEmpty() && isEmail(email)) {
            mPresenter.setUserInfo();
        } else {
            ToastUtil.show(this, "请检查你的邮箱格式");
        }

    }

    private boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void initView() {
        mainLayout = findViewById(R.id.layout_change_info);
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        mRight = findViewById(R.id.img_toolbar_right);
        toolbar = findViewById(R.id.toolbar);
        RelativeLayout layoutPortrait = findViewById(R.id.layout_change_info_portrait);
        mImgPortrait = findViewById(R.id.img_change_info_portrait);
        mEdtName = findViewById(R.id.edt_change_info_name);
        mTvSex = findViewById(R.id.tv_change_info_sex);
        mTvCity = findViewById(R.id.tv_change_info_city);
        RelativeLayout layoutCity = findViewById(R.id.layout_change_info_city);
        mEdtSigned = findViewById(R.id.edt_change_info_signed);
        mAutoEmail = findViewById(R.id.auto_tv_change_info_email);

        mainLayout.setOnClickListener(this);
        layoutPortrait.setOnClickListener(this);
        layoutCity.setOnClickListener(this);
        mTvSex.setOnClickListener(this);

        List<String> list = new ArrayList<>();
        list.add("@qq.com");
        list.add("@gmail.com");
        list.add("@163.com");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        mAutoEmail.setThreshold(9);
        mAutoEmail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mAutoEmail.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager imm = (InputMethodManager) ChangeUserInfoActivity.this.
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mAutoEmail.getWindowToken(), 0);
            }
        });
        IfCompleteWatcher watcher = new IfCompleteWatcher();
        mEdtName.addTextChangedListener(watcher);
        mTvSex.addTextChangedListener(watcher);
        mRight.setEnabled(false);

        mPresenter = new ChangeInfoPresenterImp(this, this);
        mPresenter.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_change_info:
                popupWindow.dismiss();
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                break;
            case R.id.layout_change_info_portrait:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                UploadPicture.showPictureSelectDialog(this, this);
                break;
            case R.id.tv_change_info_sex:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case R.id.layout_change_info_city:
                KeyBoardUtil.hideInputFromWindow(this, mainLayout);
//                Intent intent = new Intent(this, ChooseCityActivity.class);
//                startActivityForResult(intent, C.CHOOSE_CITY);
                break;
            default:
                break;
        }
    }

    private void popWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.window_popup_sex, null);
        popupWindow = new PopupWindow(popupView, getWindowManager().getDefaultDisplay().getWidth() / 2,
                getWindowManager().getDefaultDisplay().getHeight() / 2, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        RadioButton rbMan = popupWindow.getContentView().findViewById(R.id.rbMale);
        RadioButton rbUnKnow = popupWindow.getContentView().findViewById(R.id.rbSex);
        RadioButton rbWoman = popupWindow.getContentView().findViewById(R.id.rbFemale);

        rbMan.setOnClickListener(v -> {
            popupWindow.dismiss();
            mTvSex.setText(R.string.rb_sex_man);
        });
        rbUnKnow.setOnClickListener(v -> {
            popupWindow.dismiss();
            mTvSex.setText(R.string.rb_sex_sex);
        });
        rbWoman.setOnClickListener(v -> {
            popupWindow.dismiss();
            mTvSex.setText(R.string.rb_sex_woman);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case C.CHOOSE_CITY:
                    mTvCity.setText(data.getStringExtra("chooseItem"));
                    break;
                case C.PICK_FROM_FILE:
                    startActivityForResult(UploadPicture.cropPicture(data.getData(), 200, 200), C
                            .CHOOSE_CROPED_PICTURE);
                    break;
                case C.PICK_FROM_CAMERA:
                    startActivityForResult(UploadPicture.cropPicture(UploadPicture.cameraUri,
                            200, 200), C.CHOOSE_CROPED_PICTURE);
                    break;
                case C.CHOOSE_CROPED_PICTURE:
                    mPresenter.sendPicture();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void finishActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public String getName() {
        return mEdtName.getText().toString();
    }

    @Override
    public String getSex() {
        String sex = (String) mTvSex.getText();
        if ("男".equals(sex)) {
            return "1";
        } else if ("女".equals(sex)) {
            return "0";
        } else {
            return "-1";
        }
    }

    @Override
    public String getCity() {
        return mTvCity.getText().toString();
    }

    @Override
    public String getSigned() {
        return mEdtSigned.getText().toString();
    }

    @Override
    public String getEmail() {
        return mAutoEmail.getText().toString();
    }

    @Override
    public void setName(String name) {
        mEdtName.setText(name);
    }

    @Override
    public void setSex(String sex) {
        if (Integer.valueOf(sex) == 1) {
            mTvSex.setText("男");
        } else if (Integer.valueOf(sex) == 0) {
            mTvSex.setText("女");
        } else {
            mTvSex.setText("其他");
        }
    }

    @Override
    public void setCity(String city) {
        mTvCity.setText(city);
    }

    @Override
    public void setSigned(String signed) {
        mEdtSigned.setText(signed);
    }

    @Override
    public void setEmail(String email) {
        mAutoEmail.setText(email);
    }

    @Override
    public void setPortrait(String url) {
        Glide.with(this).load(url).error(R.mipmap.default_portrait).into(mImgPortrait);
    }

    private void ifComplete() {
        if (!(TextUtils.isEmpty(mEdtName.getText())
                || TextUtils.isEmpty(mTvSex.getText()))) {
            mRight.setTextColor(getResources().getColor(R.color.titleBlack));
            mRight.setEnabled(true);
        } else {
            mRight.setTextColor(Color.parseColor("#cbf0e8"));
            mRight.setEnabled(false);
        }
    }

    private class IfCompleteWatcher extends CompleteWatcher {
        @Override
        public void ifCompleteWatcher() {
            ifComplete();
        }
    }
}
