package com.infinitymegamall.infinity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Exclusive;
import com.infinitymegamall.infinity.model.Product_details;

import java.util.List;

/**
 * Created by shuvo on 25-Dec-17.
 */

public class Category_frag_grid_adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    ImageLoader imageLoader = Server_request.getInstance().getImageLoader();
    private List<Product_details> productDetailsList;

    public Category_frag_grid_adapter(Activity activity, List<Product_details> productDetailsList) {
        this.activity = activity;
        this.productDetailsList = productDetailsList;
    }

    @Override
    public int getCount() {
        return productDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.new_products_details, null);

        if (imageLoader == null)
            imageLoader = Server_request.getInstance().getImageLoader();

        TextView name_tv= (TextView) convertView.findViewById(R.id.product_name);
        TextView price_tv= (TextView) convertView.findViewById(R.id.product_price);
        NetworkImageView image_niv = (NetworkImageView) convertView.findViewById(R.id.product_image);

        Product_details s=productDetailsList.get(position);

        name_tv.setText(s.getProduct_name());
        price_tv.setText(s.getProduct_price());
        image_niv.setImageUrl(s.getProduct_image(), imageLoader);

        return convertView;
    }
}
