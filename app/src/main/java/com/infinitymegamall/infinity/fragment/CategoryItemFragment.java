package com.infinitymegamall.infinity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.infinitymegamall.infinity.Activity.HomePageActivity;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.CategoryPageCategoryItemAdapter;
import com.infinitymegamall.infinity.adapter.Category_frag_grid_adapter;
import com.infinitymegamall.infinity.adapter.ExclusivelistAdapter;
import com.infinitymegamall.infinity.model.Exclusive;
import com.infinitymegamall.infinity.model.Product_details;
import com.infinitymegamall.infinity.model.nv_category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class CategoryItemFragment extends Fragment {

    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;

    private RecyclerView category_item_list;
    private static ArrayList<nv_category> category_arraylist;
    private static RecyclerView.Adapter category_adapter;

    private RecyclerView catfag_product_grid;
    private static ArrayList<Product_details> catfag_product_list;
    private static RecyclerView.Adapter catfag_grid_adapter;

    private View v;
    private ProgressBar category_fragment_progressbar;

    private int visibleThreshold = 18;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    int inc =1;


    String a ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=20&category=";
    String main_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=";
    String lubnan_store_url="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=20&tag=293&page=";
    String richman_store_url="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=20&tag=266&page=";
    //String url ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=10&min_price=200";//?after=2017-02-19T16:39:57-08:00";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    String category_id="";
    int category;
    int lubnan_int = 293;
    String lubnan ="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getInt("category");
            category_id = Integer.toString(category);

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

        lubnan = Integer.toString(lubnan_int);
        v = getActivity().findViewById(R.id.home_activity_id);
        category_fragment_progressbar = (ProgressBar) getActivity().findViewById(R.id.category_item_progressbar);
        category_item_list = (RecyclerView) getActivity().findViewById(R.id.category_item_List);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        category_item_list.setHasFixedSize(true);
        category_item_list.setLayoutManager(layoutManager1);


        category_arraylist = new ArrayList<>();
        category_arraylist.add(new nv_category("All", 0));

        if(category_id.equals("293") || category_id.equals("266")){

        }
        else {
            subcategories_api_request(category_id);
        }
        category_adapter = new CategoryPageCategoryItemAdapter(getActivity(), category_arraylist);
        category_item_list.setAdapter(category_adapter);

        category_item_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), category_item_list ,new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {

                        String sub_cat_id = category_arraylist.get(position).getCategoryName();
                        //Snackbar.make(v,sub_cat_id,Snackbar.LENGTH_LONG).show();

                        if(category_id.equals("293")){
                            catfag_product_list.clear();
                            product_store_api_request(lubnan_store_url+"1");
                        }
                        else if(category_id.equals("266")){
                            catfag_product_list.clear();
                            product_store_api_request(richman_store_url+"1");
                        }
                        else {
                            if (sub_cat_id == "All") {
                                catfag_product_list.clear();
                                product_details_api_request(category_id);
                            } else {
                                catfag_product_list.clear();
                                String a = String.valueOf(category_arraylist.get(position).getId());
                                product_details_api_request(a);
                            }
                        }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                } ));

        catfag_product_list = new ArrayList<>();
        catfag_product_grid = (RecyclerView) getActivity().findViewById(R.id.category_frag_gridview);
        catfag_grid_adapter  = new Category_frag_grid_adapter(getActivity(),catfag_product_list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        catfag_product_grid.setLayoutManager(gridLayoutManager);
        catfag_product_grid.setHasFixedSize(true);
        catfag_product_grid.setItemAnimator(new DefaultItemAnimator());
        catfag_product_grid.setAdapter(catfag_grid_adapter);

        catfag_product_grid.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), catfag_product_grid ,new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        //String s= catfag_product_list.get(position).getProduct_image();
                        //Snackbar.make(v,s,Snackbar.LENGTH_LONG).show();
                        int productId = catfag_product_list.get(position).getId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("productId",productId);
                        productDetailViewFragment = new ProductDetailViewFragment();
                        productDetailViewFragment.setArguments(bundle);
                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.child_fragment_container, productDetailViewFragment);
                        transaction.addToBackStack("ProductDetailViewFragment");
                        transaction.commit();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                } ));

        catfag_product_grid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
               // Snackbar.make(v," grid scroll",Snackbar.LENGTH_LONG).show();

                totalItemCount = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                if (loading==false && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    inc++;
                    if(category_id.equals("293")){
                        product_store_api_request(lubnan_store_url+inc);
                    }
                    else if(category_id.equals("266")){
                        product_store_api_request(richman_store_url+inc);
                    }
                    else {
                        String a = category_id + "&page=" + inc;
                        product_details_api_request(a);
                    }
                }
            }
        });

        if(category_id.equals("293")){
            product_store_api_request(lubnan_store_url+"1");
        }else if(category_id.equals("266")){
            product_store_api_request(richman_store_url+"1");
        }else {
            product_details_api_request(category_id + "&page=1");
        }
    }


    public void subcategories_api_request(String postfix){

        String subcat_url=main_url+postfix;
        // Creating volley request obj
        category_fragment_progressbar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(subcat_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                nv_category model = new nv_category();
                                model.setId(obj.getInt("id"));
                                model.setCategoryName(obj.getString("name"));
                                category_arraylist.add(model);
                                // adding model to movies array


                            } catch (JSONException e) {
                                e.printStackTrace();
                                category_fragment_progressbar.setVisibility(View.GONE);
                                Snackbar.make(v," json exception something went wrong",Snackbar.LENGTH_LONG).show();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        category_fragment_progressbar.setVisibility(View.GONE);
                        category_adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                category_fragment_progressbar.setVisibility(View.GONE);
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
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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
                    return Response.success(new JSONArray(jsonString), cacheEntry);
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

    public void product_store_api_request(String postfix){
        //catfag_product_list.clear();
        category_fragment_progressbar.setVisibility(View.VISIBLE);

        loading = true;
        // Creating volley request obj
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(postfix,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Product_details model = new Product_details();
                                model.setId(obj.getInt("id"));
                                model.setProduct_name(obj.getString("name"));
                                model.setProduct_price(obj.getString("price"));
                                JSONArray img = obj.getJSONArray("images");
                                JSONObject item = img.getJSONObject(0);
                                String image = item.getString("src");
                                model.setProduct_image(image);
                                // adding model to product details  array
                                //Snackbar.make(v,"grid  "+model.getId(),Snackbar.LENGTH_SHORT).show();
                                catfag_product_list.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                category_fragment_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        category_fragment_progressbar.setVisibility(View.GONE);
                        catfag_grid_adapter.notifyDataSetChanged();
                        loading = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                category_fragment_progressbar.setVisibility(View.GONE);

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
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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
                    return Response.success(new JSONArray(jsonString), cacheEntry);
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

    public void product_details_api_request(String postfix){
        //catfag_product_list.clear();
        category_fragment_progressbar.setVisibility(View.VISIBLE);
        String api= a+postfix;
        loading = true;
        // Creating volley request obj
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Product_details model = new Product_details();
                                model.setId(obj.getInt("id"));
                                model.setProduct_name(obj.getString("name"));
                                model.setProduct_price(obj.getString("price"));
                                JSONArray img = obj.getJSONArray("images");
                                JSONObject item = img.getJSONObject(0);
                                String image = item.getString("src");
                                model.setProduct_image(image);
                                // adding model to product details  array
                                //Snackbar.make(v,"grid  "+model.getId(),Snackbar.LENGTH_SHORT).show();
                                catfag_product_list.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                category_fragment_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        category_fragment_progressbar.setVisibility(View.GONE);
                        catfag_grid_adapter.notifyDataSetChanged();
                        loading = false;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                category_fragment_progressbar.setVisibility(View.GONE);

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
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
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
                    return Response.success(new JSONArray(jsonString), cacheEntry);
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
