package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

import com.example.a14512.discover.modules.routeplan.mode.ModeImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.presenter.imp.IScenicPresenter;
import com.example.a14512.discover.modules.routeplan.view.imp.IScenicView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicPresenterImp implements IScenicPresenter {
    private Context mContext;
    private ModeImp mModeImp;
    private IScenicView mView;
    private ArrayList<ScenicCommentUser> mUsers = new ArrayList<>();

    public ScenicPresenterImp(IScenicView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mModeImp = new ModeImp();
    }

    @Override
    public void getData() {
        initData();
        mView.setAdapter(mUsers);
    }

    @Override
    public void followScenic(String placeName, int placeFollow) {
        String phone = ACache.getDefault().getAsString("account");
        phone = "18323954590";
        if (phone != null) {
            ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(mContext, false, false) {
                @Override
                public void onNext(Integer value) {
                    if (value != null) {
                        PLog.e(" "+value);
                        mView.isFollow(value);
                    }
                }
            };
            mModeImp.followScenic(apiSubscriber, placeFollow, phone, placeName);
        }
    }

    private void initData() {
        for (int i = 0; i <= 5; i++) {
            ScenicCommentUser user = new ScenicCommentUser();
            user.name = "One user" + i;
            user.star = i;
            mUsers.add(user);
        }
    }
}
