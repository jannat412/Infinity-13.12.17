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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.infinitymegamall.infinity.Activity.HomePageActivity;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.MyCategory;
import com.infinitymegamall.infinity.MyNewArival;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.RecyclerItemClickListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    private RecyclerView categorylistView;
    private static ArrayList<HomeCategory> categories;
    private static RecyclerView.Adapter categorylistAdapter;

    private GridView exclusivelist;
    private List<Exclusive> exclusives = new ArrayList<Exclusive>();
    private ExclusivelistAdapter exclusivelistAdapter;

    private RecyclerView newarrivalList;
    private static ArrayList<NewArrival> newArrivals;
    private static RecyclerView.Adapter newArrivalAdapter;

    private RecyclerView productDetailsList;
    private static ArrayList<Product_details> newProductDetails;
    private static RecyclerView.Adapter newProductDetailAdapter;
    private View v;

    String main_url="https://infinitymegamall.com/wp-json/wc/v2/products?category=";
    String url ="https://infinitymegamall.com/wp-json/wc/v2/products?per_page=10&min_price=200";//?after=2017-02-19T16:39:57-08:00";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";


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
        mDemoSlider = (SliderLayout)getActivity().findViewById(R.id.slider);
        categorylistView = (RecyclerView) getActivity().findViewById(R.id.category_list);
        exclusivelist = (GridView) getActivity().findViewById(R.id.exclusive);
        newarrivalList = (RecyclerView) getActivity().findViewById(R.id.newarrivalList);
        productDetailsList = (RecyclerView) getActivity().findViewById(R.id.newarrivalDetailList);


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

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        newarrivalList.setHasFixedSize(true);
        newarrivalList.setLayoutManager(layoutManager1);

        newArrivals = new ArrayList<NewArrival>();
        for(int i = 0; i< MyNewArival.newarrival.length; i++){
            newArrivals.add(new NewArrival(
                    MyNewArival.newarrival[i],
                    MyNewArival.id[i]
            ));
        }
        newArrivalAdapter = new NewArrivalAdapter(getActivity(), newArrivals);
        newarrivalList.setAdapter(newArrivalAdapter);

        newarrivalList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), newarrivalList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String categoryName = newArrivals.get(position).getNewArrival();

                        switch (categoryName){
                            case "WOMEN":
                                String women_url =main_url+"34";
                                newProductDetails.clear();
                                request3(women_url);
                                break;

                            case "MEN":
                                String men_url =main_url+"37";
                                newProductDetails.clear();
                                request3(men_url);
                                break;


                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        request2();

        exclusivelistAdapter = new ExclusivelistAdapter(getActivity(),exclusives);
        exclusivelist.setAdapter(exclusivelistAdapter);

        exclusivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int exPosition, long l) {
                Toast.makeText(getActivity(), ""+exclusives.get(exPosition).getExclusiveText(), Toast.LENGTH_SHORT).show();

            }
        });

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.slider1);
        file_maps.put("Big Bang Theory",R.drawable.slider2);
        file_maps.put("House of Cards",R.drawable.slider3);
        file_maps.put("Game of Thrones", R.drawable.slider4);

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
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        productDetailsList.setHasFixedSize(true);
        productDetailsList.setLayoutManager(layoutManager2);

        newProductDetails = new ArrayList<Product_details>();
        String men_product_url =main_url+"37";
        request3(men_product_url);
        newProductDetailAdapter  = new Product_details_adapter(getActivity(),newProductDetails);
        productDetailsList.setAdapter(newProductDetailAdapter);


    }

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
        };

        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);

    }

    public void request3( String api){

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
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        newProductDetailAdapter.notifyDataSetChanged();
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
