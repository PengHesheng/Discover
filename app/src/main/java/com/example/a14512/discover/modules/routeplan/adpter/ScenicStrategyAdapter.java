package com.example.a14512.discover.modules.routeplan.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.routeplan.mode.entity.StrategyMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/5
 */

public class ScenicStrategyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<StrategyMode> mStrategyModes;

    public ScenicStrategyAdapter(ArrayList<StrategyMode> strategyModes) {
        this.mStrategyModes = strategyModes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_view_strategy_frag, parent, false);
        return new StrategyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StrategyViewHolder) {
            StrategyMode strategyMode = mStrategyModes.get(position);
            if (strategyMode != null) {
                ((StrategyViewHolder) holder).name.setText(strategyMode.name);
                Glide.with(mContext).load(strategyMode.img).error(R.mipmap.ic_launch)
                        .into(((StrategyViewHolder) holder).img);
                ((StrategyViewHolder) holder).score.setText(strategyMode.comment);
                ((StrategyViewHolder) holder).way.setText(strategyMode.way);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStrategyModes.size();
    }

    class StrategyViewHolder extends RecyclerView.ViewHolder {
        TextView name, score, way;
        ImageView img;

        public StrategyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_strategy_name);
            img = itemView.findViewById(R.id.img_strategy_photo);
            score = itemView.findViewById(R.id.tv_strategy_score);
            way = itemView.findViewById(R.id.tv_strategy_way);
        }
    }
}
