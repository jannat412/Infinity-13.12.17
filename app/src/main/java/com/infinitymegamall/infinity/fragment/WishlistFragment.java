package com.infinitymegamall.infinity.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.WishListAdapter;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;

import java.util.ArrayList;

import static com.infinitymegamall.infinity.R.layout.wishlist_fragment;

/**
 * Created by shuvo on 30-Dec-17.
 */

public class WishlistFragment extends Fragment {

    private RecyclerView wishlistRv;
    private static ArrayList<Product_details> wishArraylist;
    private static RecyclerView.Adapter wishlistAdpaater;

    public WishlistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wishlist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//*************Wish list
        wishlistRv =(RecyclerView) getActivity().findViewById(R.id.wishlistRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        wishlistRv.setHasFixedSize(true);
        wishlistRv.setLayoutManager(layoutManager1);
        wishArraylist = new ArrayList<>();
        wishArraylist.add(new Product_details(2,"Jannat","233","https://assets.teleflora.com/images/customhtml/meaning-of-flowers/carnation.png"));
        wishlistAdpaater = new WishListAdapter(getActivity(), wishArraylist);
        wishlistRv.setAdapter(wishlistAdpaater);
        wishlistRv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), wishlistRv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), wishArraylist.get(position).getProduct_name(), Toast.LENGTH_SHORT).show();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
//####################

    }
}































