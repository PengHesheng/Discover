package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.routeplan.mode.ModeImp;
import com.example.a14512.discover.modules.routeplan.presenter.imp.IAllRoutePresenter;
import com.example.a14512.discover.modules.routeplan.view.imp.IGoGuideView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.Time;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/3/2
 */

public class GoGuidePresenterImp implements IAllRoutePresenter {
    private Context mContext;
    private IGoGuideView mView;
    private ModeImp mMode;

    public GoGuidePresenterImp(IGoGuideView view, Context context) {
        this.mView = view;
        this.mContext = context;
        mMode = new ModeImp();
    }

    @Override
    public void addMyRoute(String information) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        if (phone.equals(C.ACCOUNT)) {
            ToastUtil.show(mContext, "还没有登录，请登录后再试！");
            return;
        }
        String routeName = mView.getRouteName();
        int placeNum = mView.getPlaceNum();
        int distance = mView.getAllDistance();
        int coast = mView.getAllCoast();
        int routeTime = mView.getRouteTime();
        String saveTime = Time.getNowYMD();
        PLog.e(routeName);
        ApiSubscriber<String> apiSubscriber = new ApiSubscriber<String>(mContext, true,
                true, "正在保存路线...") {
            @Override
            public void onNext(String value) {
                if (value.equals("suceess")) {
                    ToastUtil.show(mContext, "保存路线成功！");
                } else {
                    ToastUtil.show(mContext, "出问题咯！");
                }
            }
        };
        mMode.addMyRoute(apiSubscriber, routeName, placeNum, distance,
                coast, routeTime, phone, information);
    }

    @Override
    public void endRoute(String routeName) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        if (phone.equals(C.ACCOUNT)) {
            ToastUtil.show(mContext, "请先登录！");
        } else {
            ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(
                    mContext, false, false) {
                @Override
                public void onNext(Integer value) {

                }
            };
            mMode.endRoute(apiSubscriber, phone, routeName);
        }
    }
}
