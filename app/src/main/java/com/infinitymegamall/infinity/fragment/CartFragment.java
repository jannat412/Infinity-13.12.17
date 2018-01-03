package com.infinitymegamall.infinity.fragment;

/**
 * Created by shuvo on 03-Jan-18.
 */
import android.support.annotation.NonNull;
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
import com.infinitymegamall.infinity.adapter.CartListAdapter;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.WishListAdapter;
import com.infinitymegamall.infinity.model.Cart;
import com.infinitymegamall.infinity.model.Cartproduct;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;

import java.util.ArrayList;

public class CartFragment extends Fragment{

    private RecyclerView cartRV;
    private static ArrayList<Cartproduct> cartproductArrayList;
    private static RecyclerView.Adapter cartListAdapter;

    private String productId="",productSize="",productQuantity="";
    ArrayList<Cartproduct> cartArrayList ;
    private View v;
    public CartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString("productid");
            productSize = getArguments().getString("productsize");
            productQuantity = getArguments().getString("productquantity");

        }
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getActivity().findViewById(R.id.home_activity_id);
        cartRV =(RecyclerView) getActivity().findViewById(R.id.cartRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cartRV.setHasFixedSize(true);
        cartRV.setLayoutManager(layoutManager1);
        cartArrayList = new ArrayList<>();
        cartArrayList.add(new Cartproduct("2","Jannat","https://assets.teleflora.com/images/customhtml/meaning-of-flowers/carnation.png",
                "2","",""));
        cartListAdapter = new CartListAdapter(getActivity(), cartArrayList);
        cartRV.setAdapter(cartListAdapter);
        cartRV.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), cartRV ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(), cartArrayList.get(position).getProductName(), Toast.LENGTH_SHORT).show();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}
