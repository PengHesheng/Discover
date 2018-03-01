package com.example.a14512.discover.modules.main.userself.myroute.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.a14512.discover.R;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.ScenicScore;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/3/1
 */

public class CommentScenicAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ScenicScore> mScores;

    public CommentScenicAdapter(ArrayList<ScenicScore> scenicScores) {
        this.mScores = scenicScores;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_comment_scenic, parent, false);
        return new ScenicScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScenicScoreViewHolder) {
            ScenicScore score = mScores.get(position);
            ((ScenicScoreViewHolder) holder).scenicName.setText(score.name);
            score.first = ((ScenicScoreViewHolder) holder).mRatingBar1.getNumStars();
            score.second = ((ScenicScoreViewHolder) holder).mRatingBar2.getNumStars();
            score.third = ((ScenicScoreViewHolder) holder).mRatingBar3.getNumStars();
            score.fourth = ((ScenicScoreViewHolder) holder).mRatingBar4.getNumStars();
            score.five = ((ScenicScoreViewHolder) holder).mRatingBar5.getNumStars();
        }
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    class ScenicScoreViewHolder extends RecyclerView.ViewHolder {
        TextView scenicName;
        RatingBar mRatingBar1, mRatingBar2, mRatingBar3, mRatingBar4, mRatingBar5;

        ScenicScoreViewHolder(View itemView) {
            super(itemView);
            scenicName = itemView.findViewById(R.id.tv_scenic_name_comment);
            mRatingBar1 = itemView.findViewById(R.id.rating_bar_1);
            mRatingBar2 = itemView.findViewById(R.id.rating_bar_2);
            mRatingBar3 = itemView.findViewById(R.id.rating_bar_3);
            mRatingBar4 = itemView.findViewById(R.id.rating_bar_4);
            mRatingBar5 = itemView.findViewById(R.id.rating_bar_5);
        }
    }
}
