package com.example.a14512.discover.modules.routeplan.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/1
 */

public class ScenicCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ScenicCommentUser> mUsers;

    public ScenicCommentAdapter(Context context) {
        this.mContext = context;
    }

    public void setCommentAdapter(ArrayList<ScenicCommentUser> users) {
        this.mUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_recycler_view_scenic_details_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            ScenicCommentUser user = mUsers.get(position);
            if (user != null) {
                Glide.with(mContext).load(user.portrait)
                        .error(R.mipmap.default_portrait).into(((CommentViewHolder) holder).portrait);
                ((CommentViewHolder) holder).name.setText(user.name);
                if (user.star != null) {
                    ((CommentViewHolder) holder).mRatingBar.setRating(Integer.valueOf(user.star));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView portrait;
        TextView name;
        RatingBar mRatingBar;
        LinearLayout zan;

        public CommentViewHolder(View itemView) {
            super(itemView);
            portrait = itemView.findViewById(R.id.img_scenic_details_comment_portrait);
            name = itemView.findViewById(R.id.tv_scenic_details_comment_name);
            mRatingBar = itemView.findViewById(R.id.rating_bar_scenic_details_comment);
//            zan = itemView.findViewById(R.id.layout_scenic_details_comment_zan);
        }
    }
}
