package com.example.a14512.discover.modules.routeplan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseFragment;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;

/**
 * @author 14512 on 2018/3/5
 */

public class IntroduceFragment extends BaseFragment {

    private TextView tvAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvAbout = view.findViewById(R.id.tv_scenic_details_about);
        Scenic scenic = (Scenic) getArguments().getSerializable("scenic");
        assert scenic != null;
        tvAbout.setText(scenic.content);
    }
}
