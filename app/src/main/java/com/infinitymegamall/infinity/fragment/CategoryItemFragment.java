package com.infinitymegamall.infinity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.adapter.CategoryPageCategoryItemAdapter;
import com.infinitymegamall.infinity.model.nv_category;

import java.util.ArrayList;


public class CategoryItemFragment extends Fragment {

    private RecyclerView category_item_list;
    private static ArrayList<nv_category> category_arraylist;
    private static RecyclerView.Adapter category_adapter;

    String url ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=10&min_price=200";//?after=2017-02-19T16:39:57-08:00";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    int category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getInt("category");
            String b = Integer.toString(category);
            Toast.makeText(getActivity(),b,
                    Toast.LENGTH_LONG).show();

        }
    }

    public CategoryItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        category_item_list = (RecyclerView) getActivity().findViewById(R.id.category_item_List);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        category_item_list.setHasFixedSize(true);
        category_item_list.setLayoutManager(layoutManager1);

        category_arraylist = new ArrayList<>();
        category_arraylist.add(new nv_category("shuvo prosad", 0));

        category_adapter = new CategoryPageCategoryItemAdapter(getActivity(), category_arraylist);
        category_item_list.setAdapter(category_adapter);
    }


}
