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
        this.Product_price = product_price;
        this.Product_image = product_image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product_details)) return false;

        Product_details that = (Product_details) o;

        if (getId() != that.getId()) return false;
        if (!getProduct_name().equals(that.getProduct_name())) return false;
        if (!getProduct_price().equals(that.getProduct_price())) return false;
        return getProduct_image().equals(that.getProduct_image());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getProduct_name().hashCode();
        result = 31 * result + getProduct_price().hashCode();
        result = 31 * result + getProduct_image().hashCode();
        return result;
    }
}

