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
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class Category_drawer_adapter extends ExpandableRecyclerViewAdapter<ParentCategoryVH,ChildCategoryVH> {


    public Category_drawer_adapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ParentCategoryVH onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_parent,parent,false);
        return new ParentCategoryVH(v);
    }

    @Override
    public ChildCategoryVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_child,parent,false);
        return new ChildCategoryVH(v);
    }

    @Override
    public void onBindChildViewHolder(ChildCategoryVH holder, int flatPosition, ExpandableGroup group, int childIndex) {
        ChildCategory cg = (ChildCategory) group.getItems().get(childIndex);
        holder.setTitle(cg.getCategory());
    }

    @Override
    public void onBindGroupViewHolder(ParentCategoryVH holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group.getTitle());
    }

}
