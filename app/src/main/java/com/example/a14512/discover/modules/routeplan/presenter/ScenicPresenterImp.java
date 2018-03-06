package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.routeplan.mode.ModeImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;
import com.example.a14512.discover.modules.routeplan.presenter.imp.IScenicPresenter;
import com.example.a14512.discover.modules.routeplan.view.imp.IScenicView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicPresenterImp implements IScenicPresenter {
    private Context mContext;
    private ModeImp mModeImp;
    private IScenicView mView;

    public ScenicPresenterImp(IScenicView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mModeImp = new ModeImp();
    }

    @Override
    public void getData(String place) {
        ApiSubscriber<ArrayList<ScenicCommentUser>> apiSubscriber =
                new ApiSubscriber<ArrayList<ScenicCommentUser>>(mContext, true, true) {
            @Override
            public void onNext(ArrayList<ScenicCommentUser> value) {
                if (value != null) {
                    mView.setCommentAdapter(value);
                }
            }
        };
        mModeImp.getCommentUser(apiSubscriber, place);

        ApiSubscriber<ArrayList<ConsumeMode>> apiSubscriber1 =
                new ApiSubscriber<ArrayList<ConsumeMode>>(mContext, false, false) {
            @Override
            public void onNext(ArrayList<ConsumeMode> value) {
                if (value != null) {
                    mView.setConsumeAdapter(value);
                }
            }
        };
        mModeImp.getConsume(apiSubscriber1, place);

        ApiSubscriber<ArrayList<StrategyMode>> apiSubscriber2 =
                new ApiSubscriber<ArrayList<StrategyMode>>(mContext, false, false) {
            @Override
            public void onNext(ArrayList<StrategyMode> value) {
                if (value != null) {
                    mView.setStrategyAdapter(value);
                }
            }
        };
        mModeImp.getStrategy(apiSubscriber2, place);
    }

    @Override
    public void followScenic(String placeName, int placeFollow) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        if (phone != null) {
            ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(mContext, false, false) {
                @Override
                public void onNext(Integer value) {
                    if (value != null) {
                        mView.isFollow(value);
                    }
                }
            };
            mModeImp.followScenic(apiSubscriber, placeFollow, phone, placeName);
        }
    }

}
