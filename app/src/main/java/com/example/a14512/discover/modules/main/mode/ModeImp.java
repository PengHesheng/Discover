package com.example.a14512.discover.modules.main.mode;

import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

/**
 * @author 14512 on 2018/1/28
 */

public class ModeImp {

    public void getScenic(ApiSubscriber<Object> apiSubscriber, double startLng, double startLat,
                          double endLng, double endLat, String startName, String endName,
                          long time, int personSelect, int tfSelect,
                          int onePlace, String phone) {
        RetrofitHelper.getInstance().getScenic(startLng, startLat, endLng, endLat, startName, endName,
                time, personSelect, tfSelect, onePlace, phone).subscribe(apiSubscriber);
    }

    public void changeOneScenic(ApiSubscriber<Object> apiSubscriber, String changePlace, String lastPlace,
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
