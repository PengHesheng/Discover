package com.example.a14512.discover.modules.login.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.login.mode.Mode;
import com.example.a14512.discover.modules.login.mode.RegisterData;
import com.example.a14512.discover.modules.login.presenter.imp.IRegisterPresenter;
import com.example.a14512.discover.modules.login.view.imp.IRegisterView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/2
 */

public class RegisterPresenterImp implements IRegisterPresenter {
    private Context mContext;
    private IRegisterView mView;
    private Mode mMode;
    private int mCode;

    public RegisterPresenterImp(IRegisterView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getCode() {
        String phone = mView.getPhoneNum();
        String pwd = mView.getPwd();
        if (phone.isEmpty() && pwd.isEmpty()) {
            ApiSubscriber<RegisterData> apiSubscriber = new ApiSubscriber<RegisterData>(
                    mContext, false, false) {
                @Override
                public void onNext(RegisterData value) {
                    ToastUtil.show(mContext, value.getMessage());
                    if (value.getMessage().equals("短信发送成功")) {
                        mCode = value.getTextCode();
                    }
                }
            };
            mMode.getCode(apiSubscriber, phone, pwd);
            mView.showCodeTime();
        } else {
            ToastUtil.show(mContext, "电话号码或者密码为空！");
        }
    }

    @Override
    public void register() {
        String phone = mView.getPhoneNum();
        String pwd = mView.getPwd();
        String code = mView.getCode();
        if (phone.isEmpty() && pwd.isEmpty() && code.isEmpty()) {
            if (Integer.valueOf(code) == mCode) {
                ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(
                        mContext, true, true, "正在注册，请稍候!") {
                    @Override
                    public void onNext(Integer value) {
                        if (value == 1) {
                            ToastUtil.show(mContext, "注册成功！");
                            mView.isRegister();
                            ACache.getDefault().put(C.ACCOUNT, phone);
                        } else {
                            ToastUtil.show(mContext, "注册失败！");
                        }
                    }
                };
                mMode.register(apiSubscriber, phone, code);
            } else {
                ToastUtil.show(mContext, "验证码错误！");
            }
        } else {
            ToastUtil.show(mContext, "电话号码或密码或验证码为空！");
        }
    }

}
