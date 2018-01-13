package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ChildCategory {
    String category;
    int id;

    public ChildCategory(String category, int id) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
