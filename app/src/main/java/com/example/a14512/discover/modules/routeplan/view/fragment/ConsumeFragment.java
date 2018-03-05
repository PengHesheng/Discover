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
import com.example.a14512.discover.modules.routeplan.adpter.ScenicConsumeAdapter;
import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/5
 */

public class ConsumeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<ConsumeMode> mConsumeModes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consume, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.consume_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        initData();
        ScenicConsumeAdapter adapter = new ScenicConsumeAdapter(mConsumeModes);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            ConsumeMode consumeMode = new ConsumeMode();
            consumeMode.name = "潘哥两" + i;
            consumeMode.content = "很好的美食" + i;
            consumeMode.category = "一类， 两类 " + i;
            consumeMode.money = 10 + i;
            mConsumeModes.add(consumeMode);
        }
    }

}