
package com.infinitymegamall.infinity;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem implements Serializable, Parcelable
{

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("variation_id")
    @Expose
    private Integer variationId;
    public final static Parcelable.Creator<LineItem> CREATOR = new Creator<LineItem>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LineItem createFromParcel(Parcel in) {
            return new LineItem(in);
        }

        public LineItem[] newArray(int size) {
            return (new LineItem[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2959192182010650556L;

    protected LineItem(Parcel in) {
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.variationId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public LineItem() {
    }

    /**
     * 
     * @param variationId
     * @param quantity
     * @param productId
     */
    public LineItem(Integer productId, Integer quantity, Integer variationId) {
        super();
        this.productId = productId;
        this.quantity = quantity;
        this.variationId = variationId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(quantity);
        dest.writeValue(variationId);
    }

    public int describeContents() {
        return  0;
    }

}
