package com.infinitymegamall.infinity.fragment;

/**
 * Created by shuvo on 16-Jan-18.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.Category_frag_grid_adapter;
import com.infinitymegamall.infinity.model.Product_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Search_fragment extends Fragment{
    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;
    private RecyclerView search_fag_product_grid;
    private static ArrayList<Product_details> search_fag_product_list;
    private static RecyclerView.Adapter search_fag_grid_adapter;
    private String search ="";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    String a ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=36&search=";
    String main_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=";
    private TextView result;
    private ProgressBar fragment_progressbar;
    private View v;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search = getArguments().getString("search");
            //Toast.makeText(getActivity(),productId,Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_products, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v = getActivity().findViewById(R.id.home_activity_id);
        result =(TextView) getActivity().findViewById(R.id.result);
        fragment_progressbar = (ProgressBar) getActivity().findViewById(R.id.search_item_progressbar);
        search_fag_product_list = new ArrayList<>();
        search_fag_product_grid = (RecyclerView) getActivity().findViewById(R.id.search_frag_gridview);
        search_fag_grid_adapter  = new Category_frag_grid_adapter(getActivity(),search_fag_product_list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        search_fag_product_grid.setLayoutManager(gridLayoutManager);
        search_fag_product_grid.setHasFixedSize(true);
        search_fag_product_grid.setItemAnimator(new DefaultItemAnimator());
        search_fag_product_grid.setAdapter(search_fag_grid_adapter);

        search_fag_product_grid.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), search_fag_product_grid ,new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        //String s= catfag_product_list.get(position).getProduct_image();
                        //Snackbar.make(v,s,Snackbar.LENGTH_LONG).show();
                        int productId = search_fag_product_list.get(position).getId();
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

        product_details_api_request(search);

    }



    public void product_details_api_request(String postfix){
        //catfag_product_list.clear();
        fragment_progressbar.setVisibility(View.VISIBLE);
        String api= a+postfix;
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
                                search_fag_product_list.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                fragment_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        fragment_progressbar.setVisibility(View.GONE);
                        search_fag_grid_adapter.notifyDataSetChanged();
                        if(search_fag_product_list.size()>0){
                            result.setText("Search results for ' "+search+" '");
                        }
                        else {
                            result.setText("0 Search results for ' "+search+" '");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                fragment_progressbar.setVisibility(View.GONE);

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
