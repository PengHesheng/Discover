package com.example.a14512.discover.modules.main.presenter;

import com.example.a14512.discover.modules.main.modle.ModelImp;
import com.example.a14512.discover.modules.main.modle.entity.ScenicCommentUser;
import com.example.a14512.discover.modules.main.presenter.imp.IScenicPresenter;
import com.example.a14512.discover.modules.main.view.imp.IScenicView;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicPresenterImp implements IScenicPresenter {
    private ModelImp mModelImp;
    private IScenicView mView;
    private ArrayList<ScenicCommentUser> mUsers = new ArrayList<>();

    public ScenicPresenterImp(IScenicView view) {
        this.mView = view;
        mModelImp = new ModelImp();
    }

    @Override
    public void getData() {
        initData();
        mView.setAdapter(mUsers);
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            ScenicCommentUser user = new ScenicCommentUser();
            user.content = "那就哦啊达芙妮卡就能看空间的空间啊师傅帮你，"+i+"大连放开你你，都发生弄。";
            user.name = "One user" + i;
            mUsers.add(user);
        }
    }
}
