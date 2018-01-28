package com.example.a14512.discover.modules.main.presenter;

import android.support.v7.widget.SearchView;
import android.util.Log;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.a14512.discover.modules.main.modle.ChooseModelImp;
import com.example.a14512.discover.modules.main.presenter.imp.IChoosePresenter;
import com.example.a14512.discover.modules.main.view.IChooseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 14512 on 2018/1/28
 */

public class ChoosePresenterImp implements IChoosePresenter {
    private static final String TAG = "ChoosePresenterImp";

    private IChooseView mView;
    private ChooseModelImp mModel;


    private OnGetSuggestionResultListener listener;

    public ChoosePresenterImp(IChooseView chooseView) {
        this.mView = chooseView;
        mModel = new ChooseModelImp();
//        suggestionPoi();
    }




    public OnGetSuggestionResultListener suggestionPoi(SearchView searchView) {
        listener = res -> {

            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }

            ArrayList<String> infos = new ArrayList<>();
            List<SuggestionResult.SuggestionInfo> list = res.getAllSuggestions();
            for (SuggestionResult.SuggestionInfo info : list) {
                Log.e(TAG, info.district + "\n" + info.uid + "\n" + info.city);
                infos.add(info.district);
            }
            mView.setList(searchView, infos);
            //获取在线建议检索结果
        };

        return listener;

    }

    public OnGetSuggestionResultListener getListener() {
        return listener;
    }

    @Override
    public void putData() {
        boolean isSortDistance = mView.isSortDistance();
        boolean isLessPay = mView.isLessPay();
        boolean isLongPlay = mView.isLongPlay();
        boolean isHighComment = mView.isHighComment();
        boolean isRecommend = mView.isRecommend();
        String startPlace = mView.getStartPlace();
        String endPlace = mView.getEndPlace();
        String startTime = mView.getStartTime();
        String endTime = mView.getEndTime();
    }
}
