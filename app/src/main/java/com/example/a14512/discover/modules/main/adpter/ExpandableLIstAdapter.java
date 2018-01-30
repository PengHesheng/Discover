package com.example.a14512.discover.modules.main.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author 14512 on 2018/1/30
 */

public class ExpandableLIstAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private Map<String, List<String>> mMap;
    private List<String> mNodes;

    public ExpandableLIstAdapter(Context context, Map<String, List<String>> map, List<String> nodes) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.parent_item, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
