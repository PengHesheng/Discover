package com.example.a14512.discover.modules.route_plan.presenter;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.route_plan.mode.ModeImp;
import com.example.a14512.discover.modules.route_plan.mode.entity.Scenic;
import com.example.a14512.discover.modules.route_plan.presenter.imp.IChoosePresenter;
import com.example.a14512.discover.modules.route_plan.view.imp.IChooseView;
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
    private LatLng start, end;
    private int tfSelect, personSelect, time;
    private String startPlace;
    private String endPlace;
    private String startT;

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
                        try {
                            putRoute(start, end);
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

    private void putRoute(LatLng start, LatLng end) throws UnsupportedEncodingException {
        double startLng = start.longitude;
        double startLat = start.latitude;
        double endLng = end.longitude;
        double endLat = end.latitude;

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

//        mView.startActivity(true, personSelect);
        ApiSubscriber<ArrayList<Scenic>> apiSubscriber = new ApiSubscriber<ArrayList<Scenic>>(mContext,
                false, false) {
            @Override
            public void onNext(ArrayList<Scenic> value) {
                PLog.e(value.size()+"");
                if (value != null) {
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
