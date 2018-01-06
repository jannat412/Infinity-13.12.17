
package com.infinitymegamall.infinity.pojo;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infinitymegamall.infinity.Billing;
import com.infinitymegamall.infinity.LineItem;
import com.infinitymegamall.infinity.Shipping;
import com.infinitymegamall.infinity.ShippingLine;

public class Pojo implements Serializable, Parcelable
{

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("set_paid")
    @Expose
    private Boolean setPaid;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<ShippingLine> shippingLines = null;
    public final static Parcelable.Creator<Pojo> CREATOR = new Creator<Pojo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Pojo createFromParcel(Parcel in) {
            return new Pojo(in);
        }

        public Pojo[] newArray(int size) {
            return (new Pojo[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8105672298563691179L;

    protected Pojo(Parcel in) {
        this.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentMethodTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.setPaid = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.billing = ((Billing) in.readValue((Billing.class.getClassLoader())));
        this.shipping = ((Shipping) in.readValue((Shipping.class.getClassLoader())));
        in.readList(this.lineItems, (com.infinitymegamall.infinity.LineItem.class.getClassLoader()));
        in.readList(this.shippingLines, (com.infinitymegamall.infinity.ShippingLine.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Pojo() {
    }

    /**
     * 
     * @param shipping
     * @param billing
     * @param shippingLines
     * @param paymentMethodTitle
     * @param setPaid
     * @param lineItems
     * @param paymentMethod
     */
    public Pojo(String paymentMethod, String paymentMethodTitle, Boolean setPaid, Billing billing, Shipping shipping, List<LineItem> lineItems, List<ShippingLine> shippingLines) {
        super();
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.setPaid = setPaid;
        this.billing = billing;
        this.shipping = shipping;
        this.lineItems = lineItems;
        this.shippingLines = shippingLines;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public Boolean getSetPaid() {
        return setPaid;
    }

    public void setSetPaid(Boolean setPaid) {
        this.setPaid = setPaid;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(paymentMethod);
        dest.writeValue(paymentMethodTitle);
        dest.writeValue(setPaid);
        dest.writeValue(billing);
        dest.writeValue(shipping);
        dest.writeList(lineItems);
        dest.writeList(shippingLines);
    }

    public int describeContents() {
        return  0;
    }

}
