
package com.infinitymegamall.infinity;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingLine implements Serializable, Parcelable
{

    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("total")
    @Expose
    private Integer total;
    public final static Parcelable.Creator<ShippingLine> CREATOR = new Creator<ShippingLine>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ShippingLine createFromParcel(Parcel in) {
            return new ShippingLine(in);
        }

        public ShippingLine[] newArray(int size) {
            return (new ShippingLine[size]);
        }

    }
    ;
    private final static long serialVersionUID = -994767448933001690L;

    protected ShippingLine(Parcel in) {
        this.methodId = ((String) in.readValue((String.class.getClassLoader())));
        this.methodTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShippingLine() {
    }

    /**
     * 
     * @param total
     * @param methodTitle
     * @param methodId
     */
    public ShippingLine(String methodId, String methodTitle, Integer total) {
        super();
        this.methodId = methodId;
        this.methodTitle = methodTitle;
        this.total = total;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(methodId);
        dest.writeValue(methodTitle);
        dest.writeValue(total);
    }

    public int describeContents() {
        return  0;
    }

}
