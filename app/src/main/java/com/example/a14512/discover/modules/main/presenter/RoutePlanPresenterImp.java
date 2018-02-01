package com.example.a14512.discover.modules.main.presenter;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.modle.ModelImp;
import com.example.a14512.discover.modules.main.modle.entity.Scenic;
import com.example.a14512.discover.modules.main.presenter.imp.IRoutePlanPresenter;
import com.example.a14512.discover.modules.main.view.imp.IRoutePlanView;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/29
 */

public class RoutePlanPresenterImp implements IRoutePlanPresenter{
    private IRoutePlanView mView;
    private ModelImp mModel;
    private ArrayList<Scenic> mScenics = new ArrayList<>();

    public RoutePlanPresenterImp(IRoutePlanView view) {
        this.mView = view;
        mModel = new ModelImp();
        initData();
    }

    private void initData() {
        for (int i = 0; i <= 6; i++) {
            Scenic scenic = new Scenic();
            scenic.name = "name" +i;
            scenic.monthAver = "人气" + 80 + i;
            scenic.peopleAver =  "" + 50 +i;
            scenic.location = "南坪" + i;
            scenic.time = "8:00-10:00" + i;
            scenic.img = "R.mipmap.ic_launcher";
            scenic.content = "打开了房间哈收到两份卢卡斯地方那蓝色的发货了安利的福建省啊进了房间拉萨的空间放辣椒" + i;
            mScenics.add(scenic);
        }
    }

    @Override
    public void getData() {
        ArrayList<Scenic> morning = getInitData(0, 2);
        ArrayList<Scenic> afternoon = getInitData(3, 4);
        ArrayList<Scenic> evening = getInitData(5, 6);
        if (morning != null) {
            mView.setAdapter(morning, C.MORNING);
        }
        if (afternoon != null) {
            mView.setAdapter(afternoon, C.AFTERNOON);
        }
        if (evening != null) {
            mView.setAdapter(evening, C.EVENING);
        }
    }

    private ArrayList<Scenic> getInitData(int start, int end) {
        ArrayList<Scenic> scenics = new ArrayList<>();
        for (int i = start; i<=end; i++) {
            scenics.add(mScenics.get(i));
        }
        return scenics;
    }

    @Override
    public void deleteOneData(String category, int position) {
        ArrayList<Scenic> scenics = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            Scenic scenic = new Scenic();
            scenic.name = "names" +i;
            scenic.monthAver = "人气s" + 80 + i;
            scenic.peopleAver = "人均s" + 50 +i;
            scenic.location = "南坪s" + i;
            scenic.time = "8:00-10:00s" + i;
            scenic.img = "R.mipmap.ic_launcher";
            scenics.add(scenic);
        }
        mView.setOneData(position, scenics.get(position), category);
    }

}
