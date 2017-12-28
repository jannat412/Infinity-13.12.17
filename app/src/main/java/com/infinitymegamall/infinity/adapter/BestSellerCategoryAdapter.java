package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.NewArrival;

import java.util.ArrayList;

/**
 * Created by shuvo on 28-Dec-17.
 */

public class BestSellerCategoryAdapter extends RecyclerView.Adapter<BestSellerCategoryAdapter.MyViewHolder>{
    private ArrayList<NewArrival> newArrivals;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategoryNewarrival;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewCategoryNewarrival = (TextView) itemView.findViewById(R.id.newarrivalCategorylist);
        }
    }

    public BestSellerCategoryAdapter(Context c, ArrayList<NewArrival> data) {
        this.c = c;
        this.newArrivals = data;
    }

    @Override
    public BestSellerCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newarrival, parent, false);

        BestSellerCategoryAdapter.MyViewHolder myViewHolder = new BestSellerCategoryAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final BestSellerCategoryAdapter.MyViewHolder holder, final int listPosition) {

        TextView textViewCategoryNewarrival = holder.textViewCategoryNewarrival;

        textViewCategoryNewarrival.setText(newArrivals.get(listPosition).getNewArrival());

    }

    @Override
    public int getItemCount() {
        return newArrivals.size();
    }
}
