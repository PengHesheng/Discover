package com.example.a14512.discover.modules.routeplan.presenter;

import android.content.Context;

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
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.Time;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/28
 */

public class ChoosePresenterImp implements IChoosePresenter {
    private static final String TAG = "ChoosePresenterImp";

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
    private OnGetPoiSearchResultListener listener;

    public ChoosePresenterImp(IChooseView chooseView, Context context) {
        this.mContext = context;
        this.mView = chooseView;
        mModel = new ModeImp();
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
        String date = startTime.substring(0, startTime.length() - 6);
        startT = startTime.substring(startTime.length() - 5, startTime.length());
        String endT = endTime.substring(endTime.length() - 5, endTime.length());
        time = Time.calculateMinute(startT, endT);
        PLog.e("" + time);
        ACache.getDefault().put("start_time", startT);
        ACache.getDefault().put("end_time", endT);
        ACache.getDefault().put("date", date);

        poiSearch(startPlace, 1);
        poiSearch(endPlace, 2);

    }

    private void poiSearch(String startPlace, int type) {
        poiSearch = PoiSearch.newInstance();
        listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (type == 1) {
                        startScenic = new Scenic();
                        PoiInfo poiInfo = poiResult.getAllPoi().get(0);
                        startScenic.latitude = poiInfo.location.latitude;
                        startScenic.longitude = poiInfo.location.longitude;
                        startScenic.name = "我的位置";
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
                        poiSearch.destroy();
                        try {
                            putRoute(startScenic, endScenic);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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
        poiSearch.searchInCity(new PoiCitySearchOption().city("重庆市").keyword(startPlace));
    }

    private void putRoute(Scenic startScenic, Scenic endScenic) throws UnsupportedEncodingException {
        poiSearch.destroy();
        double startLng = startScenic.longitude;
        double startLat = startScenic.latitude;
        double endLng = endScenic.longitude;
        double endLat = endScenic.latitude;

        String phone = "18323954590";

        //TODO 数据测试
        startLng = 106.575662;
        startLat = 29.532033;
        endLng = 106.575532;
        endLat = 29.531644;
        time = 500;
        startT = "11:00:00";
        personSelect = 4;
        tfSelect = 1;

        ApiSubscriber<ArrayList<Scenic>> apiSubscriber = new ApiSubscriber<ArrayList<Scenic>>(
                mContext, true, false) {
            @Override
            public void onNext(ArrayList<Scenic> value) {
                PLog.e(value.size()+"");
                if (value != null) {
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
        };
        mModel.getScenic(apiSubscriber, startLng, startLat, endLng, endLat, startPlace, endPlace,
                startT, time, personSelect, tfSelect, 0, phone);
    }


    private int isTrue(boolean b) {
        return b ? 1 : 0;
    }
}
