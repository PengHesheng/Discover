package com.example.a14512.discover.modules.main.userself.myroute.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;

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
        this.mMyRoutes = myRoutes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_my_route, parent, false);
        return new MyRouteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyRouteViewHolder) {
            MyRoute myRoute = mMyRoutes.get(position);
            ((MyRouteViewHolder) holder).pay.setText(String.valueOf(myRoute.getAll_coast()) + "元");
            ((MyRouteViewHolder) holder).path.setText(String.valueOf(myRoute.getAll_distance()) + "km");
            ((MyRouteViewHolder) holder).saveTime.setText(myRoute.getRoute_time());
            ((MyRouteViewHolder) holder).mLayout.setOnClickListener(v -> startActivity(myRoute));
        }
    }

    private void startActivity(MyRoute myRoute) {

    }

    @Override
    public int getItemCount() {
        return mMyRoutes.size();
    }

    public class MyRouteViewHolder extends RecyclerView.ViewHolder {
        TextView start, end, passby, path, pay, saveTime;
        LinearLayout mLayout;

        public MyRouteViewHolder(View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.tv_my_route_start);
            end = itemView.findViewById(R.id.tv_my_route_end);
            passby = itemView.findViewById(R.id.tv_my_route_pass_by_num);
            path = itemView.findViewById(R.id.tv_my_route_long_path);
            pay = itemView.findViewById(R.id.tv_my_route_pay);
            saveTime = itemView.findViewById(R.id.tv_my_route_save_time);
            mLayout = itemView.findViewById(R.id.my_route_item);
        }
    }
}
