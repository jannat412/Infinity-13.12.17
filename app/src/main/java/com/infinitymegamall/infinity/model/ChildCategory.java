package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ChildCategory {
    String category;
    String id;

    public ChildCategory(String id,String category ) {
        this.category = category;
        this.id = id;
    }

    public ChildCategory() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
