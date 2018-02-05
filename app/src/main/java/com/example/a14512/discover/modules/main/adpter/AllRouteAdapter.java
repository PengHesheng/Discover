package com.example.a14512.discover.modules.main.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/4
 */

public class AllRouteAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<String> mNames;
    private ArrayList<Integer> mTimes;
    private int mLocation;

    public AllRouteAdapter(Context context) {
        this.mContext = context;
    }

    public void setAdapterData(ArrayList<String> names, int location, ArrayList<Integer> distances) {
        this.mNames = names;
        this.mLocation = location;
        this.mTimes = distances;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view_all_route, parent, false);
        return new AllRouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AllRouteViewHolder) {
            ((AllRouteViewHolder) holder).num.setText(String.valueOf(position + 1));
            ((AllRouteViewHolder) holder).name.setText(mNames.get(position));
            if (position < mTimes.size()) {
                setProgress(((AllRouteViewHolder) holder).progress, mTimes.get(position));
            }
            if (position == mLocation) {
                ((AllRouteViewHolder) holder).location.setVisibility(View.VISIBLE);
                ((AllRouteViewHolder) holder).num.setTextColor(mContext.getResources().getColor(R.color.allRoute));
                ((AllRouteViewHolder) holder).num.setTextSize(18);
            }
        }
    }

    private void setProgress(ImageView progress, Integer dis) {
        progress.setBackgroundColor(mContext.getResources().getColor(R.color.allRoute));
        ViewGroup.LayoutParams params = progress.getLayoutParams();
        //TODO 百分比有待优化
        params.width =  dis / 60;
        progress.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class AllRouteViewHolder extends RecyclerView.ViewHolder {
        ImageView location, progress;
        TextView num, name;

        public AllRouteViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.img_all_route_location);
            progress = itemView.findViewById(R.id.img_all_route_progress);
            num = itemView.findViewById(R.id.tv_all_route_num);
            name = itemView.findViewById(R.id.tv_all_route_name);
        }
    }

}
