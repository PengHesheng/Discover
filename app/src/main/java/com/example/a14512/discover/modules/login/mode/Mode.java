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
    public void register(ApiSubscriber<Integer> apiSubscriber, String phone, String pwd) {
        RetrofitHelper.getInstance().register(phone, pwd).subscribe(apiSubscriber);
    }

    @Override
    public void getCode(ApiSubscriber<RegisterData> apiSubscriber, String phone, String pwd) {
        RetrofitHelper.getInstance().getCode(phone, pwd).subscribe(apiSubscriber);
    }

}
