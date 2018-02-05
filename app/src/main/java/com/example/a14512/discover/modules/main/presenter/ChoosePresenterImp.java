package com.example.a14512.discover.modules.main.presenter;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.a14512.discover.modules.main.mode.ModeImp;
import com.example.a14512.discover.modules.main.presenter.imp.IChoosePresenter;
import com.example.a14512.discover.modules.main.view.imp.IChooseView;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.Time;

/**
 * @author 14512 on 2018/1/28
 */

public class ChoosePresenterImp implements IChoosePresenter {
    private static final String TAG = "ChoosePresenterImp";

    private Context mContext;
    private IChooseView mView;
    private ModeImp mModel;
    private LatLng start, end;
    private int tfSelect, personSelect, time;
    private String startPlace, endPlace;

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
        String startT = startTime.substring(startTime.length() - 5, startTime.length());
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
        PoiSearch poiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (type == 1) {
                        start = poiResult.getAllPoi().get(0).location;
                    } else {
                        end = poiResult.getAllPoi().get(0).location;
                    }
                    if (start != null && end != null) {
                        PLog.e(start.longitude + "  " + start.latitude + "\n"
                                + end.longitude + "  " + end.latitude);
                        poiSearch.destroy();
                        putRoute(start, end);

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

    private void putRoute(LatLng start, LatLng end) {
        double startLng = start.longitude;
        double startLat = start.latitude;
        double endLng = end.longitude;
        double endLat = end.latitude;

        //TODO 数据请求后进行活动跳转，此时为模拟数据
        mView.startActivity(true);

//        ApiSubscriber apiSubscriber = new ApiSubscriber(mContext, false, false) {
//            @Override
//            public void onNext(Object value) {
//                mView.startActivity(true);
//            }
//        };
//        mModel.getScenic(apiSubscriber, startLng, startLat, endLng, endLat, startPlace, endPlace, time,
//                personSelect, tfSelect, onePlace, phone);
    }


    private int isTrue(boolean b) {
        return b ? 1 : 0;
    }
}
