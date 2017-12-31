package com.infinitymegamall.infinity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.infinitymegamall.infinity.R;

/**
 * Created by shuvo on 31-Dec-17.
 */

public class WishListAdapter {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView wishImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.wishImage = (ImageView) itemView.findViewById(R.id.wish_image);

        }
    }
}
