package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.ChildCategory;
import com.infinitymegamall.infinity.model.ParentCategory;

import java.util.ArrayList;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class Category_drawer_adapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ParentCategory> deptList;

    public Category_drawer_adapter(Context context, ArrayList<ParentCategory> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildCategory> productList = deptList.get(groupPosition).getList();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildCategory> productList = deptList.get(groupPosition).getList();
        return productList.get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentCategory headerInfo = (ParentCategory) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_item_parent, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.cat_list_parent);
        heading.setText(headerInfo.getName().trim());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildCategory detailInfo = (ChildCategory) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child, null);
        }

        TextView sequence = (TextView) convertView.findViewById(R.id.cat_list_child);
        sequence.setText(detailInfo.getCategory());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
