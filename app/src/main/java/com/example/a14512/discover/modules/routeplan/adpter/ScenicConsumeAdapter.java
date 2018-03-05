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
import com.example.a14512.discover.modules.routeplan.mode.entity.ConsumeMode;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/5
 */

public class ScenicConsumeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ConsumeMode> mConsumeModes;

    public ScenicConsumeAdapter(ArrayList<ConsumeMode> consumeModes) {
        this.mConsumeModes = consumeModes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_view_consume_frag, parent, false);
        return new ConsumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ConsumeViewHolder) {
            ConsumeMode consumeMode = mConsumeModes.get(position);
            if (consumeMode != null) {
                Glide.with(mContext).load(consumeMode.img).error(R.mipmap.ic_launcher)
                        .into(((ConsumeViewHolder) holder).img);
                ((ConsumeViewHolder) holder).name.setText(consumeMode.name);
                ((ConsumeViewHolder) holder).content.setText(consumeMode.content);
                ((ConsumeViewHolder) holder).category.setText(consumeMode.category);
                ((ConsumeViewHolder) holder).money.setText(String.valueOf(consumeMode.money));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mConsumeModes.size();
    }

    class ConsumeViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, category, content, money;

        public ConsumeViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_consume_photo);
            name = itemView.findViewById(R.id.tv_consume_name);
            content = itemView.findViewById(R.id.tv_consume_content);
            category = itemView.findViewById(R.id.tv_consume_type);
            money = itemView.findViewById(R.id.tv_consume_money);
        }
    }
}
