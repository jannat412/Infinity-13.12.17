package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Gallery;
import com.infinitymegamall.infinity.model.NewArrival;

import java.util.ArrayList;

/**
 * Created by Jannat Mostafiz on 12/27/2017.
 */

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.MyViewHolder> {
    private ArrayList<Gallery> galleries;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.galleryImage = (ImageView) itemView.findViewById(R.id.gallery);
        }
    }

    public GalleryImageAdapter(Context c, ArrayList<Gallery> data) {
        this.c = c;
        this.galleries = data;
    }

    @Override
    public GalleryImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list, parent, false);

        GalleryImageAdapter.MyViewHolder myViewHolder = new GalleryImageAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryImageAdapter.MyViewHolder holder, final int listPosition) {

        ImageView galleryImage  = holder.galleryImage;

        galleryImage.setImageResource(galleries.get(listPosition).getGalleryImage());

    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }


}