package com.example.a14512.discover.modules.main.presenter;

import com.example.a14512.discover.modules.main.mode.ModeImp;
import com.example.a14512.discover.modules.main.presenter.imp.IChoosePresenter;
import com.example.a14512.discover.modules.main.view.imp.IChooseView;

/**
 * @author 14512 on 2018/1/28
 */

public class ChoosePresenterImp implements IChoosePresenter {
    private static final String TAG = "ChoosePresenterImp";

    private IChooseView mView;
    private ModeImp mModel;

    public ChoosePresenterImp(IChooseView chooseView) {
        this.mView = chooseView;
        mModel = new ModeImp();
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
