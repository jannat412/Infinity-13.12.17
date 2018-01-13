package com.infinitymegamall.infinity.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ParentCategoryVH extends GroupViewHolder {

    private TextView title;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ParentCategoryVH(View itemView) {
        super(itemView);
        title=(TextView)itemView.findViewById(R.id.cat_list_parent);
    }
}
