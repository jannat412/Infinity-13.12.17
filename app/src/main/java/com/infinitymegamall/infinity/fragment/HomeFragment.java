package com.infinitymegamall.infinity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.infinitymegamall.infinity.Activity.HomePageActivity;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.MyCategory;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
import com.infinitymegamall.infinity.adapter.BestSellerCategoryAdapter;
import com.infinitymegamall.infinity.adapter.CategorylistAdapter;
import com.infinitymegamall.infinity.adapter.ExclusivelistAdapter;
import com.infinitymegamall.infinity.adapter.NewArrivalAdapter;
import com.infinitymegamall.infinity.adapter.Product_details_adapter;
import com.infinitymegamall.infinity.model.Exclusive;
import com.infinitymegamall.infinity.model.HomeCategory;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.Product_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    FragmentTransaction transaction;
    private ProductDetailViewFragment productDetailViewFragment;

    private SliderLayout mDemoSlider;
    private RecyclerView categorylistView;
    private static ArrayList<HomeCategory> categories;
    private static RecyclerView.Adapter categorylistAdapter;

   // private GridView exclusivelist;
   // private List<Exclusive> exclusives = new ArrayList<>();
   // private ExclusivelistAdapter exclusivelistAdapter;

    private RecyclerView newarrivalList;
    private static ArrayList<NewArrival> newArrivals;
    private static RecyclerView.Adapter newArrivalAdapter;

    private RecyclerView bestsellerList;
    private RecyclerView bestsellerDetailList;
    private RecyclerView.Adapter bestSellerCategoryAdapter;
    private RecyclerView.Adapter bestSellerProductDetailsAdapter;
    private ArrayList<NewArrival> bestSellerCategoryArraylist;
    private ArrayList<Product_details> bestSellerProductDetailsArrayList;

    private RecyclerView productDetailsList;
    private static ArrayList<Product_details> newProductDetails;
    private static RecyclerView.Adapter newProductDetailAdapter;
    private View v;

    private ProgressBar new_arrival_progress;
    private ProgressBar bestseller_progressbar;

    String main_url="https://infinitymegamall.com/wp-json/wc/v2/products?order=asc&category=";
    String main_url2="https://infinitymegamall.com/wp-json/wc/v2/products?min_price=3000&category=";
    String url ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=4&min_price=200";//?after=2017-02-19T16:39:57-08:00";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    String category_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=0&per_page=10";


    public static final String API_KEY = "AIzaSyDkadPHN-zCcuIGermsAlpwpMXhurR8BVk";
    private static String VIDEO_ID = "sBGiyjOrRIs";


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getActivity().findViewById(R.id.home_activity_id);

        HomePageActivity homePageActivity = (HomePageActivity) getActivity();

        //top slider
        mDemoSlider = (SliderLayout)getActivity().findViewById(R.id.slider);

        //Category drawer
        categorylistView = (RecyclerView) getActivity().findViewById(R.id.category_list);

        //exclusive grid view
        //exclusivelist = (GridView ) getActivity().findViewById(R.id.exclusive);

        //new arrival
        newarrivalList = (RecyclerView) getActivity().findViewById(R.id.newarrivalList);
        productDetailsList = (RecyclerView) getActivity().findViewById(R.id.newarrivalDetailList);
        new_arrival_progress = (ProgressBar) getActivity().findViewById(R.id.newarrival_progressbar);

        //best seller
        bestsellerList = (RecyclerView) getActivity().findViewById(R.id.bestsellerList);
        bestsellerDetailList = (RecyclerView) getActivity().findViewById(R.id.bestsellerDetailList);
        bestseller_progressbar = (ProgressBar) getActivity().findViewById(R.id.bestseller_progressbar);

        //Image slider start
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Welcome to Infinity", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Welcome to Infinity", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("Welcome to Infinity", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Welcome to Infinity", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Welcome to Infinity",R.drawable.slider1);
        file_maps.put("Buy exclusive products",R.drawable.slider2);
        file_maps.put("Happy to see you",R.drawable.slider3);
        file_maps.put("Best quality ", R.drawable.slider4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }
        //Image slider ends

        //circle categories under the slider start
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categorylistView.setHasFixedSize(true);
        categorylistView.setLayoutManager(layoutManager);

        categories = new ArrayList<HomeCategory>();
        for (int i = 0; i < MyCategory.category.length; i++) {
            categories.add(new HomeCategory(
                    MyCategory.category[i],
                    MyCategory.image[i],
                    MyCategory.id[i]

            ));
        }

        categorylistAdapter = new CategorylistAdapter(getActivity(), categories);
        categorylistView.setAdapter(categorylistAdapter);
        categorylistView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), categorylistView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Snackbar.make(v,categories.get(position).getCategoryName(),Snackbar.LENGTH_LONG).show();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        // circle category slider end


        //exclusive products
       /* exclusivelistAdapter = new ExclusivelistAdapter(getActivity(),exclusives);
        exclusivelist.setAdapter(exclusivelistAdapter);

        exclusivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int exPosition, long l) {
                //Toast.makeText(getActivity(), ""+exclusives.get(exPosition).getExclusiveText(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(v,exclusives.get(exPosition).getExclusiveText(),Snackbar.LENGTH_LONG).show();
                int productId = exclusives.get(exPosition).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("productId",productId);
                productDetailViewFragment = new ProductDetailViewFragment();
                productDetailViewFragment.setArguments(bundle);
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.child_fragment_container, productDetailViewFragment);
                transaction.addToBackStack("ProductDetailViewFragment");
                transaction.commit();
            }
        });*/


        //new arrival****************************
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        newarrivalList.setHasFixedSize(true);
        newarrivalList.setLayoutManager(layoutManager1);
        newArrivals = new ArrayList<>();
        newArrivalAdapter = new NewArrivalAdapter(getActivity(), newArrivals);
        newarrivalList.setAdapter(newArrivalAdapter);
        newarrivalList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), newarrivalList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String men_product_url =main_url+String.valueOf(newArrivals.get(position).getId());;
                        product_details_api_request(men_product_url);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        //new arrival**********************************


        //bestseller tab
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bestsellerList.setHasFixedSize(true);
        bestsellerList.setLayoutManager(layoutManager3);
        bestSellerCategoryArraylist = new ArrayList<>();
        bestSellerCategoryAdapter = new BestSellerCategoryAdapter(getActivity(), bestSellerCategoryArraylist);
        bestsellerList.setAdapter(bestSellerCategoryAdapter);
        bestsellerList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), bestsellerList, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String men_product_url =main_url2+String.valueOf(newArrivals.get(position).getId());;
                        product_details_api_request2(men_product_url);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        productDetailsList.setHasFixedSize(true);
        productDetailsList.setLayoutManager(layoutManager2);

        newProductDetails = new ArrayList<Product_details>();
        String men_product_url =main_url+"37";
        product_details_api_request(men_product_url);
        newProductDetailAdapter  = new Product_details_adapter(getActivity(),newProductDetails);
        productDetailsList.setAdapter(newProductDetailAdapter);
        productDetailsList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), productDetailsList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Snackbar.make(v,newProductDetails.get(position).getProduct_image(),Snackbar.LENGTH_LONG).show();
                        int productId = newProductDetails.get(position).getId();
                        Bundle bundle = new Bundle();
                        bundle.putInt("productId",productId);
                        productDetailViewFragment = new ProductDetailViewFragment();
                        productDetailViewFragment.setArguments(bundle);
                        transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.child_fragment_container, productDetailViewFragment);
                        transaction.addToBackStack("ProductDetailViewFragment");
                        transaction.commit();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bestsellerDetailList.setHasFixedSize(true);
        bestsellerDetailList.setLayoutManager(layoutManager4);

        bestSellerProductDetailsArrayList = new ArrayList<>();
        String men_product_url2 =main_url2+"34";
        product_details_api_request2(men_product_url2);
        bestSellerProductDetailsAdapter  = new Product_details_adapter(getActivity(),bestSellerProductDetailsArrayList);
        bestsellerDetailList.setAdapter(bestSellerProductDetailsAdapter);
        bestsellerDetailList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), bestsellerDetailList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(v,bestSellerProductDetailsArrayList.get(position).getProduct_name(),Snackbar.LENGTH_LONG).show();
                int productId = bestSellerProductDetailsArrayList.get(position).getId();
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
        }));
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(API_KEY, new OnInitializedListener() {

            @Override
            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(VIDEO_ID);
                }
            }

            @Override
            public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

        categories_api_request();
        //request2();

    }

    public void categories_api_request(){

        // Creating volley request obj
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(category_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                NewArrival model = new NewArrival();
                                model.setId(obj.getInt("id"));
                                model.setNewArrival(obj.getString("name"));
                                // adding model to category array
                                bestSellerCategoryArraylist.add(model);
                                newArrivals.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        bestSellerCategoryAdapter.notifyDataSetChanged();
                        newArrivalAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"home fragment error",Snackbar.LENGTH_LONG).show();

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



/*

    public void request2(){

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
                                Exclusive model = new Exclusive();
                                model.setId(obj.getInt("id"));
                                model.setExclusiveText(obj.getString("name"));
                                JSONArray img = obj.getJSONArray("images");
                                JSONObject item = img.getJSONObject(0);
                                String image = item.getString("src");
                                model.setImage(image);
                                // adding model to movies array
                                exclusives.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        exclusivelistAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"error req2",Snackbar.LENGTH_LONG).show();

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
            */
/*@Override
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
            }*//*

        };

        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);

    }
*/

    public void product_details_api_request(String api){
        new_arrival_progress.setVisibility(View.VISIBLE);
        newProductDetails.clear();
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
                                newProductDetails.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                new_arrival_progress.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        new_arrival_progress.setVisibility(View.GONE);
                        newProductDetailAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                new_arrival_progress.setVisibility(View.GONE);

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
  public void product_details_api_request2(String api){
        bestseller_progressbar.setVisibility(View.VISIBLE);
      bestSellerProductDetailsArrayList.clear();
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
                                bestSellerProductDetailsArrayList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_LONG).show();
                                bestseller_progressbar.setVisibility(View.GONE);
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        bestseller_progressbar.setVisibility(View.GONE);
                        bestSellerProductDetailsAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"check your internet connection",Snackbar.LENGTH_LONG).show();
                bestseller_progressbar.setVisibility(View.GONE);

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

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra")+ "", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
