package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Product_details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuvo on 19-Dec-17.
 */

public class Product_details_adapter extends RecyclerView.Adapter<Product_details_adapter.MyViewHolder>  {

    private ArrayList<Product_details> product_details;
    Context c;
    ImageLoader imageLoader = Server_request.getInstance().getImageLoader();


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textViewProductName;
        TextView textViewProductPrice;
        NetworkImageView networkImageViewProductImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewProductName = (TextView) itemView.findViewById(R.id.product_name);
            this.textViewProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            this.networkImageViewProductImage=(NetworkImageView) itemView.findViewById(R.id.product_image);
        }
    }

    public Product_details_adapter(Context c, ArrayList<Product_details> data) {
        this.c = c;
        this.product_details = data;
    }

    public Product_details_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_arrival_details, parent, false);

        Product_details_adapter.MyViewHolder myViewHolder = new Product_details_adapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (imageLoader == null)
            imageLoader = Server_request.getInstance().getImageLoader();

        TextView textViewProductName = holder.textViewProductName;
        TextView textViewProductPrice= holder.textViewProductPrice;
        NetworkImageView networkImageViewProductImage = holder.networkImageViewProductImage;

        textViewProductName.setText(product_details.get(position).getProduct_name());
        textViewProductPrice.setText(product_details.get(position).getProduct_price());
        networkImageViewProductImage.setImageUrl(product_details.get(position).getProduct_image(), imageLoader);
    }
    @Override
    public int getItemCount() {
        return product_details.size();
    }

}
