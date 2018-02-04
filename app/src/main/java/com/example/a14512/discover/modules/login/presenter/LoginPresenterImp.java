package com.example.a14512.discover.modules.login.presenter;

import android.text.TextUtils;

import com.example.a14512.discover.modules.login.mode.Mode;
import com.example.a14512.discover.modules.login.presenter.imp.ILoginPresenter;
import com.example.a14512.discover.modules.login.view.imp.ILoginView;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/2
 */

public class LoginPresenterImp implements ILoginPresenter {
    private ILoginView mView;
    private Mode mMode;

    public LoginPresenterImp(ILoginView view) {
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void login(boolean isRemember) {
        String account  = mView.getAccount();
        String pwd = mView.getPwd();
        if (TextUtils.isEmpty(account) && TextUtils.isEmpty(pwd)) {
            ToastUtil.show("帐号或密码不能为空!");
        } else {
            ToastUtil.show("正在登陆！");
            mView.isLogin();
            if (isRemember) {
                ToastUtil.show("remember");
            }
        }
    }
}
