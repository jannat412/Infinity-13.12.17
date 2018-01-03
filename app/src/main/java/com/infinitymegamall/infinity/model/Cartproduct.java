package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 03-Jan-18.
 */

public class Cartproduct {
    String productId;
    String productName;
    String productImage;
    String productPrie;
    String productSize;
    String productQuantity;

    public Cartproduct(String productId, String productName, String productImage, String productPrie, String productSize, String productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrie = productPrie;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrie() {
        return productPrie;
    }

    public void setProductPrie(String productPrie) {
        this.productPrie = productPrie;
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
