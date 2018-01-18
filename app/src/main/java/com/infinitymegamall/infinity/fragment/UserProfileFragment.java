package com.infinitymegamall.infinity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infinitymegamall.infinity.R;
import com.infinitymegamall.infinity.model.Cart;
import com.infinitymegamall.infinity.model.UserProfile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shuvo on 06-Jan-18.
 */


public class UserProfileFragment extends Fragment{

    private Gson gsonInstance;
    private UserProfile user;
    private EditText firstName,lastName,district,city,address,email,mobile;
    private String firstNames,lastNames,districts,citys,addresss,emails,mobiles;
    private Button save;
    private String error_msg="Invalid";
    private Boolean valid = true;
    private View v;
    private ImageView backbutton;

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v = getActivity().findViewById(R.id.home_activity_id);
        save = (Button)getActivity().findViewById(R.id.user_button);
        firstName = (EditText) getActivity().findViewById(R.id.first_name);
        lastName =  (EditText) getActivity().findViewById(R.id.last_name);
        district =  (EditText) getActivity().findViewById(R.id.district);
        city =  (EditText) getActivity().findViewById(R.id.city);
        address =  (EditText) getActivity().findViewById(R.id.street_address);
        email =  (EditText) getActivity().findViewById(R.id.email);
        mobile =  (EditText) getActivity().findViewById(R.id.phone);

        backbutton = (ImageView)getActivity().findViewById(R.id.backbutton_profile);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                } else {
                    //super.onBackPressed();
                }
            }
        });
        loadData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid = true;
                firstNames = firstName.getText().toString();
                lastNames = lastName.getText().toString();
                districts = district.getText().toString();
                citys = city.getText().toString();
                addresss = address.getText().toString();
                emails = email.getText().toString();
                mobiles = mobile.getText().toString();
                if(isNullOrEmpty(firstNames)){
                    error_msg += " first name,";
                    valid =false;
                }
                if(isNullOrEmpty(lastNames)){
                    error_msg+=" last name,";
                    valid =false;
                }
                if(isNullOrEmpty(districts)){
                    error_msg+=" district,";
                    valid =false;
                }
                if(isNullOrEmpty(citys)){
                    error_msg+=" city,";
                    valid =false;
                }
                if(isNullOrEmpty(addresss)){
                    error_msg+=" address,";
                    valid =false;
                }
                if(isNullOrEmpty(mobiles)){
                    error_msg+=" mobile number,";
                    valid =false;
                }
                if(isNullOrEmpty(emails) || !isValidEmail(emails)){
                    error_msg+=" email,";
                    valid =false;
                }
                if (!valid){
                    Snackbar.make(v,error_msg,Snackbar.LENGTH_SHORT).show();
                    error_msg="invalid";
                }
                if(valid){
                    saveData(new UserProfile(firstNames,lastNames,districts,citys,addresss,emails,mobiles));
                }

            }
        });
    }

    public void saveData(UserProfile user){

        SharedPreferences sharedpref = getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedpref.edit();
        gsonInstance = new Gson();
        String json = gsonInstance.toJson(user);
        editor.putString("user",json);
        editor.apply();
        Snackbar.make(v,"User profile saved",Snackbar.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preference",Context.MODE_PRIVATE);
        gsonInstance = new Gson();
        String json = sharedPreferences.getString("user",null);
        Type type = new TypeToken<UserProfile>(){}.getType();
        user = gsonInstance.fromJson(json,type);
        if(user != null){
            firstName.setText(user.getFisrtName());
            lastName.setText(user.getLastName());
            district.setText(user.getDistrict());
            city.setText(user.getCity());
            address.setText(user.getStreetAddress());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
        }else {
            user = new UserProfile();
        }
    }

    public boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }
}
