package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 03-Jan-18.
 */

public class Cart {
    String productId;
    String productSize;
    String productQuantity;

    public Cart(String productId, String productSize, String productQuantity) {
        this.productId = productId;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
