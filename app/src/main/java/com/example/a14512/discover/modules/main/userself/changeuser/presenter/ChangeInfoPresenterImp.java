package com.example.a14512.discover.modules.main.userself.changeuser.presenter;

import android.content.Context;

import com.example.a14512.discover.C;
import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.userself.changeuser.mode.Mode;
import com.example.a14512.discover.modules.main.userself.changeuser.view.IChangeUserInfoView;
import com.example.a14512.discover.network.RxUtil.ApiSubscriber;
import com.example.a14512.discover.utils.ACache;
import com.example.a14512.discover.utils.PLog;
import com.example.a14512.discover.utils.ToastUtil;
import com.example.a14512.discover.utils.UploadPicture;

/**
 * @author 14512 on 2018/2/14
 */

public class ChangeInfoPresenterImp implements IChangeInfoPrensenter {
    private Context mContext;
    private IChangeUserInfoView mView;
    private Mode mMode;

    public ChangeInfoPresenterImp(IChangeUserInfoView view, Context context) {
        this.mContext = context;
        this.mView = view;
        mMode = new Mode();
    }


    @Override
    public void setUserInfo() {
        if (UploadPicture.cameraUri == null || UploadPicture.cropUri == null) {
            sendData();
        } else {
            UploadPicture.uploadPicture((key, info, response) -> {
                PLog.e(info.error);
                if (info.isOK()) {
                    ToastUtil.show(mContext, "头像上传成功");
                    sendData();
                } else {
                    ToastUtil.show(mContext, "头像上传失败");
                }
            });
        }
    }

    private void sendData() {
        String phone = ACache.getDefault().getAsString(C.ACCOUNT);
        String name = mView.getName();
        String sex = mView.getSex();
        String city = mView.getCity();
        String signed = mView.getSigned();
        String email = mView.getEmail();
        ApiSubscriber<Integer> apiSubscriber = new ApiSubscriber<Integer>(
                mContext, true, true, "上传中...") {
            @Override
            public void onNext(Integer value) {
                if (value == 1) {
                    ToastUtil.show(mContext, "上传成功！");
                    mView.finishActivity();
                } else {
                    ToastUtil.show(mContext, "上传失败！");
                }
            }
        };
        mMode.setUserInfo(apiSubscriber, phone, null, name, sex, null, null, signed, email);
    }

    @Override
    public void getUserInfo() {
        UserInfo userInfo = (UserInfo) ACache.getDefault().getAsObject("user_info");
        if (userInfo != null) {
            mView.setPortrait(userInfo.portrait);
            mView.setName(userInfo.name);
            mView.setSex(userInfo.sex);
            mView.setCity(userInfo.city);
            mView.setEmail(userInfo.email);
            mView.setSigned(userInfo.signed);
        }
    }
}
