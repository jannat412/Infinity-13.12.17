package com.infinitymegamall.infinity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Exclusive;
import com.infinitymegamall.infinity.model.Product_details;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuvo on 25-Dec-17.
 */

public class Category_frag_grid_adapter extends RecyclerView.Adapter<Category_frag_grid_adapter.MyViewHolder> {
    private Context c;
    //ImageLoader imageLoader = Server_request.getInstance().getImageLoader();
    private ArrayList<Product_details> productDetailsList;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView price_tv;
        ImageView image_niv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name_tv = (TextView)itemView.findViewById(R.id.product_name);
            this.price_tv = (TextView) itemView.findViewById(R.id.product_price);
            this.image_niv = (ImageView) itemView.findViewById(R.id.product_image);
        }
    }

    public Category_frag_grid_adapter(Context c, ArrayList<Product_details> productDetailsList) {
        this.c = c;
        this.productDetailsList = productDetailsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_products_details, parent, false);

        Category_frag_grid_adapter.MyViewHolder myViewHolder = new Category_frag_grid_adapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        if (imageLoader == null)
//            imageLoader = Server_request.getInstance().getImageLoader();

        TextView name_tvbh = holder.name_tv;
        TextView price_tvbh = holder.price_tv;
//        ImageView image_nivbh = holder.image_niv;

        name_tvbh.setText(productDetailsList.get(position).getProduct_name());
        price_tvbh.setText("৳ "+productDetailsList.get(position).getProduct_price());
        //image_nivbh.setImageUrl(productDetailsList.get(position).getProduct_image(),imageLoader);

        Glide
                .with(c)
                .load(productDetailsList.get(position).getProduct_image())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(holder.image_niv);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productDetailsList.size();
    }

}
