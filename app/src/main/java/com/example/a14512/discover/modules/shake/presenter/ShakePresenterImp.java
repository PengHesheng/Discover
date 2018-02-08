package com.example.a14512.discover.modules.shake.presenter;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.a14512.discover.modules.route_plan.mode.entity.Scenic;
import com.example.a14512.discover.modules.shake.mode.Mode;
import com.example.a14512.discover.modules.shake.view.IShakeView;
import com.example.a14512.discover.utils.LocationUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author 14512 on 2018/2/8
 */

public class ShakePresenterImp implements IShakePresenter {
    private Context mContext;
    private IShakeView mView;
    private Mode mMode;
    private ArrayList<Scenic> mScenics = new ArrayList<>();

    public ShakePresenterImp(IShakeView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
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
    public void getScenic() {
        LocationUtil locationUtil = LocationUtil.getInstance();
        BDAbstractLocationListener listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Random random = new Random();
                int i = random.nextInt(5);
                ArrayList<Scenic> scenics = new ArrayList<>();
                Scenic scenic = new Scenic();
                scenic.name = "我的位置";
                scenic.latitude = bdLocation.getLatitude();
                scenic.longitude = bdLocation.getLongitude();
                scenic.location = bdLocation.getStreet();
                scenics.add(scenic);
                scenics.add(mScenics.get(i));
                mView.showScenic(scenics);
            }
        };
        locationUtil.getLocation(mContext, listener);
//        locationUtil.unRegisterListener(listener);
    }
}
