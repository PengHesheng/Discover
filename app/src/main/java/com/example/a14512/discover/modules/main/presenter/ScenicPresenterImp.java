package com.example.a14512.discover.modules.main.presenter;

import com.example.a14512.discover.modules.main.mode.ModeImp;
import com.example.a14512.discover.modules.main.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.main.presenter.imp.IScenicPresenter;
import com.example.a14512.discover.modules.main.view.imp.IScenicView;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicPresenterImp implements IScenicPresenter {
    private ModeImp mModeImp;
    private IScenicView mView;
    private ArrayList<ScenicCommentUser> mUsers = new ArrayList<>();

    public ScenicPresenterImp(IScenicView view) {
        this.mView = view;
        mModeImp = new ModeImp();
    }

    @Override
    public void getData() {

        initData();
        mView.setAdapter(mUsers);
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            ScenicCommentUser user = new ScenicCommentUser();
            user.name = "One user" + i;
            mUsers.add(user);
        }
    }
}
