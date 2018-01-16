package com.infinitymegamall.infinity.model;

import android.os.Parcel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ParentCategory  {
    private String id;
    private String name;
    private ArrayList<ChildCategory> list = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChildCategory> getList() {
        return list;
    }

    public void setList(ArrayList<ChildCategory> list) {
        this.list = list;
    }
}
