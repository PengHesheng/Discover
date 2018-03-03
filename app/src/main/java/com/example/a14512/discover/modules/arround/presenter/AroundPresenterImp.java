package com.example.a14512.discover.modules.arround.presenter;

import android.content.Context;

import com.example.a14512.discover.modules.arround.mode.Mode;
import com.example.a14512.discover.modules.arround.view.IAroundView;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class AroundPresenterImp implements IAroundPresenter {
    private Context mContext;
    private IAroundView mView;
    private Mode mMode;

    public AroundPresenterImp(IAroundView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getSecnics() {
        ArrayList<Scenic> scenics = (ArrayList<Scenic>) ACache.getDefault().getAsObject("around_scenic");
        if (scenics != null) {
            mView.setScenics(scenics);
        } else {
            ApiSubscriber<ArrayList<Scenic>> apiSubscriber = new ApiSubscriber<ArrayList<Scenic>>(
                    mContext, true, true) {
                @Override
                public void onNext(ArrayList<Scenic> value) {
                    if (value != null) {
                        mView.setScenics(value);
                        ACache.getDefault().put("around_scenic", value);
                    } else {
                        ToastUtil.show(mContext, "没有数据哦!");
                    }
                }
            };
            mMode.getAroundScenic(apiSubscriber);
        }
    }
}
