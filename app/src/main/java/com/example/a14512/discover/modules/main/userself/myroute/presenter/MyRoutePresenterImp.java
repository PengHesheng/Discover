package com.example.a14512.discover.modules.main.userself.myroute.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.userself.myroute.mode.Mode;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.imp.IMyRoutePresenter;
import com.example.a14512.discover.modules.main.userself.myroute.view.imp.IMyRouteView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class MyRoutePresenterImp implements IMyRoutePresenter {
    private Context mContext;
    private IMyRouteView mView;
    private Mode mMode;

    public MyRoutePresenterImp(IMyRouteView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getHistoricRouteFromACache() {
        ArrayList<MyRoute> routes = (ArrayList<MyRoute>) ACache.getDefault().getAsObject("my_historic");
        if (routes != null) {
            mView.setHistoricRoute(routes);
        }
    }

    @Override
    public void getMyCollectFromACache() {
        ArrayList<MyRoute> routes = (ArrayList<MyRoute>) ACache.getDefault().getAsObject("my_collect");
        if (routes != null) {
            mView.setMyCollect(routes);
        }
    }

    @Override
    public void getMtRoute(int type) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        ApiSubscriber<ArrayList<MyRoute>> apiSubscriber = new ApiSubscriber<ArrayList<MyRoute>>(
                mContext, true, true) {
            @Override
            public void onNext(ArrayList<MyRoute> value) {
                if (!value.isEmpty()) {
                    PLog.e(value.get(0).getRoute_name());
                    chooseMyRoute(value, type);
                }
            }
        };
        mMode.getMyRoute(apiSubscriber, phone);
    }

    @Override
    public boolean isACache() {
        ArrayList<MyRoute> historicRoutes = (ArrayList<MyRoute>) ACache.getDefault()
                .getAsObject("my_historic");
        ArrayList<MyRoute> myRoutes = (ArrayList<MyRoute>) ACache.getDefault()
                .getAsObject("my_collect");
        return myRoutes != null || historicRoutes != null;
    }

    private void chooseMyRoute(ArrayList<MyRoute> routes, int type) {
        ArrayList<MyRoute> historicRoutes = new ArrayList<>();
        ArrayList<MyRoute> collectRoutes = new ArrayList<>();
        for (MyRoute myRoute : routes) {
            if (myRoute.getRoute_found() == 1) {
                historicRoutes.add(myRoute);
            } else if (myRoute.getRoute_found() == 0) {
                collectRoutes.add(myRoute);
            }
        }
        ACache.getDefault().put("my_historic", historicRoutes);
        ACache.getDefault().put("my_collect", collectRoutes);
        if (type == 0) {
            mView.setMyCollect(collectRoutes);
        } else {
            mView.setHistoricRoute(historicRoutes);
        }
    }
}
