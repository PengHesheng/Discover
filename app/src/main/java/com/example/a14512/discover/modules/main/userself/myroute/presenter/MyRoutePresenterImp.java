package com.example.a14512.discover.modules.main.userself.myroute.presenter;

import android.content.Context;

import com.example.a14512.discover.modules.main.userself.myroute.mode.Mode;
import com.example.a14512.discover.modules.main.userself.myroute.mode.MyRoute;
import com.example.a14512.discover.modules.main.userself.myroute.view.IMyRouteView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class MyRoutePresenterImp implements IMyRoutePresenter{
    private Context mContext;
    private IMyRouteView mView;
    private Mode mMode;

    public MyRoutePresenterImp(IMyRouteView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getHistoricRoute() {
        ArrayList<MyRoute> routes = (ArrayList<MyRoute>) ACache.getDefault().getAsObject("my_historic");
        if (routes == null) {
            ApiSubscriber<ArrayList<MyRoute>> apiSubscriber = new ApiSubscriber<ArrayList<MyRoute>>(
                    mContext, false, false) {
                @Override
                public void onNext(ArrayList<MyRoute> value) {
                    if (value != null) {
                        mView.setHistoricRoute(value);
                        ACache.getDefault().put("my_historic", value);
                    }
                }
            };
            mMode.getHistoricRoute(apiSubscriber);
        } else {
            mView.setHistoricRoute(routes);
        }
    }

    @Override
    public void getMyCollect() {
        ArrayList<MyRoute> routes = (ArrayList<MyRoute>) ACache.getDefault().getAsObject("my_historic");
        if (routes == null) {
            ApiSubscriber<ArrayList<MyRoute>> apiSubscriber = new ApiSubscriber<ArrayList<MyRoute>>(
                    mContext, false, false) {
                @Override
                public void onNext(ArrayList<MyRoute> value) {
                    if (value != null) {
                        mView.setMyCollect(value);
                        ACache.getDefault().put("my_historic", value);
                    }
                }
            };
            mMode.getMyCollect(apiSubscriber);
        } else {
            mView.setMyCollect(routes);
        }
    }
}
