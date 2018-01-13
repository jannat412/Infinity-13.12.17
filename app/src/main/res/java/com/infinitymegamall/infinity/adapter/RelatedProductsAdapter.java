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
 * Created by shuvo on 01-Jan-18.
 */

public class RelatedProductsAdapter extends RecyclerView.Adapter<RelatedProductsAdapter.MyViewHolder>{
    private ArrayList<Product_details> product_details;
    Context c;
//    ImageLoader imageLoader = Server_request.getInstance().getImageLoader();


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textViewProductName;
        TextView textViewProductPrice;
        ImageView ImageViewProductImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewProductName = (TextView) itemView.findViewById(R.id.product_name);
            this.textViewProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            this.ImageViewProductImage=(ImageView) itemView.findViewById(R.id.product_image);
        }
    }

    public RelatedProductsAdapter(Context c, ArrayList<Product_details> data) {
        this.c = c;
        this.product_details = data;
    }

    public RelatedProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_products_details, parent, false);

        RelatedProductsAdapter.MyViewHolder myViewHolder = new RelatedProductsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RelatedProductsAdapter.MyViewHolder holder, int position) {

//        if (imageLoader == null)
//            imageLoader = Server_request.getInstance().getImageLoader();

        TextView textViewProductName = holder.textViewProductName;
        TextView textViewProductPrice= holder.textViewProductPrice;
//        ImageView ImageViewProductImage = holder.ImageViewProductImage;

        textViewProductName.setText(product_details.get(position).getProduct_name());
        textViewProductPrice.setText("৳ "+product_details.get(position).getProduct_price());
        //ImageViewProductImage.setImageUrl(product_details.get(position).getProduct_image(), imageLoader);
        Glide
                .with(c)
                .load(product_details.get(position).getProduct_image())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(holder.ImageViewProductImage);
    }
    @Override
    public int getItemCount() {
        return product_details.size();
    }

}
