package com.example.a14512.discover.modules.main.userself.personality.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseActivity;
import com.example.a14512.discover.modules.main.userself.personality.presenter.PersonalityPresenterImp;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/12
 */

public class PersonalityActivity extends BaseActivity implements View.OnClickListener, IPersonalityView {

    private ImageView mLeft;
    private TextView mTitle;
    private Toolbar toolbar;
    private RadioGroup mGroup1, mGroup2, mGroup4, mGroup5;
    private RadioButton mRb11;
    private RadioButton mRb12;
    private RadioButton mRb13;
    private RadioButton mRb14;
    private RadioButton mRb21;
    private RadioButton mRb22;
    private RadioButton mRb23;
    private RadioButton mRb24;
    private CheckBox mCheckBox31;
    private CheckBox mCheckBox32;
    private CheckBox mCheckBox33;
    private CheckBox mCheckBox34;
    private CheckBox mCheckBox35;
    private CheckBox mCheckBox36;
    private RadioButton mRb41;
    private RadioButton mRb42;
    private RadioButton mRb43;
    private RadioButton mRb44;
    private RadioButton mRb51;
    private RadioButton mRb52;
    private RadioButton mRb53;
    private RadioButton mRb54;
    private RadioButton mRb55;
    private RadioButton mRb56;

