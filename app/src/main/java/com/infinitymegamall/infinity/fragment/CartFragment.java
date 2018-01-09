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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
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
import com.google.gson.reflect.TypeToken;
import com.infinitymegamall.infinity.Billing;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.Shipping;
import com.infinitymegamall.infinity.adapter.CartListAdapter;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.WishListAdapter;
import com.infinitymegamall.infinity.model.Cart;
import com.infinitymegamall.infinity.model.Cartproduct;
import com.infinitymegamall.infinity.model.Gallery;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;
import com.infinitymegamall.infinity.pojo.Pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class CartFragment extends Fragment{

    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;
    private RecyclerView cartRV;
    private static ArrayList<Cartproduct> cartproductArrayList;
    private static RecyclerView.Adapter cartListAdapter;

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

        buy_now =(Button) getActivity().findViewById(R.id.buy_button);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cartRV =(RecyclerView) getActivity().findViewById(R.id.cartRecycler);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cartRV.setHasFixedSize(true);
        cartRV.setLayoutManager(layoutManager1);
        cartArrayList = new ArrayList<>();
        /*cartArrayList.add(new Cartproduct("2","Jannat","https://assets.teleflora.com/images/customhtml/meaning-of-flowers/carnation.png",
                "2","XXl","5"));*/
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

    public void deletedata(String id){
        outerloop:
        for(int i = 0;i<cartlocalArrayList.size();i++){
            String pid = cartlocalArrayList.get(i).getProductId();
            String cid = cartArrayList.get(i).getProductId();
            if(pid == id && cid == id){
                cartlocalArrayList.remove(i);
                cartArrayList.remove(i);
                break outerloop;
            }
        }
        cartListAdapter.notifyDataSetChanged();
        savedata();
        Snackbar.make(v,"Item deleted",Snackbar.LENGTH_SHORT).show();
    }

    public void makeCartList(){

        String request_url =url;
        if(cartlocalArrayList !=null) {
            for (int i = 0; i < cartlocalArrayList.size(); i++) {
                String id =cartlocalArrayList.get(i).getProductId();
                request_url +=id+",";
                //Toast.makeText(getContext(), request_url, Toast.LENGTH_SHORT).show();
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

    public void product_buy_request(){
        Pojo obj = new Pojo();
        obj.setShipping(new Shipping("","","","","","","",""));
        obj.setBilling(new Billing("","","","","","","","","",""));
        gsonInstance = new Gson();
        String Json = gsonInstance.toJson(obj);
        try {
            JSONObject jsonobj = new JSONObject(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();


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
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);

    }

}
