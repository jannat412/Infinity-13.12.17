package com.infinitymegamall.infinity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Exclusive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class ExclusivelistAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
//    ImageLoader imageLoader = Server_request.getInstance().getImageLoader();
    private List<Exclusive> exclusives;

    public ExclusivelistAdapter( Activity activity,List<Exclusive> exclusive) {
        this.activity = activity;
        this.exclusives = exclusive;
    }
    @Override
    public int getCount() {
        return exclusives.size();
    }
    @Override
    public Object getItem(int position) {
        return exclusives.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.exclusive_list, null);

//        if (imageLoader == null)
//            imageLoader = Server_request.getInstance().getImageLoader();

            TextView exclusiveText= (TextView) convertView.findViewById(R.id.exclusive_text);
            ImageView exclusiveImage = (ImageView) convertView.findViewById(R.id.exclusive_image);

            Exclusive s=exclusives.get(position);

            exclusiveText.setText(s.getExclusiveText());

            //exclusiveImage.setImageUrl(s.getImage(), imageLoader);
        Glide
                .with(activity)
                .load(s.getImage())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(exclusiveImage);

        return convertView;
    }

}

