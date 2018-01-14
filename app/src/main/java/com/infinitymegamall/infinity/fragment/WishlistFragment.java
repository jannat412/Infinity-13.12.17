package com.infinitymegamall.infinity.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.WishListAdapter;
import com.infinitymegamall.infinity.model.Cart;
import com.infinitymegamall.infinity.model.Cartproduct;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.infinitymegamall.infinity.R.layout.wishlist_fragment;

/**
 * Created by shuvo on 30-Dec-17.
 */

public class WishlistFragment extends Fragment {

    private RecyclerView wishlistRv;
    private static ArrayList<Cartproduct> wishArraylist;
    private static RecyclerView.Adapter wishlistAdpaater;
    private ProgressBar wish_progressbar;
    private String url ="https://infinitymegamall.com/wp-json/wc/v2/products?include=";
    private String order_url ="https://infinitymegamall.com/wp-json/wc/v2/orders";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    private String productId="",productSize="",productQuantity="";
    ArrayList<Cart> wishlocalArrayList;
    private View v;
    private Gson gsonInstance;
    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;
    private CartFragment cartFragment;


    public WishlistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loaddata();
        if (getArguments() != null) {
            productId = getArguments().getString("productid");
            productSize = getArguments().getString("productsize");
            productQuantity = getArguments().getString("productquantity");
            wishlocalArrayList.add(new Cart(productId,productSize,productQuantity));

            savedata();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wishlist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wish_progressbar = (ProgressBar) getActivity().findViewById(R.id.wishlist_progressbar);
        v = getActivity().findViewById(R.id.home_activity_id);
//*************Wish list
        wishlistRv =(RecyclerView) getActivity().findViewById(R.id.wishlistRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        wishlistRv.setHasFixedSize(true);
        wishlistRv.setLayoutManager(layoutManager1);
        wishArraylist = new ArrayList<>();
        //wishArraylist.add(new Product_details(2,"Jannat","233","https://assets.teleflora.com/images/customhtml/meaning-of-flowers/carnation.png"));
        wishlistAdpaater = new WishListAdapter(getActivity(), wishArraylist);
        wishlistRv.setAdapter(wishlistAdpaater);
        wishlistRv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), wishlistRv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int productId = Integer.parseInt(wishArraylist.get(position).getProductId());
                        Bundle bundle = new Bundle();
                        bundle.putInt("productId",productId);
                        productDetailViewFragment = new ProductDetailViewFragment();
                        productDetailViewFragment.setArguments(bundle);
                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.child_fragment_container, productDetailViewFragment);
                        transaction.addToBackStack("ProductDetailViewFragment");
                        transaction.commit();

                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Add or remove item")
                                .setMessage("press add to cart to add this item to your cart or press delete to remove item" )
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        wishArraylist.remove(position);
                                        wishlocalArrayList.remove(position);
                                        wishlistAdpaater.notifyItemRemoved(position);
                                        wishlistAdpaater.notifyItemRangeChanged(position,wishArraylist.size());
                                        savedata();
                                    }
                                })
                            .setNegativeButton("add to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String id = wishArraylist.get(position).getProductId();
                                String quantity = wishArraylist.get(position).getProductQuantity();
                                String size = wishArraylist.get(position).getProductSize();
                                wishArraylist.remove(position);
                                wishlocalArrayList.remove(position);
                                wishlistAdpaater.notifyItemRemoved(position);
                                wishlistAdpaater.notifyItemRangeChanged(position,wishArraylist.size());
                                savedata();
                                Bundle bundle = new Bundle();
                                bundle.putString("productquantity",quantity);
                                bundle.putString("productsize",size);
                                bundle.putString("productid",id);
                                cartFragment = new CartFragment();
                                cartFragment.setArguments(bundle);
                                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.child_fragment_container, cartFragment);
                                transaction.addToBackStack("ProductDetailViewFragment");
                                transaction.commit();
                            }
                        })
                        .setNeutralButton("X", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.create().show();
                    }
                })
        );
//####################

        makeWishList();

    }



    public void loaddata(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        gsonInstance = new Gson();
        String json = sharedPreferences.getString("wishlocal",null);
        Type type = new TypeToken<ArrayList<Cart>>(){}.getType();
        wishlocalArrayList = gsonInstance.fromJson(json,type);
        if(wishlocalArrayList==null){
            wishlocalArrayList =new ArrayList<>();
        }
    }

    public void savedata(){
        SharedPreferences sharedpref = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedpref.edit();
        gsonInstance = new Gson();
        String json = gsonInstance.toJson(wishlocalArrayList);
        editor.putString("wishlocal",json);
        editor.apply();
    }

    public void makeWishList(){

        String request_url =url;
        if(wishlocalArrayList !=null) {
            for (int i = 0; i < wishlocalArrayList.size(); i++) {
                String id =wishlocalArrayList.get(i).getProductId();
                request_url +=id+",";
            }
            product_details_api_request(request_url);
        }

    }


    public void product_details_api_request(String url){

        wish_progressbar.setVisibility(View.VISIBLE);

        // Creating volley request obj
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Cartproduct model = new Cartproduct();
                                model.setProductId(String.valueOf(obj.getInt("id")));
                                model.setProductName(obj.getString("name"));
                                model.setProductPrie(obj.getString("price"));
                                JSONArray img = obj.getJSONArray("images");
                                JSONObject item = img.getJSONObject(0);
                                String image = item.getString("src");
                                model.setProductImage(image);
                                model.setProductQuantity(wishlocalArrayList.get(i).getProductQuantity());
                                model.setProductSize(wishlocalArrayList.get(i).getProductSize());
                                // adding model to product details  array
                                //Snackbar.make(v,"grid  "+model.getId(),Snackbar.LENGTH_SHORT).show();
                                wishArraylist.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                wish_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        wish_progressbar.setVisibility(View.GONE);
                        wishlistAdpaater.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                wish_progressbar.setVisibility(View.GONE);

            }
        }){

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String credentials = username+":"+ password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);

    }



}































