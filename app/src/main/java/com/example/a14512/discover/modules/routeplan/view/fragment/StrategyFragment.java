package com.example.a14512.discover.modules.routeplan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseFragment;
import com.example.a14512.discover.modules.routeplan.adpter.ScenicStrategyAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/5
 */

public class StrategyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.strategy_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (getArguments() != null) {
            ArrayList<StrategyMode> strategyModes = (ArrayList<StrategyMode>) getArguments()
                    .getSerializable("strategy");
            assert strategyModes != null;
            ScenicStrategyAdapter adapter = new ScenicStrategyAdapter(strategyModes);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}
