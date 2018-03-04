package com.example.a14512.discover.modules.main.userself.myroute.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.C;
import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;
import com.example.a14512.discover.modules.main.userself.myroute.view.CommentScenicActivity;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.view.activity.MapActivity;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/9
 */

public class MyRouteAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<MyRoute> mMyRoutes;
    private int type;

    public MyRouteAdapter(Context context) {
        this.mContext = context;
    }

    public void setAdapter(ArrayList<MyRoute> myRoutes, int type) {
        mMyRoutes = new ArrayList<>();
        this.mMyRoutes = myRoutes;
        this.type = type;
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
            String startToEnd = myRoute.getRoute_name();
            String start = startToEnd.substring(0, startToEnd.indexOf("-"));
            String end = startToEnd.substring(startToEnd.indexOf("-") + 1, startToEnd.length());
            ((MyRouteViewHolder) holder).start.setText(start);
            ((MyRouteViewHolder) holder).end.setText(end);
            ((MyRouteViewHolder) holder).pay.setText(String.valueOf(myRoute.getAll_coast()) + "元");
            ((MyRouteViewHolder) holder).path.setText(String.valueOf(myRoute.getAll_distance()) + "km");
            ((MyRouteViewHolder) holder).saveTime.setText(myRoute.getRoute_time());
            ((MyRouteViewHolder) holder).mLayout.setOnClickListener(v -> startActivity(myRoute));
        }
    }

    private void startActivity(MyRoute myRoute) {
        //TODO  跳转后的逻辑
        if (type == 0) {
            //我的路线
            Intent intent = new Intent(mContext, MapActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(C.SCENIC_DETAIL, myRoute.getRouteInformation());
            intent.putExtra(C.SCENIC_DETAIL, bundle);
            mContext.startActivity(intent);
        } else {
            //历史路线
            if (myRoute.isComment) {
                ToastUtil.show(mContext, "你已经评论了哦！");
            } else {
                ArrayList<Scenic> scenics = myRoute.getRouteInformation();
                ArrayList<String> scenicNames = new ArrayList<>();
                for (int i = 1; i < scenics.size() - 1; i++) {
                    scenicNames.add(scenics.get(i).name);
                    myRoute.isComment = true;
                }
                Intent intent = new Intent(mContext, CommentScenicActivity.class);
                intent.putStringArrayListExtra("my_historic_route", scenicNames);
                mContext.startActivity(intent);
            }
        }
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
