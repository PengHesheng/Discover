package com.example.a14512.discover.modules.main.userself.attention.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.userself.attention.mode.Mode;
import com.example.a14512.discover.modules.main.userself.attention.view.IMyAttentionView;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class MyAttentionPresenterImp implements IMyAttentionPresenter {
    private Context mContext;
    private IMyAttentionView mView;
    private Mode mMode;

    public MyAttentionPresenterImp(IMyAttentionView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getMyFollow() {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        if (phone != null) {
            ApiSubscriber<ArrayList<Scenic>> apiSubscriber = new ApiSubscriber<ArrayList<Scenic>>(
                    mContext, true, false) {
                @Override
                public void onNext(ArrayList<Scenic> value) {
                    mView.setAdapter(value);
                    ACache.getDefault().put("my_follow", value);
                }
            };
            mMode.getMyFollow(apiSubscriber, phone);
        }
    }

    @Override
    public boolean getMyFollowFromACache() {
        ArrayList<Scenic> myFollow = (ArrayList<Scenic>) ACache.getDefault().getAsObject("my_follow");
        if (myFollow == null) {
            return false;
        } else {
            mView.setAdapter(myFollow);
            return true;
        }
    }
}
