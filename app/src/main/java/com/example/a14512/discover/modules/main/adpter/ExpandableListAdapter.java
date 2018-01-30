package com.example.a14512.discover.modules.main.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a14512.discover.R;

import java.util.List;
import java.util.Map;

/**
 * @author 14512 on 2018/1/30
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListAdapter";
    private Context mContext;
    private Map<String, List<String>> mMap;
    private List<String> mNodes;

    public ExpandableListAdapter(Context context, Map<String, List<String>> map, List<String> nodes) {
        this.mContext = context;
        this.mMap = map;
        this.mNodes = nodes;
    }


    @Override
    public int getGroupCount() {
        return mNodes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mMap.get(mNodes.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mMap.get(mNodes.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMap.get(mNodes.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_list_parent, null);
            holder = new GroupViewHolder();
            holder.imgExpand = view.findViewById(R.id.img_expand_down);
            holder.imgProgress = view.findViewById(R.id.img_progress_undo);
            holder.tvPlaceName = view.findViewById(R.id.tv_place_name);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        bindParentHolder(groupPosition, holder);
        return view;
    }

    private void bindParentHolder(int groupPosition, GroupViewHolder holder) {
        String str = mNodes.get(groupPosition);
        holder.tvPlaceName.setText(str);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ChildViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_list_child, null);
            holder = new ChildViewHolder();
            holder.imgExpand = view.findViewById(R.id.img_child_expand_down);
            holder.imgProgress = view.findViewById(R.id.img_child_progress_undo);
            holder.tvStepName = view.findViewById(R.id.tv_step);
            holder.tvTravel = view.findViewById(R.id.tv_travel);
            holder.tvDistance = view.findViewById(R.id.tv_distance);
            holder.imgTravelCategory = view.findViewById(R.id.img_travel_category);
            holder.mLayout = view.findViewById(R.id.layout_broadcast);
            holder.tvNextStation = view.findViewById(R.id.tv_next_station);
            holder.tvNearBus = view.findViewById(R.id.tv_near_bus);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        bindChildHolder(groupPosition, childPosition, holder);
        return view;
    }

    private void bindChildHolder(int groupPosition, int childPosition, ChildViewHolder holder) {
        Log.e(TAG, "" + groupPosition + "::" + childPosition);
        if (childPosition == 0) {
            holder.imgExpand.setBackgroundResource(R.mipmap.expand_up);
        }
        String str = (String) getChild(groupPosition, childPosition);
        holder.tvStepName.setText(str);
        holder.tvTravel.setText("步行");
        holder.tvDistance.setText("1.3公里（7分钟）");
        holder.imgTravelCategory.setBackgroundResource(R.mipmap.bus);
        if (str != null) {
            holder.mLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class GroupViewHolder {
        ImageView imgExpand;
        ImageView imgProgress;
        TextView tvPlaceName;
    }

    public static class ChildViewHolder {
        ImageView imgExpand;
        ImageView imgProgress;
        TextView tvStepName;
        TextView tvTravel;
        TextView tvDistance;
        ImageView imgTravelCategory;
        LinearLayout mLayout;
        TextView tvNextStation;
        TextView tvNearBus;
    }
}
