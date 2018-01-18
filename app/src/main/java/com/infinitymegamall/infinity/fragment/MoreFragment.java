package com.infinitymegamall.infinity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infinitymegamall.infinity.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by shuvo on 18-Jan-18.
 */

public class MoreFragment extends Fragment {

    String msg ="Cash On Delivery\n" +
            "You can avail cash on delivery facilities within the purchase amount of BDT 5,000. Since we provide cash on delivery only for perishable goods and it is applicable only for Dhaka, Chittagong & Sylhet city area." +
            "Return Policy?\n" +
            "\n" +
            "Customer satisfaction is our utmost priority, at all times. Customers can return their products bought online from iNFINITYmegamall.com if they want. All they need to do is to raise a request for return. Customers can initiate return within a period of 5 days from delivery date. Customers can reach us on our customer care helpline 01760-606089, our team will take things forward on the receipt of Return Request submitted on infinitymegamall.com, at the earliest.\n" +
            "\n" +
            "As per the company’s policies, the refund would be issued only if the products are returned, with the tags and packaging intact, in an unwashed and undamaged condition. The refund/replacement would be issued to customers in form of Gift Voucher of equivalent amount, after thorough quality check of the product(s) by our team.\n" +
            "\n" +
            "For hygienic purposes, our returns policy does not apply to inner wear, fragrances, beauty products, jewellery, socks, & any other accessories. Please keep in mind that, in special circumstances, the rules of promotional offers may override our regular returns policy. Shipping charges and convenience charges will not be refunded, as they are non-refundable.\n" +
            "\n" +
            " \n" +
            "\n" +
            "Return Procedure\n" +
            "\n" +
            "Customers who bought products from iNFINITYmegamall.com can return the product within 5 days of products delivered if they didn’t like it. However, if purchase was made in the period of a special promotional offer, contests or any special scheme, then special rules might apply for returns.\n" +
            " \n" +
            "\n" +
            "Customer needs to call iNFINITY Mega Mall customer care no (01760-606089) between 10 am to 8 pm on weekdays, except Friday .\n" +
            " \n" +
            "\n" +
            "Customer will be contacted by iNFINITY Mega Mall customer care team at the earliest from the date of return request by customers & initiate it.\n" +
            " \n" +
            "\n" +
            "As per the company’s policies, the refund would be issued only if the products are returned, with the tags and packaging intact, in an unwashed and undamaged condition. The refund/replacement would be issued to you, after thorough quality check of the product(s) by our team.\n" +
            " \n" +
            "\n" +
            "The purchase amount will be refunded in form of iNFINITY Mega Mall gift voucher of the same amount as paid by customers to buy the products online or from any iNFINITY Mega Mall Stores directly, however the convenience charges,& courier charges will not be refunded.\n" +
            " \n" +
            "\n" +
            "Customers may return a part of their order, in case of multiple products in your order. However, on account of the product being returned, all components of the product as well as complimentary gifts or products that came along with the original product, must be returned.\n" +
            " \n" +
            "\n" +
            "Customers can redeem the gift vouchers received in lieu of their return products online or at any iNFINITY Mega Mall stores in Bangladesh.\n" +
            " \n" +
            "\n" +
            "Customers will not be charged for courier charges for the products returned.";
    public MoreFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmrnt_more, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ExpandableTextView expTv1 = (ExpandableTextView) getActivity()
                .findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(msg);
    }
}
