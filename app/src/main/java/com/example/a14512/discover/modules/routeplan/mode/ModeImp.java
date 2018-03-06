package com.example.a14512.discover.modules.routeplan.mode;

import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;
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

    public void getCommentUser(ApiSubscriber<ArrayList<ScenicCommentUser>> apiSubscriber, String place) {
        RetrofitHelper.getInstance().getCommentUser(place).subscribe(apiSubscriber);
    }

    public void addMyRoute(ApiSubscriber<String> apiSubscriber, String routeName, int placeNum,
                           int distance, int coast, int routeTime, String phone, String information) {
        RetrofitHelper.getInstance().addMyRoute(routeName, placeNum, distance, coast, routeTime,
                phone, information).subscribe(apiSubscriber);
    }

    public void endRoute(ApiSubscriber<Integer> apiSubscriber, String phone, String routeName) {
        RetrofitHelper.getInstance().endRoute(phone, routeName).subscribe(apiSubscriber);
    }

    public void getConsume(ApiSubscriber<ArrayList<ConsumeMode>> apiSubscriber, String place) {
        RetrofitHelper.getInstance().getConsume(place).subscribe(apiSubscriber);
    }

    public void getStrategy(ApiSubscriber<ArrayList<StrategyMode>> apiSubscriber, String place) {
        RetrofitHelper.getInstance().getStrategy(place).subscribe(apiSubscriber);
    }

}
