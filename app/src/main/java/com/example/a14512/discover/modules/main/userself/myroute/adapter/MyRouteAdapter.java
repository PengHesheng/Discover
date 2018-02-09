package com.example.a14512.discover.modules.main.userself.myroute.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.userself.myroute.mode.MyRoute;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/9
 */

public class MyRouteAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<MyRoute> mMyRoutes;

    public MyRouteAdapter(Context context) {
        this.mContext = context;
    }

    public void setAdapter(ArrayList<MyRoute> myRoutes) {
        mMyRoutes = new ArrayList<>();
        this.mMyRoutes = myRoutes;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_mt_route, parent, false);
        return new MyRouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyRouteViewHolder) {
            MyRoute myRoute = mMyRoutes.get(position);

        }
    }

    @Override
    public int getItemCount() {
        return mMyRoutes.size();
    }

    public class MyRouteViewHolder extends RecyclerView.ViewHolder {
        TextView start, end, passby, path, pay, saveTime;

        public MyRouteViewHolder(View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.tv_my_route_start);
            end = itemView.findViewById(R.id.tv_my_route_end);
            passby = itemView.findViewById(R.id.tv_my_route_pass_by_num);
            path = itemView.findViewById(R.id.tv_my_route_long_path);
            pay = itemView.findViewById(R.id.tv_my_route_pay);
            saveTime = itemView.findViewById(R.id.tv_my_route_save_time);
        }
    }
}
