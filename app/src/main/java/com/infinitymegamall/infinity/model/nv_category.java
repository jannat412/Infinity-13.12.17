package com.infinitymegamall.infinity.model;

/**
 * Created by Jannat Mostafiz on 12/5/2017.
 */

public class nv_category {
    String categoryName;
    int id;

    public nv_category(String categoryName, int id) {
        this.categoryName = categoryName;
        this.id = id;
    }

    public nv_category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
