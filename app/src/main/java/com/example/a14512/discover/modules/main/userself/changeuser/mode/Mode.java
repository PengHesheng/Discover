package com.example.a14512.discover.modules.main.userself.changeuser.mode;

import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/14
 */

public class Mode {

    public void setUserInfo(ApiSubscriber<Integer> apiSubscriber,
                            String phone, String photo, String name, String sex,
                            String birth, String school, String sign, String email) {
        RetrofitHelper.getInstance().changeUserData(phone, photo, name, sex, birth, school, sign, email)
                .subscribe(apiSubscriber);
    }
}
