package com.example.a14512.discover.modules.around.mode;

import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class Mode {

    public void getAroundScenic(ApiSubscriber<ArrayList<Scenic>> apiSubscriber) {
        RetrofitHelper.getInstance().getAroundScenic().subscribe(apiSubscriber);
    }
}
