package com.example.a14512.discover.modules.main.userself.myroute.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.userself.myroute.mode.Mode;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.ScenicScore;
import com.example.a14512.discover.modules.main.userself.myroute.presenter.imp.ICommentPresenter;
import com.example.a14512.discover.modules.main.userself.myroute.view.imp.ICommentView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

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
    public void setScore() {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        ArrayList<ScenicScore> scores = mView.getScenicScore();
        int num = 0;
        for (ScenicScore score : scores) {
            num++;
            int finalNum = num;
            ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(mContext, true,
                true, "正在保存！") {
            @Override
            public void onNext(Integer value) {
                if (value == 1 && finalNum == scores.size()) {
                    ToastUtil.show(mContext, "评分成功，感谢您的评价！");
                } else {
//                    ToastUtil.show(mContext, "出问题了哦，请稍候再试！");
                }
            }
        };
        mMode.setScore(apiSubscriber, phone, score.name,
                score.first, score.second, score.third, score.fourth);
        }
    }

}
