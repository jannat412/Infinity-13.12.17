package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Cartproduct;

import java.util.ArrayList;

/**
 * Created by shuvo on 03-Jan-18.
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    Context c;
    private ArrayList<Cartproduct> cartArraylist;

    public CartListAdapter(Context c, ArrayList<Cartproduct> cartArraylist) {
        this.c = c;
        this.cartArraylist = cartArraylist;
    }

    @Override
    public CartListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        CartListAdapter.MyViewHolder myViewHolder = new CartListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        
        TextView cartProductName=holder.cartProductName;
        TextView cartProductPrice=holder.cartProductPrice;
        TextView cartProductQuantity=holder.cartProductQuantity;
        TextView cartProductSize=holder.cartProductSize;

        Glide.with(c)
                .load(cartArraylist.get(position).getProductImage())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(holder.cartImage);
        cartProductName.setText(cartArraylist.get(position).getProductName());
        cartProductPrice.setText("price: "+cartArraylist.get(position).getProductPrie());
        cartProductQuantity.setText("quantity: "+cartArraylist.get(position).getProductQuantity());
        cartProductSize.setText("size: "+cartArraylist.get(position).getProductSize());

    }

    @Override
    public int getItemCount() {
        return cartArraylist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        cartArraylist.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Cartproduct item, int position) {
        cartArraylist.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImage;
        TextView cartProductName;
        TextView cartProductPrice;
        TextView cartProductQuantity;
        TextView cartProductSize;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.cartImage = (ImageView) itemView.findViewById(R.id.cart_image);
            this.cartProductName = (TextView) itemView.findViewById(R.id.cart_name);
            this.cartProductPrice = (TextView) itemView.findViewById(R.id.cart_price);
            this.cartProductQuantity = (TextView) itemView.findViewById(R.id.cart_quantity);
            this.cartProductSize = (TextView) itemView.findViewById(R.id.cart_size);
        }
    }
}