    private PersonalityPresenterImp mPresenter;
    private String personality1, personality2, personality3, personality4, personality5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality);
        initView();
        initToolbar();
        groupOnChecked();
    }

    private void groupOnChecked() {
        mGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_personality11:
                    personality1 = mRb11.getText().toString();
                    break;
                case R.id.rb_personality12:
                    personality1 = mRb12.getText().toString();
                    break;
                case R.id.rb_personality13:
                    personality1 = mRb13.getText().toString();
                    break;
                case R.id.rb_personality14:
                    personality1 = mRb14.getText().toString();
                    break;
                default:
                    break;
            }
        });
        mGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_personality21:
                    personality2 = mRb21.getText().toString();
                    break;
                case R.id.rb_personality22:
                    personality2 = mRb22.getText().toString();
                    break;
                case R.id.rb_personality23:
                    personality2 = mRb23.getText().toString();
                    break;
                case R.id.rb_personality24:
                    personality2 = mRb24.getText().toString();
                    break;
                default:
                    break;
            }
        });
        mGroup4.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_personality41:
                    personality4 = mRb41.getText().toString();
                    break;
                case R.id.rb_personality42:
                    personality4 = mRb42.getText().toString();
                    break;
                case R.id.rb_personality43:
                    personality4 = mRb43.getText().toString();
                    break;
                case R.id.rb_personality44:
                    personality4 = mRb44.getText().toString();
                    break;
                default:
                    break;
            }
        });
        mGroup5.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_personality51:
                    personality5 = mRb51.getText().toString();
                    break;
                case R.id.rb_personality52:
                    personality5 = mRb52.getText().toString();
                    break;
                case R.id.rb_personality53:
                    personality5 = mRb53.getText().toString();
                    break;
                case R.id.rb_personality54:
                    personality5 = mRb54.getText().toString();
                    break;
                case R.id.rb_personality55:
                    personality5 = mRb55.getText().toString();
                    break;
                case R.id.rb_personality56:
                    personality5 = mRb56.getText().toString();
                    break;
                default:
                    break;
            }
        });
    }

    private void initToolbar() {
        setStatusBarColor(R.color.mainToolbar);
        setSupportActionBar(toolbar);
        mTitle.setText(R.string.tv_personality_toolbar_title);
        mLeft.setOnClickListener(v -> finish());
    }

    private void initView() {
        mLeft = findViewById(R.id.img_toolbar_left);
        mTitle = findViewById(R.id.tv_toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        mGroup1 = findViewById(R.id.radio_group1);
        mGroup2 = findViewById(R.id.radio_group2);
        mGroup4 = findViewById(R.id.radio_group4);
        mGroup5 = findViewById(R.id.radio_group5);
        mRb11 = findViewById(R.id.rb_personality11);
        mRb12 = findViewById(R.id.rb_personality12);
        mRb13 = findViewById(R.id.rb_personality13);
        mRb14 = findViewById(R.id.rb_personality14);
        mRb21 = findViewById(R.id.rb_personality21);
        mRb22 = findViewById(R.id.rb_personality22);
        mRb23 = findViewById(R.id.rb_personality23);
        mRb24 = findViewById(R.id.rb_personality24);
        mCheckBox31 = findViewById(R.id.cbox_personality31);
        mCheckBox32 = findViewById(R.id.cbox_personality32);
        mCheckBox33 = findViewById(R.id.cbox_personality33);
        mCheckBox34 = findViewById(R.id.cbox_personality34);
        mCheckBox35 = findViewById(R.id.cbox_personality35);
        mCheckBox36 = findViewById(R.id.cbox_personality36);
        mRb41 = findViewById(R.id.rb_personality41);
        mRb42 = findViewById(R.id.rb_personality42);
        mRb43 = findViewById(R.id.rb_personality43);
        mRb44 = findViewById(R.id.rb_personality44);
        mRb51 = findViewById(R.id.rb_personality51);
        mRb52 = findViewById(R.id.rb_personality52);
        mRb53 = findViewById(R.id.rb_personality53);
        mRb54 = findViewById(R.id.rb_personality54);
        mRb55 = findViewById(R.id.rb_personality55);
        mRb56 = findViewById(R.id.rb_personality56);
        Button btnPersonality = findViewById(R.id.btn_personality);

        btnPersonality.setOnClickListener(this);
        mPresenter = new PersonalityPresenterImp(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_personality:
                mPresenter.setPersonality(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void setPersonality1(String personality1) {
//        mGroup1.clearCheck();
//        switch (Integer.valueOf(personality1)) {
//            case 1:
//                mRb11.setChecked(true);
//                break;
//            case 2:
//                mRb12.setChecked(true);
//                break;
//            case 3:
//                mRb13.setChecked(true);
//                break;
//            case 4:
//                mRb14.setChecked(true);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void setPersonality2(String personality2) {
//        mGroup1.clearCheck();
//        switch (Integer.valueOf(personality2)) {
//            case 1:
//                mRb21.setChecked(true);
//                break;
//            case 2:
//                mRb22.setChecked(true);
//                break;
//            case 3:
//                mRb23.setChecked(true);
//                break;
//            case 4:
//                mRb24.setChecked(true);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void setPersonality3(String personality3) {

    }

    @Override
    public void setPersonality4(String personality4) {
//        mGroup1.clearCheck();
//        switch (Integer.valueOf(personality4)) {
//            case 1:
//                mRb41.setChecked(true);
//                break;
//            case 2:
//                mRb42.setChecked(true);
//                break;
//            case 3:
//                mRb43.setChecked(true);
//                break;
//            case 4:
//                mRb44.setChecked(true);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void setPersonality5(String personality5) {
//        mGroup1.clearCheck();
//        switch (Integer.valueOf(personality5)) {
//            case 1:
//                mRb51.setChecked(true);
//                break;
//            case 2:
//                mRb52.setChecked(true);
//                break;
//            case 3:
//                mRb53.setChecked(true);
//                break;
//            case 4:
//                mRb54.setChecked(true);
//                break;
//            case 5:
//                mRb55.setChecked(true);
//                break;
//            case 6:
//                mRb56.setChecked(true);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public String getPersonality1() {
        return personality1;
    }

    @Override
    public String getPersonality2() {
        return personality2;
    }

    @Override
    public String getPersonality3() {
        if (mCheckBox31.isChecked()) {
            personality3 = "1";
        }
        if (mCheckBox32.isChecked()) {
            personality3 += "2";
        }
        if (mCheckBox33.isChecked()) {
            personality3 += "3";
        }
        if (mCheckBox34.isChecked()) {
            personality3 += "4";
        }
        if (mCheckBox35.isChecked()) {
            personality3 += "5";
        }
        if (mCheckBox36.isChecked()) {
            personality3 += "6";
        }
        return personality3;
    }

    @Override
    public String getPersonality4() {
        return personality4;
    }

    @Override
    public String getPersonality5() {
        return personality5;
    }

    @Override
    public void finishActivity() {
        ToastUtil.show(this, "修改成功");
        finish();
    }
}
