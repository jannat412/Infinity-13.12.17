package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 19-Dec-17.
 */

public class Product_details {
    int id;
    String product_name;
    String Product_price;
    String Product_image;

    public Product_details() {
    }

    public Product_details(int id, String product_name, String product_price, String product_image) {
        this.id = id;
        this.product_name = product_name;
        Product_price = product_price;
        Product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public String getProduct_image() {
        return Product_image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }

    public void setProduct_image(String product_image) {
        Product_image = product_image;
    }
}

