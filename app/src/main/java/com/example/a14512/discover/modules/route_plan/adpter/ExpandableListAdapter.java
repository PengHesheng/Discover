package com.example.a14512.discover.modules.route_plan.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;
import com.example.a14512.discover.R;
import com.example.a14512.discover.utils.DistanceUtil;
import com.example.a14512.discover.utils.Time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;

/**
 * @author 14512 on 2018/1/30
 */

public class ExpandableListAdapter<T extends RouteStep> extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListAdapter";
    private Context mContext;
    private Map<String, List<T>> mMap = new HashMap<>();
    private List<String> mNodes = new ArrayList<>();

    public ExpandableListAdapter(Context context, Map<String, List<T>> map, List<String> nodes) {
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
        if (childPosition == 0) {
            holder.imgExpand.setBackgroundResource(R.mipmap.expand_up);
        }

        TransitStep step = (TransitStep) getChild(groupPosition, childPosition);
        holder.tvStepName.setText(step.getName());
//        holder.tvTravel.setText(step.getVehicleInfo().getTitle());

        vehicleType(holder, step.getStepType(), step);
//        if (str != null) {
//            holder.mLayout.setVisibility(View.VISIBLE);
//        }
    }

    @SuppressLint("SetTextI18n")
    private void vehicleType(ChildViewHolder holder, TransitRouteStepType type, TransitStep step) {
        switch (type) {
            case BUSLINE:
                holder.tvDistance.setText(String.valueOf(step.getVehicleInfo().getPassStationNum())
                        + "站(" + Time.tranceSecondToTime(step.getDuration()) + ")");
                holder.tvTravel.setText(step.getVehicleInfo().getTitle());
                holder.imgTravelCategory.setBackgroundResource(R.mipmap.bus);
                break;
            case SUBWAY:
                holder.tvDistance.setText(String.valueOf(step.getVehicleInfo().getPassStationNum())
                        + "站(" + Time.tranceSecondToTime(step.getDuration()) + ")");
                holder.tvTravel.setText(step.getVehicleInfo().getTitle());
                holder.imgTravelCategory.setBackgroundResource(R.mipmap.subway);
                break;
            case WAKLING:
                holder.tvDistance.setText(DistanceUtil.transformMtoKM(step.getDistance()) +
                        Time.tranceSecondToTime(step.getDuration()));
                holder.tvTravel.setText("步行");
                holder.imgTravelCategory.setBackgroundResource(R.mipmap.walk);
                break;
            default:
                break;
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
