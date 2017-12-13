package com.infinitymegamall.infinity.model;

/**
 * Created by Jannat Mostafiz on 12/6/2017.
 */

public class HomeCategory {
    String categoryName;
    int  categoryImage;
    int id;

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public HomeCategory(String categoryName, int categoryImage, int id) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.id = id;
    }

    public HomeCategory() {
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
