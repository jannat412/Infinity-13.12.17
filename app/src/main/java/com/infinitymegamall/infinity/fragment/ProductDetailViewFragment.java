package com.infinitymegamall.infinity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.infinitymegamall.infinity.MyCategory;
import com.infinitymegamall.infinity.MyGallery;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.CategorylistAdapter;
import com.infinitymegamall.infinity.adapter.GalleryImageAdapter;
import com.infinitymegamall.infinity.model.Gallery;
import com.infinitymegamall.infinity.model.HomeCategory;

import java.util.ArrayList;


public class ProductDetailViewFragment extends Fragment {
    private RecyclerView galler_list;
    private static ArrayList<Gallery> galleries;
    private static RecyclerView.Adapter galleryAdapter;
    private ImageView galleryView;

    private TextView product_name, product_price, addtocart;
    private EditText quantity;
    private ImageView up, down;
    int quantities = 0;

    private View v;

    public ProductDetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail_view, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getActivity().findViewById(R.id.home_activity_id);
        galleryView = (ImageView)getActivity().findViewById(R.id.galleryView);
        galler_list = (RecyclerView) getActivity().findViewById(R.id.galler_list);

        up = (ImageView) getActivity().findViewById(R.id.up);
        down = (ImageView) getActivity().findViewById(R.id.down);
        quantity = (EditText) getActivity().findViewById(R.id.quantity);
        quantity.setText(String.valueOf(quantities));
        product_name = (TextView) getActivity().findViewById(R.id.product_name);
        product_price = (TextView) getActivity().findViewById(R.id.product_price);
        addtocart = (TextView) getActivity().findViewById(R.id.addtocart);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantities++;
                quantity.setText(Integer.toString(quantities));
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantities--;
                quantity.setText(Integer.toString(quantities));
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        galler_list.setHasFixedSize(true);
        galler_list.setLayoutManager(layoutManager);

        galleries = new ArrayList<Gallery>();
        for (int i = 0; i < MyGallery.image.length; i++) {
            galleries.add(new Gallery(
                    MyGallery.image[i],
                    MyGallery.id[i]
            ));
        }


        galleryAdapter = new GalleryImageAdapter(getActivity(), galleries);
        galler_list.setAdapter(galleryAdapter);
        galler_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), galler_list ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Snackbar.make(v,galleries.get(position).getGalleryImage(),Snackbar.LENGTH_LONG).show();
                        galleryView.setImageResource(galleries.get(position).getGalleryImage());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

}

