package com.example.a14512.discover.modules.main.userself.personality.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.userself.personality.mode.Mode;
import com.example.a14512.discover.modules.main.userself.personality.view.IPersonalityView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;

/**
 * @author 14512 on 2018/2/19
 */

public class PersonalityPresenterImp implements IPersonalityPresenter {
    private Context mContext;
    private IPersonalityView mView;
    private Mode mMode;

    public PersonalityPresenterImp(IPersonalityView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }

    @Override
    public void setPersonality(int type) {
        String personality1 = mView.getPersonality1();
        String personality2 = mView.getPersonality2();
        String personality3 = mView.getPersonality3();
        String personality4 = mView.getPersonality4();
        String personality5 = mView.getPersonality5();
        PLog.e(personality1 + personality2+personality3+personality4+personality5);
        if (type == 0) {
            personality3 = "0";
        }

        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        ApiSubscriber<Object> apiSubscriber = new ApiSubscriber<Object>(
                mContext, true, true, "正在上传...") {
            @Override
            public void onNext(Object value) {
                mView.finishActivity();
            }
        };
        mMode.setPersonality(apiSubscriber, phone, personality3);
    }
}
