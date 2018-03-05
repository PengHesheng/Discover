package com.example.a14512.discover.modules.routeplan.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a14512.discover.R;
import com.example.a14512.discover.base.BaseFragment;

/**
 * @author 14512 on 2018/3/5
 */

public class TicketFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket, null);
        return view;
    }

}
