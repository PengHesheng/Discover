package com.example.a14512.discover.modules.main.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.modle.Scenic;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/1/29
 */

public class RoutePlanAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Scenic> mScenics;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener1) {
        this.mOnItemClickListener = onItemClickListener1;
    }

    public RoutePlanAdapter(Context context, ArrayList<Scenic> list) {
        this.mContext = context;
        this.mScenics = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_path_plan_scenic,parent,false);
        return new ScenicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScenicViewHolder) {
            Scenic scenic = mScenics.get(position);
            if (scenic != null) {
                ((ScenicViewHolder) holder).time.setText(scenic.time);
                ((ScenicViewHolder) holder).name.setText(scenic.name);
                ((ScenicViewHolder) holder).monthAver.setText(scenic.monthAver);
                ((ScenicViewHolder) holder).peopleAver.setText(scenic.peopleAver);
                ((ScenicViewHolder) holder).place.setText(scenic.place);
                Glide.with(mContext).load(scenic.img).into(((ScenicViewHolder) holder).img);
            }
            //如果设置了回调，则设置点击事件
            if (mOnItemClickListener != null) {
                ((ScenicViewHolder) holder).delete.setOnClickListener(view -> {
                    mOnItemClickListener.onItemClick(((ScenicViewHolder) holder).delete, position);
                });
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return mScenics.size();
    }

    public class ScenicViewHolder extends RecyclerView.ViewHolder {
        TextView time, name, monthAver, peopleAver, place;
        ImageView img, delete;

        public ScenicViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_recommend_time);
            name = itemView.findViewById(R.id.tv_scenic_name);
            monthAver = itemView.findViewById(R.id.tv_month_aver);
            peopleAver = itemView.findViewById(R.id.tv_people_aver);
            place = itemView.findViewById(R.id.tv_place);
            img = itemView.findViewById(R.id.img_scenic);
            delete = itemView.findViewById(R.id.img_delete);
        }
    }

    public void changeOneData(int position, Scenic scenic) {
        mScenics.remove(position);
        mScenics.add(position, scenic);
        notifyDataSetChanged();
    }


}
