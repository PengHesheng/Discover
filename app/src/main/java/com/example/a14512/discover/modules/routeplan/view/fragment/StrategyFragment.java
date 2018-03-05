package com.example.a14512.discover.modules.routeplan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.routeplan.adpter.ScenicStrategyAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/5
 */

public class StrategyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<StrategyMode> mStrategyModes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.strategy_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        Scenic scenic = (Scenic) getArguments().getSerializable("scenic");
        assert scenic != null;

        initData();
        ScenicStrategyAdapter adapter = new ScenicStrategyAdapter(mStrategyModes);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            StrategyMode strategyMode = new StrategyMode();
            strategyMode.name = "潘哥两" + i;
            strategyMode.comment = "味道很不错" + i;
            strategyMode.way = "乘坐轻轨1" + i + "分钟到达";
            mStrategyModes.add(strategyMode);
        }
    }

}
