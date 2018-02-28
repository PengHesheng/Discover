package com.example.a14512.discover.modules.main.userself.myroute.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.userself.myroute.mode.Mode;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.imp.ICommentPresenter;
import com.example.a14512.discover.modules.main.userself.myroute.view.imp.ICommentView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;

/**
 * @author 14512 on 2018/2/28
 */

public class CommentPresenterImp implements ICommentPresenter{

    private Context mContext;
    private ICommentView mView;
    private Mode mMode;

    public CommentPresenterImp(ICommentView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void setScore(String placeName) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        int first = mView.getScenicParticularStar();
        int second = mView.getTrafficStar();
        int third = mView.getAllServiceStar();
        int fourth = mView.getHistoricStar();
        int five = mView.getPlayStar();
        PLog.e(""+first+second+third+fourth+five);
        ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(mContext, true,
                true, "正在保存！") {
            @Override
            public void onNext(Integer value) {
                if (value == 1) {
                    ToastUtil.show(mContext, "评分成功，感谢您的评价！");
                } else {
                    ToastUtil.show(mContext, "出问题了哦，请稍候再试！");
                }
            }
        };
        mMode.setScore(apiSubscriber, phone, placeName, first, second, third, fourth);
    }

    @Override
    public void followScenic(String placeName, int placeFollow) {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        if (phone != null) {
            ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(mContext, false, false) {
                @Override
                public void onNext(Integer value) {
                    if (value != null) {
                        mView.isFollow(value);
                    }
                }
            };
            mMode.followScenic(apiSubscriber, placeFollow, phone, placeName);
        }
    }
}
