package com.example.a14512.discover.modules.login.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.login.mode.Mode;
import com.example.a14512.discover.modules.login.presenter.imp.ILoginPresenter;
import com.example.a14512.discover.modules.login.view.imp.ILoginView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/2
 */

public class LoginPresenterImp implements ILoginPresenter {
    private Context mContext;
    private ILoginView mView;
    private Mode mMode;

    public LoginPresenterImp(ILoginView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void login(boolean isRemember) {
        String account  = mView.getAccount();
        String pwd = mView.getPwd();
        if (TextUtils.isEmpty(account) && TextUtils.isEmpty(pwd)) {
            ToastUtil.show(mContext, "帐号或密码不能为空!");
        } else {
            ApiSubscriber<String> apiSubscriber = new ApiSubscriber<String>(
                    mContext, true, true, "登录中...") {
                @Override
                public void onNext(String value) {
                    if ("登录成功".equals(value)) {
                        if (isRemember) {
                            ACache.getDefault().put("pwd", pwd);
                        }
                        ToastUtil.show(value);
                        ACache.getDefault().put("account", account);
                        mView.isLogin();
                    } else {
                        ToastUtil.show(mContext, "密码错误");
                        ACache.getDefault().put("account", account);
                    }
                }

            };
            mMode.igLogin(apiSubscriber, account, pwd);
        }
    }

    @Override
    public void getPortrait() {
        String portrait = ACache.getDefault().getAsString(C.PORTRAIT);
        mView.setPortrait(portrait);
    }
}
