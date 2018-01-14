package com.infinitymegamall.infinity.Activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.android.volley.toolbox.JsonRequest;
import com.infinitymegamall.infinity.Connection.Server_request;
import com.infinitymegamall.infinity.MyData;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.adapter.Category_drawer_adapter;
import com.infinitymegamall.infinity.adapter.NavigationCategoryAdapter;
import com.infinitymegamall.infinity.fragment.CartFragment;
import com.infinitymegamall.infinity.fragment.CategoryItemFragment;
import com.infinitymegamall.infinity.fragment.HomeFragment;
import com.infinitymegamall.infinity.fragment.UserProfileFragment;
import com.infinitymegamall.infinity.fragment.WishlistFragment;
import com.infinitymegamall.infinity.model.ChildCategory;
import com.infinitymegamall.infinity.model.NewArrival;
import com.infinitymegamall.infinity.model.ParentCategory;
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

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {

    private static ListView listView;
    private static ArrayList<NewArrival> categories;
    NavigationCategoryAdapter adapter;
    LinearLayout parentlayout;
    public FragmentTransaction fragmentTransaction;
    public FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private WishlistFragment wishFragment;
    private CategoryItemFragment categoryItemFragment;
    private CartFragment cartFragment;
    private UserProfileFragment userProfileFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView category;
    List<ParentCategory> category_list;
    Category_drawer_adapter category_adapter;

    View v;

    String category_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=0";
    String subcategory_url="https://infinitymegamall.com/wp-json/wc/v2/products/categories?parent=";
   // String product_url ="https://infinitymegamall.com/wp-json/wc/v2/products/52511";
    String username="ck_cf774d8324810207b32ded1a1ed5e973bf01a6fa";
    String password ="cs_ea7d6990bd6e3b6d761ffbc2c222c56746c78d95";


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


                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        category = (RecyclerView) findViewById(R.id.category_nav_list);
        category_list = new ArrayList<>();
        categories_api_request();


        //categories_api_request();
/*        for (int i = 0; i < MyData.category.length; i++) {
            categories.add(new nv_category(
                    MyData.category[i],
                    MyData.id[i]

            ));
        }*/
/*        adapter = new NavigationCategoryAdapter(this, categories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                Bundle bundle = new Bundle();
                int id= categories.get(position).getId();
                bundle.putInt("category",id);
                categoryItemFragment = new CategoryItemFragment();
                categoryItemFragment.setArguments(bundle);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, categoryItemFragment);
                fragmentTransaction.commit();
                //Toast.makeText(HomePageActivity.this, ""+categories.get(position).getNewArrival(), Toast.LENGTH_SHORT).show();
            }
        });*/

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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


        parentlayout = (LinearLayout) findViewById(R.id.parentlayout);
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, homeFragment);
        fragmentTransaction.commit();



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
                                subcategories_api_request(obj.getString("id"),obj.getString("name"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_SHORT).show();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        category_adapter = new Category_drawer_adapter(category_list);
                        category.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));
                        category.setAdapter(category_adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"Unable to load category",Snackbar.LENGTH_SHORT).show();

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

    public void subcategories_api_request(String id,final String cat){

        String api = subcategory_url+id;
        final List<ChildCategory> child = new ArrayList<>();
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
                                String name = obj.getString("name");
                                int id = obj.getInt("id");
                                child.add(new ChildCategory(name,id));


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Snackbar.make(v,"something went wrong",Snackbar.LENGTH_SHORT).show();
                            }


                        }
                        // notifying list adapter about data changes

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Snackbar.make(v,"Unable to load category",Snackbar.LENGTH_SHORT).show();

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
        category_list.add(new ParentCategory(cat,child));

    }


    public void makeCategory(){

            List<ChildCategory> child = new ArrayList<>();
            child.add(new ChildCategory("ddd",2));
            child.add(new ChildCategory("sajhgbkasjd",4));
            child.add(new ChildCategory("dsjbfj",5));
            category_list.add(new ParentCategory("Shuvo",child));
            category_list.add(new ParentCategory("jannat",child));
            category_list.add(new ParentCategory("jannat",child));

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
        fragmentTransaction.commit();
    }

    public void wishlist(View view) {

        wishFragment = new WishlistFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container,wishFragment);
        fragmentTransaction.commit();

    }

    public void user(View view) {
        //Snackbar.make(v,"user",Snackbar.LENGTH_LONG).show();
        userProfileFragment = new UserProfileFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container,userProfileFragment);
        fragmentTransaction.commit();
    }

    public void more(View view) {
        Snackbar.make(v,"More",Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void onRefresh() {

    }


}
