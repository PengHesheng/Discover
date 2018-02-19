package com.example.a14512.discover.modules.main.userself.personality.mode;

import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/19
 */

public class Mode {

    public void setPersonality(ApiSubscriber<Object> apiSubscriber, String phone, String found) {
        RetrofitHelper.getInstance().setPersonality(phone, found).subscribe(apiSubscriber);
    }
}
