package com.example.a14512.discover.modules.main.userself.changeuser.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a14512.discover.R;

import java.util.List;

/**
 *
 * @author 14512
 * @date 2017/8/3
 */

public class RecyclerAdapter extends RecyclerView.Adapter {
    private List<String> listData;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setListData(List<String> dataList) {
        listData = dataList;
    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_choose, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListItemViewHolder) {
            ((ListItemViewHolder) holder).chooseItem.setText(listData.get(position));
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                    ((ListItemViewHolder) holder).chooseImage.setImageResource(R.mipmap.choose_select);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView chooseItem;
        ImageView chooseImage;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            chooseItem = itemView.findViewById(R.id.chooseItem);
            chooseImage = itemView.findViewById(R.id.chooseImage);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
