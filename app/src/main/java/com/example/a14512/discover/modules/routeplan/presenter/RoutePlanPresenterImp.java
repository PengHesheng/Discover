package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.routeplan.mode.ModeImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.presenter.imp.IRoutePlanPresenter;
import com.example.a14512.discover.modules.routeplan.view.imp.IRoutePlanView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/29
 */

public class RoutePlanPresenterImp implements IRoutePlanPresenter{
    private Context mContext;
    private IRoutePlanView mView;
    private ModeImp mModel;
    private ArrayList<Scenic> mScenics = new ArrayList<>();

    public RoutePlanPresenterImp(IRoutePlanView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mModel = new ModeImp();
        initData();
    }

    private void initData() {
        Scenic scenic1 = new Scenic();
        scenic1.name = "重庆洪崖洞";
        scenic1.latitude = 29.568133;
        scenic1.longitude = 106.584801;
        scenic1.time = 60;
        scenic1.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒";
        scenic1.peopleAver = 53;
        scenic1.monthAver = 20;
        mScenics.add(scenic1);

        Scenic scenic2 = new Scenic();
        scenic2.name = "磁器口古镇";
        scenic2.latitude = 29.585828;
        scenic2.longitude = 106.458114;
        scenic2.time = 60;
        scenic2.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒";
        scenic2.peopleAver = 48;
        scenic2.monthAver = 15;
        mScenics.add(scenic2);

        Scenic scenic3 = new Scenic();
        scenic3.name = "重庆中国三峡博物馆";
        scenic3.latitude = 29.568259;
        scenic3.longitude = 106.556901;
        scenic3.time = 60;
        scenic3.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒";
        scenic3.peopleAver = 52;
        scenic3.monthAver = 0;
        mScenics.add(scenic3);

        Scenic scenic4 = new Scenic();
        scenic4.name = "重庆动物园";
        scenic4.latitude = 29.509211;
        scenic4.longitude = 106.512557;
        scenic4.time = 60;
        scenic4.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒";
        scenic4.peopleAver = 58;
        scenic4.monthAver = 0;
        mScenics.add(scenic4);

        Scenic scenic5 = new Scenic();
        scenic5.name = "重庆南山植物园";
        scenic5.latitude = 29.560988;
        scenic5.longitude = 106.637701;
        scenic5.time = 60;
        scenic5.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒";
        scenic5.peopleAver = 56;
        scenic5.monthAver = 30;
        mScenics.add(scenic5);
    }

    @Override
    public void getData() {
        ArrayList<Scenic> morning;
        ArrayList<Scenic> afternoon;
        ArrayList<Scenic> evening;
        ArrayList<Scenic> scenics = (ArrayList<Scenic>) ACache.getDefault().getAsObject(C.SCENIC_DETAIL);
        if (scenics != null) {
           morning = getScenics(scenics, -1);
           afternoon = getScenics(scenics, 0);
           evening = getScenics(scenics, 1);
        } else {
            morning = getInitData(0, 1);
            afternoon = getInitData(2, 2);
            evening = getInitData(3, 4);
        }

        if (!morning.isEmpty()) {
            mView.setAdapter(morning, C.MORNING);
        }
        if (!afternoon.isEmpty()) {
            mView.setAdapter(afternoon, C.AFTERNOON);
        }
        if (!evening.isEmpty()) {
            mView.setAdapter(evening, C.EVENING);
        }

    }

    @Override
    public void deleteOneData(String category, String changePlace, int position,
                              String last, String next, int personSlect) {
        ApiSubscriber<Scenic> apiSubscriber = new ApiSubscriber<Scenic>(mContext, true,
                true, "更新数据中...") {
            @Override
            public void onNext(Scenic value) {
                if (value != null) {
                    mView.setOneData(position, value, category);
                } else {
                    ToastUtil.show(mContext, "没有数据了！");
                }
            }
        };
        mModel.changeOneScenic(apiSubscriber, changePlace, last, next, personSlect);
    }

    private ArrayList<Scenic> getScenics(ArrayList<Scenic> scenics, int type) {
        ArrayList<Scenic> scenicArrayList = new ArrayList<>();
        for (Scenic scenic : scenics) {
            PLog.e(scenic.timeType);
            if (scenic.timeType != null && Integer.valueOf(scenic.timeType) == type) {
                scenicArrayList.add(scenic);
            }
        }
        PLog.e(scenicArrayList.size()+"");
        return scenicArrayList;
    }

    private ArrayList<Scenic> getInitData(int start, int end) {
        ArrayList<Scenic> scenics = new ArrayList<>();
        for (int i = start; i<=end; i++) {
            scenics.add(mScenics.get(i));
        }
        return scenics;
    }

}
