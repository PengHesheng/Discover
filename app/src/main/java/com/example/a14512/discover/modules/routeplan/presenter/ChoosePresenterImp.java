package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.routeplan.mode.ModeImp;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.presenter.imp.IChoosePresenter;
import com.example.a14512.discover.modules.routeplan.view.imp.IChooseView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.DateFormatUtil;
import com.example.a14512.discover.utils.LocationUtil;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/28
 */

public class ChoosePresenterImp implements IChoosePresenter {

    private Context mContext;
    private IChooseView mView;
    private ModeImp mModel;
    private int tfSelect, personSelect, time;
    private String startPlace;
    private String endPlace;
    private String startT;
    private Scenic startScenic;
    private Scenic endScenic;

    private PoiSearch poiSearch;

    private LocationUtil locationUtil;
    private BDAbstractLocationListener listener;
    private String myLocation = "我的位置";

    public ChoosePresenterImp(IChooseView chooseView, Context context) {
        this.mContext = context;
        this.mView = chooseView;
        mModel = new ModeImp();
        poiSearch = PoiSearch.newInstance();
    }

    private void getLocation() {
        locationUtil = LocationUtil.getInstance();
        listener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                PLog.e(location.getStreet());
                if (startPlace.equals(myLocation)) {
                    startScenic = new Scenic();
                    startScenic.latitude = location.getLatitude();
                    startScenic.longitude = location.getLongitude();
                    if (!location.getStreet().isEmpty()) {
                        startScenic.name = location.getStreet();
                    } else {
                        startScenic.name = myLocation;
                    }
                    startScenic.times = "-2";
                } else {
                    poiSearch(startPlace, 1);
                }
                poiSearch(endPlace, 2);
                locationUtil.unRegisterListener(listener);
            }
        };
        locationUtil.getLocation(mContext, listener);
    }

    @Override
    public void putData() {
        tfSelect = isTrue(mView.isRecommend());
        personSelect = isTrue(mView.isSortDistance()) + isTrue(mView.isLessPay())
                + isTrue(mView.isLongPlay()) + isTrue(mView.isHighComment());
        startPlace = mView.getStartPlace();
        endPlace = mView.getEndPlace();

        String startTime = mView.getStartTime();
        String endTime = mView.getEndTime();
        ACache.getDefault().put("start_time", startTime);
        ACache.getDefault().put("end_time", endTime);
        String sT = ACache.getDefault().getAsString("start_time");
        String eT = ACache.getDefault().getAsString("end_time");

        startT = startTime.substring(startTime.length() - 5, startTime.length());
        time = DateFormatUtil.calculateMinute(sT, eT);
        PLog.e(""+time);
        if (time > 0) {
            getLocation();
        } else {
            ToastUtil.show(mContext, "时间选取为三天内！");
        }
    }

    private void poiSearch(String startPlace, int type) {
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (type == 1) {
                        startScenic = new Scenic();
                        PoiInfo poiInfo = poiResult.getAllPoi().get(0);
                        startScenic.latitude = poiInfo.location.latitude;
                        startScenic.longitude = poiInfo.location.longitude;
                        startScenic.name = poiInfo.name;
                        startScenic.times = "-2";
                    } else {
                        endScenic = new Scenic();
                        PoiInfo poiInfo = poiResult.getAllPoi().get(0);
                        endScenic.name = endPlace;
                        endScenic.latitude = poiInfo.location.latitude;
                        endScenic.longitude = poiInfo.location.longitude;
                        endScenic.times = "2";
                    }
                    if (startScenic != null && endScenic != null) {
                        PLog.e(startScenic.longitude + "  " + startScenic.latitude + "\n"
                                + endScenic.longitude + "  " + endScenic.latitude);
                        putRoute(startScenic, endScenic);
                        poiSearch.destroy();
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        poiSearch.setOnGetPoiSearchResultListener(listener);
        poiSearch.searchInCity(new PoiCitySearchOption().city(C.CHONG_QING).keyword(startPlace).pageNum(1));
    }

    private void putRoute(Scenic startScenic, Scenic endScenic) {
        double startLng = startScenic.longitude;
        double startLat = startScenic.latitude;
        double endLng = endScenic.longitude;
        double endLat = endScenic.latitude;
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        ApiSubscriber<ArrayList<Scenic>> apiSubscriber = new ApiSubscriber<ArrayList<Scenic>>(
                mContext, false, false) {
            @Override
            public void onNext(ArrayList<Scenic> value) {
                PLog.e(value.size()+"");
                if (!value.isEmpty()) {
                    value.add(0, startScenic);
                    value.add(endScenic);
                    ACache.getDefault().put(C.SCENIC_DETAIL, value);
                    PLog.e(value.get(0).name);
                    mView.startActivity(true, personSelect);
                } else {
                    mView.startActivity(false, personSelect);
                    PLog.e("null");
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                mView.dismissDialog();
            }
        };
        mModel.getScenic(apiSubscriber, startLng, startLat, endLng, endLat, startPlace, endPlace,
                startT, time, personSelect, tfSelect, 0, phone);
    }


    private int isTrue(boolean b) {
        return b ? 1 : 0;
    }
}
