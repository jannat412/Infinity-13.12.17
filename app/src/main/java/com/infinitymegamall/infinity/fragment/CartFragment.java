package com.infinitymegamall.infinity.fragment;

/**
 * Created by shuvo on 03-Jan-18.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.infinitymegamall.infinity.Billing;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.pojo.Shipping;
import com.infinitymegamall.infinity.adapter.CartListAdapter;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.WishListAdapter;
import com.infinitymegamall.infinity.model.Cart;
import com.infinitymegamall.infinity.model.Cartproduct;
import com.infinitymegamall.infinity.model.Gallery;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;
import com.infinitymegamall.infinity.model.UserProfile;
import com.infinitymegamall.infinity.pojo.LineItem;
import com.infinitymegamall.infinity.pojo.Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class CartFragment extends Fragment{

    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;
    private RecyclerView cartRV;
    private static RecyclerView.Adapter cartListAdapter;

    public FragmentTransaction fragmentTransaction;
    public FragmentManager fragmentManager;
    private UserProfileFragment userProfileFragment;

    private ProgressBar cart_progressbar;
    private String url ="https://infinitymegamall.com/wp-json/wc/v2/products?include=";
    private String order_url ="https://infinitymegamall.com/wp-json/wc/v2/orders";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    private String productId="",productSize="",productQuantity="";
    ArrayList<Cartproduct> cartArrayList ;
    ArrayList<Cart> cartlocalArrayList;
    private View v;
    private Gson gsonInstance;
    private Button buy_now;
    private UserProfile user;
    TextView result ;

    public CartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loaddata();
        if (getArguments() != null) {
            productId = getArguments().getString("productid");
            productSize = getArguments().getString("productsize");
            productQuantity = getArguments().getString("productquantity");
            cartlocalArrayList.add(new Cart(productId,productSize,productQuantity));

            savedata();
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
        cart_progressbar = (ProgressBar) getActivity().findViewById(R.id.cart_progressbar);
        //result = (TextView) getActivity().findViewById(R.id.textView3);
        buy_now =(Button) getActivity().findViewById(R.id.buy_button);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preference",Context.MODE_PRIVATE);
                gsonInstance = new Gson();
                if(sharedPreferences.contains("user")){
                    String json = sharedPreferences.getString("user",null);
                    Type type = new TypeToken<UserProfile>(){}.getType();
                    user = gsonInstance.fromJson(json,type);
                    List<LineItem> list = new ArrayList<>();
                    if(cartlocalArrayList==null || cartlocalArrayList.size()==0){
                        Snackbar.make(v,"Cart is empty",Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    for(int i=0;i<cartlocalArrayList.size();i++){
                        list.add(new LineItem(Integer.valueOf(cartlocalArrayList.get(i).getProductId()),Integer.valueOf(cartlocalArrayList.get(i).getProductQuantity()),0));
                    }
                    product_buy_request(user,list);
                }
                else {
                    Snackbar.make(v,"fill up user info",Snackbar.LENGTH_LONG).show();
                    userProfileFragment = new UserProfileFragment();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.child_fragment_container,userProfileFragment);
                    fragmentTransaction.commit();
                }

            }
        });

        if(cartlocalArrayList==null || cartlocalArrayList.size()==0){
            buy_now.setVisibility(View.GONE);
        }

        cartRV =(RecyclerView) getActivity().findViewById(R.id.cartRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cartRV.setHasFixedSize(true);
        cartRV.setLayoutManager(layoutManager1);
        cartArrayList = new ArrayList<>();
        cartListAdapter = new CartListAdapter(getActivity(), cartArrayList);
        cartRV.setAdapter(cartListAdapter);
        cartRV.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), cartRV ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int productId = Integer.parseInt(cartArrayList.get(position).getProductId());
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
                        // do whatever
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Delete item")
                                .setMessage("Remove product from list" )
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cartArrayList.remove(position);
                                        cartlocalArrayList.remove(position);
                                        cartListAdapter.notifyItemRemoved(position);
                                        cartListAdapter.notifyItemRangeChanged(position,cartArrayList.size());
                                        savedata();
                                    }
                                });

                        builder.create().show();
                    }
                })
        );

        makeCartList();
    }

    public void loadUserdata(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preference",Context.MODE_PRIVATE);
        gsonInstance = new Gson();
        if(sharedPreferences.contains("user")){
            String json = sharedPreferences.getString("user",null);
            Type type = new TypeToken<UserProfile>(){}.getType();
            user = gsonInstance.fromJson(json,type);
        }
        else {
            Snackbar.make(v,"fill up user info",Snackbar.LENGTH_LONG).show();
            userProfileFragment = new UserProfileFragment();
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.child_fragment_container,userProfileFragment);
            fragmentTransaction.commit();
        }
    }

    public void loaddata(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preference",Context.MODE_PRIVATE);
        gsonInstance = new Gson();
        String json = sharedPreferences.getString("cartlocal",null);
        Type type = new TypeToken<ArrayList<Cart>>(){}.getType();
        cartlocalArrayList = gsonInstance.fromJson(json,type);
        if(cartlocalArrayList==null){
            cartlocalArrayList =new ArrayList<>();
        }
    }

    public void savedata(){
        SharedPreferences sharedpref = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedpref.edit();
        gsonInstance = new Gson();
        String json = gsonInstance.toJson(cartlocalArrayList);
        editor.putString("cartlocal",json);
        editor.apply();
    }

    public void makeCartList(){

        String request_url =url;
        if(cartlocalArrayList !=null) {
            for (int i = 0; i < cartlocalArrayList.size(); i++) {
                String id =cartlocalArrayList.get(i).getProductId();
                request_url +=id+",";
            }
            product_details_api_request(request_url);
        }

    }



    public void product_details_api_request(String url){

        cart_progressbar.setVisibility(View.VISIBLE);

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
                                model.setProductQuantity(cartlocalArrayList.get(i).getProductQuantity());
                                model.setProductSize(cartlocalArrayList.get(i).getProductSize());
                                // adding model to product details  array
                                //Snackbar.make(v,"grid  "+model.getId(),Snackbar.LENGTH_SHORT).show();
                                cartArrayList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                cart_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        cart_progressbar.setVisibility(View.GONE);
                        cartListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                cart_progressbar.setVisibility(View.GONE);

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

    public void product_buy_request(UserProfile user,List<LineItem> list){
        Pojo obj = new Pojo();
        obj.setPaymentMethod("cash on delivery");
        obj.setPaymentMethodTitle("cash on delivery");
        obj.setLineItems(list);
        obj.setShipping(new Shipping(user.getFisrtName(),user.getLastName(),user.getStreetAddress(),user.getStreetAddress(),user.getCity(),user.getDistrict(),"","bangladesh"));
        obj.setBilling(new Billing(user.getFisrtName(),user.getLastName(),user.getStreetAddress(),user.getStreetAddress(),user.getCity(),user.getDistrict(),"","bangladesh",user.getEmail(),user.getMobile()));
        gsonInstance = new Gson();
        String Json = gsonInstance.toJson(obj);
        JSONObject objjj = null;
        try {
            objjj = new JSONObject(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,order_url,objjj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Snackbar.make(v,"Order placed you will receive a call soon",Snackbar.LENGTH_LONG).show();
                        cartlocalArrayList.clear();
                        cartArrayList.clear();
                        savedata();
                        cartListAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"Error order did not placed",Snackbar.LENGTH_LONG).show();


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

            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if(response.statusCode==404 ||response.statusCode==500){
                    Snackbar.make(v,"not found or internal server error",Snackbar.LENGTH_SHORT).show();
                }
                return super.parseNetworkResponse(response);
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);

    }

}
