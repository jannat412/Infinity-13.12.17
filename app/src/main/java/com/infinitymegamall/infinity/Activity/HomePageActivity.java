package com.infinitymegamall.infinity.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.adapter.Category_drawer_adapter;
import com.infinitymegamall.infinity.fragment.CartFragment;
import com.infinitymegamall.infinity.fragment.CategoryItemFragment;
import com.infinitymegamall.infinity.fragment.HomeFragment;
import com.infinitymegamall.infinity.fragment.UserProfileFragment;
import com.infinitymegamall.infinity.fragment.WishlistFragment;
import com.infinitymegamall.infinity.model.ChildCategory;
import com.infinitymegamall.infinity.model.ParentCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {


    LinearLayout parentlayout;
    public FragmentTransaction fragmentTransaction;
    public FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private WishlistFragment wishFragment;
    private CartFragment cartFragment;
    private UserProfileFragment userProfileFragment;
    private CategoryItemFragment categoryItemFragment;

    private SwipeRefreshLayout swipeRefreshLayout;

    private LinkedHashMap<String, ParentCategory> sub_category_hashmap = new LinkedHashMap<String, ParentCategory>();
    private ArrayList<ParentCategory> parent_category_arraylist = new ArrayList<ParentCategory>();

    private Category_drawer_adapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    View v;

    String category_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=0";
    String subcategory_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=";
    //String product_url ="https://infinitymegamall.com/wp-json/wc/v2/products/52511";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";
    String a ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(toolbar);

        v = findViewById(R.id.home_activity_id);

        //listView = (ListView) findViewById(R.id.navigation_list);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        sub_category_hashmap.clear();
                        parent_category_arraylist.clear();
                        categories_api_request();

                    }
                }
        );
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView menuRight = (ImageView) findViewById(R.id.menuRight);
        menuRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        } else {
                            drawer.openDrawer(GravityCompat.START);
                        }
                    }
                });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        simpleExpandableListView = (ExpandableListView) findViewById(R.id.category_nav_list);
        // create the adapter by passing your ArrayList data
        listAdapter = new Category_drawer_adapter(HomePageActivity.this, parent_category_arraylist);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                //get the group header
                ParentCategory headerInfo = parent_category_arraylist.get(groupPosition);
                //get the child info
                ChildCategory detailInfo =  headerInfo.getList().get(childPosition);
                //display it or do something with it
                int cid = Integer.valueOf(detailInfo.getId());
                Bundle bundle = new Bundle();
                bundle.putInt("category",cid);
                categoryItemFragment = new CategoryItemFragment();
                categoryItemFragment.setArguments(bundle);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, categoryItemFragment);
                fragmentTransaction.commit();

                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header


                return false;
            }
        });



        parentlayout = (LinearLayout) findViewById(R.id.parentlayout);
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, homeFragment);
        fragmentTransaction.commit();

        categories_api_request();


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
                            String id="",name="";
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                id= obj.getString("id");
                                name=obj.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_SHORT).show();
                            }
                            subcategories_api_request(id,name);
                        }
                        // notifying list adapter about data changes
                        listAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"Unable to load category",Snackbar.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        Server_request.getInstance().addToRequestQueue(jsObjRequest);


    }

    public void subcategories_api_request(final String category_id,final String category_name){

        String api = subcategory_url+category_id;

        // Creating volley request obj
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(api,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            String name="",id="";
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                name = obj.getString("name");
                                id = obj.getString("id");

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_SHORT).show();
                            }

                            addProduct(category_id,category_name,id,name);
                        }
                        // notifying list adapter about data changes

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"Unable to load sub-category",Snackbar.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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
    private void addProduct(String categoryid,String categoryname,String subcategoryid, String subcategoryname){

        //check the hash map if the group already exists
        ParentCategory headerInfo = sub_category_hashmap.get(categoryname);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new ParentCategory();
            headerInfo.setName(categoryname);
            headerInfo.setId(categoryid);
            sub_category_hashmap.put(categoryname, headerInfo);
            parent_category_arraylist.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildCategory> productList = headerInfo.getList();

        //create a new child and add that to the group
        ChildCategory detailInfo = new ChildCategory();
        detailInfo.setId(subcategoryid);
        detailInfo.setCategory(subcategoryname);
        if(!productList.contains(detailInfo))
        {
        productList.add(detailInfo);
        }
        headerInfo.setList(productList);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart) {
            cart();
            return true;
        }
        /*else if (id == R.id.action_search) {

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void home(View view) {

        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, homeFragment);
        fragmentTransaction.commit();
    }

    public void cart(){

        cartFragment = new CartFragment();
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, cartFragment);
        fragmentTransaction.addToBackStack("CartFragment");
        fragmentTransaction.commit();
    }

    public void wishlist(View view) {

        wishFragment = new WishlistFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container,wishFragment);
        fragmentTransaction.addToBackStack("WishlistFragment");
        fragmentTransaction.commit();

    }

    public void user(View view) {
        //Snackbar.make(v,"user",Snackbar.LENGTH_LONG).show();
        userProfileFragment = new UserProfileFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container,userProfileFragment);
        fragmentTransaction.addToBackStack("UserProfileFragment");
        fragmentTransaction.commit();
    }

    public void more(View view) {
        Snackbar.make(v,"More",Snackbar.LENGTH_LONG).show();

    }


    @Override
    public void onRefresh() {

    }
}
