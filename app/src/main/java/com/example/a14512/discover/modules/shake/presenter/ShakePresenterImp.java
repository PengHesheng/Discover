package com.example.a14512.discover.modules.shake.presenter;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.shake.mode.Mode;
import com.example.a14512.discover.modules.shake.view.IShakeView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.LocationUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/8
 */

public class ShakePresenterImp implements IShakePresenter {
    private Context mContext;
    private IShakeView mView;
    private Mode mMode;
    private BDAbstractLocationListener listener;
    private LocationUtil locationUtil;

    public ShakePresenterImp(IShakeView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void getScenic() {
        locationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                ApiSubscriber<Scenic> apiSubscriber = new ApiSubscriber<Scenic>(mContext,
                        false, false) {
                    @Override
                    public void onNext(Scenic value) {
                        if (value != null) {
                            ArrayList<Scenic> scenics = new ArrayList<>();
                            Scenic scenic = new Scenic();
                            scenic.name = bdLocation.getStreet();
                            scenic.latitude = bdLocation.getLatitude();
                            scenic.longitude = bdLocation.getLongitude();
                            scenic.location = bdLocation.getAddrStr();
                            scenics.add(scenic);
                            scenics.add(value);
                            mView.showScenic(scenics);
                            unRegisterListener();
                        }
                    }
                };
                mMode.getSharkeScenic(apiSubscriber);
            }
        };
        locationUtil.getLocation(mContext, listener);
    }

    private void unRegisterListener() {
        locationUtil.unRegisterListener(listener);
    }

}
