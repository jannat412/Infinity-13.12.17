package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Product_details;

import java.util.ArrayList;

/**
 * Created by shuvo on 31-Dec-17.
 */

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder>{

    Context c;
    private ArrayList<Product_details> wishArrayList;

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist,parent,false);
        WishListAdapter.MyViewHolder myViewHolder = new WishListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ImageView wishImage = holder.wishImage;
        TextView wishProductName = holder.wishProductName;
        TextView wishProductPrice = holder.wishProductPrice;

        Glide.with(c)
             .load(wishArrayList.get(position).getProduct_image())
             .centerCrop()
             .placeholder(R.drawable.loading)
             .crossFade()
             .into(holder.wishImage);
        wishProductName.setText(wishArrayList.get(position).getProduct_name());
        wishProductPrice.setText(wishArrayList.get(position).getProduct_price());
    }

    @Override
    public int getItemCount() {
        return wishArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView wishImage;
        TextView wishProductName;
        TextView wishProductPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.wishImage = (ImageView) itemView.findViewById(R.id.wish_image);
            this.wishProductName = (TextView) itemView.findViewById(R.id.wish_name);
            this.wishProductPrice = (TextView) itemView.findViewById(R.id.wish_price);
        }
    }

    public WishListAdapter(Context c, ArrayList<Product_details> wishArrayList) {
        this.c = c;
        this.wishArrayList = wishArrayList;
    }
}
