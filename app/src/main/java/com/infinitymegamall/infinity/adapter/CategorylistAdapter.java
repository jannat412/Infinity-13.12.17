package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.HomeCategory;
import com.infinitymegamall.infinity.model.nv_category;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jannat Mostafiz on 12/6/2017.
 */

public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.MyViewHolder> {
    private ArrayList<HomeCategory> categories;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategory;
        de.hdodenhof.circleimageview.CircleImageView categoryImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCategory = (TextView) itemView.findViewById(R.id.category_name);
            this.categoryImage = (de.hdodenhof.circleimageview.CircleImageView)itemView.findViewById(R.id.category_image);
        }
    }

    public CategorylistAdapter(Context c,ArrayList<HomeCategory> data) {
        this.c = c;
        this.categories = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categotyhome_list, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewCategory = holder.textViewCategory;
        final de.hdodenhof.circleimageview.CircleImageView categoryImage = holder.categoryImage;


        textViewCategory.setText(categories.get(listPosition).getCategoryName());
        categoryImage.setImageResource(categories.get(listPosition).getCategoryImage());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
