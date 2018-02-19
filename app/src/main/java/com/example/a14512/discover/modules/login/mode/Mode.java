package com.example.a14512.discover.modules.login.mode;

import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/2
 */

public class Mode implements Imode {

    @Override
    public void igLogin(ApiSubscriber<String> apiSubscriber, String phone, String pwd) {
        RetrofitHelper.getInstance().isLogin(phone, pwd).subscribe(apiSubscriber);
    }

    @Override
    public void register(ApiSubscriber<String> apiSubscriber, String phone, String code) {
        RetrofitHelper.getInstance().register(phone, code).subscribe(apiSubscriber);
    }

    @Override
    public void getCode(ApiSubscriber<String> apiSubscriber, String phone, String pwd) {
        RetrofitHelper.getInstance().getCode(phone, pwd).subscribe(apiSubscriber);
    }

}
