package com.example.a14512.discover.modules.main.userself.myroute.mode;

import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;
import com.example.a14512.discover.network.RetrofitHelper;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class Mode {

    public void getMyRoute(ApiSubscriber<ArrayList<MyRoute>> apiSubscriber, String phone) {
        RetrofitHelper.getInstance().getMyRoute(phone).subscribe(apiSubscriber);
    }

    public void setScore(ApiSubscriber<Integer> apiSubscriber, String phone, String placeName,
                         int firstScore, int secondScore, int thirdScore, int fourthScore) {
        RetrofitHelper.getInstance().setCommentScore(phone, placeName, firstScore, secondScore,
                thirdScore, fourthScore).subscribe(apiSubscriber);
    }

    public void followScenic(ApiSubscriber<Integer> apiSubscriber,
                             int isFollow, String phone, String placeName) {
        RetrofitHelper.getInstance().followScenic(isFollow, phone, placeName)
                .subscribe(apiSubscriber);
    }
}
