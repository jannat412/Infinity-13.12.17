package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.HomeCategory;
import com.infinitymegamall.infinity.model.NewArrival;

import java.util.ArrayList;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.MyViewHolder> {
    private ArrayList<NewArrival> newArrivals;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategoryNewarrival;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCategoryNewarrival = (TextView) itemView.findViewById(R.id.newarrivalCategorylist);
        }
    }

    public NewArrivalAdapter(Context c, ArrayList<NewArrival> data) {
        this.c = c;
        this.newArrivals = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newarrival, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewArrivalAdapter.MyViewHolder holder, final int listPosition) {

        TextView textViewCategoryNewarrival = holder.textViewCategoryNewarrival;

        textViewCategoryNewarrival.setText(newArrivals.get(listPosition).getNewArrival());

    }

    @Override
    public int getItemCount() {
        return newArrivals.size();
    }
}
