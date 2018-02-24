package com.example.a14512.discover.modules.login.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.login.mode.Mode;
import com.example.a14512.discover.modules.login.presenter.imp.IRegisterPresenter;
import com.example.a14512.discover.modules.login.view.imp.IRegisterView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/2
 */

public class RegisterPresenterImp implements IRegisterPresenter {
    private Context mContext;
    private IRegisterView mView;
    private Mode mMode;

    public RegisterPresenterImp(IRegisterView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getCode() {
        String phone = mView.getPhoneNum();
        String pwd = mView.getPwd();
        if (phone != null && pwd != null) {
            ApiSubscriber<String> apiSubscriber = new ApiSubscriber<String>(
                    mContext, false, false) {
                @Override
                public void onNext(String value) {
                    ToastUtil.show(mContext, value);
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
        PLog.e(phone + ":::" + code);
        if (phone != null && pwd != null && code != null) {
            ApiSubscriber<String> apiSubscriber = new ApiSubscriber<String>(
                    mContext, true, true, "正在注册，请稍候!") {
                @Override
                public void onNext(String value) {
                    ToastUtil.show(mContext, value);
                    if ("注册成功".equals(value)) {
                        mView.isRegister();
                        ACache.getDefault().put(C.ACCOUNT, phone);
                    }
                }
            };
            mMode.register(apiSubscriber, phone, code);
        } else {
            ToastUtil.show(mContext, "电话号码或密码或验证码为空！");
        }
    }

}
