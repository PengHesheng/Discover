package com.example.a14512.discover.modules.main.mode;

import com.example.a14512.discover.modules.main.mode.entity.Scenic;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/28
 */

public class ModeImp {

    public void getScenic(ApiSubscriber<ArrayList<Scenic>> apiSubscriber, double startLng, double startLat,
                          double endLng, double endLat, String startName, String endName,
                          String startTime, long time, int personSelect, int tfSelect,
                          int onePlace, String phone) {
        RetrofitHelper.getInstance().getScenic(startLng, startLat, endLng, endLat, startName, endName,
                startTime, time, personSelect, tfSelect, onePlace, phone).subscribe(apiSubscriber);
    }

    public void changeOneScenic(ApiSubscriber<Scenic> apiSubscriber, String changePlace, String lastPlace,
                              String nextPlace, int personSelect) {
        RetrofitHelper.getInstance().chaneOneScenic(changePlace, lastPlace, nextPlace, personSelect)
                .subscribe(apiSubscriber);
    }

    public void followScenic(ApiSubscriber<Integer> apiSubscriber,
                             int isFollow, String phone, String placeName) {
        RetrofitHelper.getInstance().followScenic(isFollow, phone, placeName)
                .subscribe(apiSubscriber);
    }



}
