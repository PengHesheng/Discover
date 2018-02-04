package com.example.a14512.discover.modules.login.presenter;

import com.example.a14512.discover.modules.login.mode.Mode;
import com.example.a14512.discover.modules.login.presenter.imp.IRegisterPresenter;
import com.example.a14512.discover.modules.login.view.imp.IRegisterView;

/**
 * @author 14512 on 2018/2/2
 */

public class RegisterPresenterImp implements IRegisterPresenter {
    private IRegisterView mView;
    private Mode mMode;

    public RegisterPresenterImp(IRegisterView view) {
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getCode() {
        //TODO 获取验证码
        mView.showCodeTime();
    }

    @Override
    public void register() {
        String phone = mView.getPhoneNum();
        String pwd = mView.getPwd();
        int code = mView.getCode();
        mView.isRegister();
    }

}
