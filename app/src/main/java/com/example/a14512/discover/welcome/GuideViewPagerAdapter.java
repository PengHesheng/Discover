package com.example.a14512.discover.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * @author 14512 on 2018/2/2
 */

public class GuideViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> mViews;

    public GuideViewPagerAdapter(Context context, List<View> views) {
        this.mContext = context;
        this.mViews = views;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(mViews.get(position));
        return mViews.get(position);
    }


    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
