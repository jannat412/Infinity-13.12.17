package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.nv_category;

import java.util.ArrayList;

/**
 * Created by shuvo on 24-Dec-17.
 */

public class CategoryPageCategoryItemAdapter extends RecyclerView.Adapter<CategoryPageCategoryItemAdapter.MyViewHolder> {

    private ArrayList<nv_category> nv_categoryArrayList;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategoryItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCategoryItem = (TextView) itemView.findViewById(R.id.category_item_name_categorylist);
        }
    }
    public CategoryPageCategoryItemAdapter(Context c,ArrayList<nv_category> nv_categoryArrayList) {
        this.nv_categoryArrayList = nv_categoryArrayList;
        this.c = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_name, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryPageCategoryItemAdapter.MyViewHolder holder, int listPosition) {

        TextView textViewCategoryname = holder.textViewCategoryItem;

        textViewCategoryname.setText(nv_categoryArrayList.get(listPosition).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return nv_categoryArrayList.size();
    }


}
