package com.infinitymegamall.infinity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.gson.JsonObject;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.MyCategory;
import com.infinitymegamall.infinity.MyGallery;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.CategorylistAdapter;
import com.infinitymegamall.infinity.adapter.GalleryImageAdapter;
import com.infinitymegamall.infinity.model.Gallery;
import com.infinitymegamall.infinity.model.HomeCategory;
import com.infinitymegamall.infinity.model.Product_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class ProductDetailViewFragment extends Fragment {
    private RecyclerView galler_list;
    private static ArrayList<Gallery> galleries;
    private static RecyclerView.Adapter galleryAdapter;
    private ImageView galleryView;

    private TextView product_name, product_price, addtocart;
    private EditText quantity;
    private ImageView up, down;
    int quantities = 1;

    private static String productId;
    private View v;
    private static String producyURL ="https://infinitymegamall.com/wp-json/wc/v2/products/";
    private static String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    private static String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";

    public ProductDetailViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            productId = Integer.toString(getArguments().getInt("productId"));
            Toast.makeText(getActivity(),productId,Toast.LENGTH_LONG).show();

        }
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


        galleryAdapter = new GalleryImageAdapter(getActivity(), galleries);
        galler_list.setAdapter(galleryAdapter);
        galler_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), galler_list ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Snackbar.make(v,galleries.get(position).getGalleryImage(),Snackbar.LENGTH_LONG).show();
                        Glide.with(getActivity())
                                .load(galleries.get(position).getGalleryImage())
                                .centerCrop()
                                .placeholder(R.drawable.loading)
                                .crossFade()
                                .into(galleryView);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        product_request(producyURL+productId);
    }

    public void product_request(String url){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            product_name.setText(response.getString("name"));
                            product_price.setText("৳ "+ response.getString("price"));
                            JSONArray array = response.getJSONArray("images");

                            for(int i=0;i<array.length();i++){
                                JSONObject obj = array.getJSONObject(i);
                                galleries.add(new Gallery(obj.getString("src"),response.getInt("id")));
                                if(i==0){
                                    Glide.with(getActivity())
                                            .load(obj.getString("src"))
                                            .centerCrop()
                                            .placeholder(R.drawable.loading)
                                            .crossFade()
                                            .into(galleryView);
                                }

                            }

                            // adding model to product details  array
                            Snackbar.make(v,"grid  ",Snackbar.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        galleryAdapter.notifyDataSetChanged();
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

