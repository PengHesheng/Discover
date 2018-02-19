package com.example.a14512.discover.modules.shake.mode;

import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/2/8
 */

public class Mode {

    public void getSharkeScenic(ApiSubscriber<Scenic> apiSubscriber) {
        RetrofitHelper.getInstance().getSharkeScenic().subscribe(apiSubscriber);
    }
}
