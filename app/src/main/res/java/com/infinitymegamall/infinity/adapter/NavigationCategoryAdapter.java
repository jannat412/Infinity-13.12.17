package com.infinitymegamall.infinity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.nv_category;

import java.util.ArrayList;

/**
 * Created by Jannat Mostafiz on 12/5/2017.
 */

public class NavigationCategoryAdapter extends BaseAdapter {
    Context c;
    private ArrayList<NewArrival> categories;

    public NavigationCategoryAdapter(Context c, ArrayList<NewArrival> navigationCategory) {
        this.c = c;
        this.categories = navigationCategory;
    }
    @Override
    public int getCount() {
        return categories.size();
    }
    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.navigation_list,parent,false);
        }
        TextView categoryName= (TextView) convertView.findViewById(R.id.navigationCategorylist);

        NewArrival s= (NewArrival) this.getItem(position);
        categoryName.setText(s.getNewArrival());
        return convertView;
    }

}
