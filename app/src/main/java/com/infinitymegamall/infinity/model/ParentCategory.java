package com.infinitymegamall.infinity.model;

import android.os.Parcel;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ParentCategory  {
    private String id;
    private String name;
    private ArrayList<ChildCategory> list = new ArrayList();

    public ParentCategory() {
    }

    public ParentCategory(String id, String name, ArrayList<ChildCategory> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

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

    public static Comparator<ParentCategory> categoryComparator = new Comparator<ParentCategory>(){
        public int compare(ParentCategory s1,ParentCategory s2){
            String d1 = s1.getName().toUpperCase();
            String d2 = s2.getName().toUpperCase();
            return d1.compareTo(d2);
        }
    };

}
