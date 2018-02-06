package com.example.a14512.discover.modules.main.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.mode.entity.Scenic;
import com.example.a14512.discover.modules.main.mode.entity.ScenicCommentUser;
import com.example.a14512.discover.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ScenicCommentUser> mUsers = new ArrayList<>();
    private Scenic mScenic;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener1) {
        this.mOnItemClickListener = onItemClickListener1;
    }

    public ScenicCommentAdapter(Context context) {
        this.mContext = context;
    }

    public void setCommentAdapter(ArrayList<ScenicCommentUser> list) {
        this.mUsers = list;
    }

    public void setScenicsAdapter(Scenic scenic) {
        this.mScenic = scenic;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_recycler_view_scenic_details_comment, parent, false);
            return new CommentViewHolder(view);
        } else if (viewType == 0) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_recycler_view_scenic_details, parent, false);
            return new FirstScenicDetailViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            ScenicCommentUser user = mUsers.get(position);
            if (user != null) {
                Glide.with(mContext).load(user.portrait)
                        .error(R.mipmap.ic_launcher).into(((CommentViewHolder) holder).portrait);
                ((CommentViewHolder) holder).name.setText(user.name);
            }
            //如果设置了回调，则设置点击事件
            if (mOnItemClickListener != null) {
                ((CommentViewHolder) holder).zan.setOnClickListener(v ->
                        mOnItemClickListener.onOnePartyClick(((CommentViewHolder) holder).zan, position));
                ((CommentViewHolder) holder).comment.setOnClickListener(v -> ToastUtil.show("Comment"));
            }
        } else if (holder instanceof FirstScenicDetailViewHolder){
            ((FirstScenicDetailViewHolder) holder).scenicName.setText(mScenic.name);
            ((FirstScenicDetailViewHolder) holder).workTime.setText(""+mScenic.time);
            ((FirstScenicDetailViewHolder) holder).about.setText(mScenic.content);
            ((FirstScenicDetailViewHolder) holder).location.setText(mScenic.location);
            ((FirstScenicDetailViewHolder) holder).money.setText("人均"+mScenic.peopleAver);
            ((FirstScenicDetailViewHolder) holder).monthPeople.setText("人气"+mScenic.monthAver);
            if (mOnItemClickListener != null) {
                ((FirstScenicDetailViewHolder) holder).attention.setOnClickListener(v -> {
                    mOnItemClickListener.onOnePartyClick(((FirstScenicDetailViewHolder) holder).attention, position);
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView portrait;
        TextView name;
        ImageView imgComment;
        TextView commentContent;
        LinearLayout zan, comment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            portrait = itemView.findViewById(R.id.img_scenic_details_comment_portrait);
            name = itemView.findViewById(R.id.tv_scenic_details_comment_name);
            imgComment = itemView.findViewById(R.id.img_scenic_details_comment);
            commentContent = itemView.findViewById(R.id.tv_scenic_details_comment_content);
            zan = itemView.findViewById(R.id.layout_scenic_details_comment_zan);
            comment = itemView.findViewById(R.id.layout_scenic_details_comment_img);
        }
    }

    public class FirstScenicDetailViewHolder extends RecyclerView.ViewHolder {
        TextView scenicName, monthPeople, money, location, workTime, about;
        LinearLayout attention;

        public FirstScenicDetailViewHolder(View itemView) {
            super(itemView);
            scenicName = itemView.findViewById(R.id.tv_details_scenic_name);
            monthPeople = itemView.findViewById(R.id.tv_scenic_details_people);
            money = itemView.findViewById(R.id.tv_scenic_details_money);
            attention = itemView.findViewById(R.id.layout_details_attention);
            location = itemView.findViewById(R.id.tv_scenic_details_location);
            workTime = itemView.findViewById(R.id.tv_scenic_details_time);
            about = itemView.findViewById(R.id.tv_scenic_details_about);
        }
    }
}
