package com.infinitymegamall.infinity.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ChildCategoryVH extends ChildViewHolder {

    TextView title;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ChildCategoryVH(View itemView) {
        super(itemView);
        title = (TextView)itemView.findViewById(R.id.cat_list_child);
    }
}
