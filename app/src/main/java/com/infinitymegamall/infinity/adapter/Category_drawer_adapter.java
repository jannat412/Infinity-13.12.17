package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.ViewHolders.ChildCategoryVH;
import com.infinitymegamall.infinity.ViewHolders.ParentCategoryVH;
import com.infinitymegamall.infinity.model.ChildCategory;
import com.infinitymegamall.infinity.model.ParentCategory;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class Category_drawer_adapter extends CheckableChildRecyclerViewAdapter<ParentCategoryVH,ChildCategoryVH> {


    public Category_drawer_adapter(List<ParentCategory> groups) {
        super(groups);
    }

    @Override
    public ParentCategoryVH onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent,parent,false);
        return new ParentCategoryVH(v);
    }

    @Override
    public void onBindGroupViewHolder(ParentCategoryVH holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group.getTitle());
    }

    @Override
    public ChildCategoryVH onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_child,parent,false);
        return new ChildCategoryVH(v);
    }

    @Override
    public void onBindCheckChildViewHolder(ChildCategoryVH holder, int flatPosition, CheckedExpandableGroup group, int childIndex) {
        final ChildCategory cg = (ChildCategory) group.getItems().get(childIndex);
        holder.setTitle(cg.getCategory());
    }
}
