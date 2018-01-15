package com.infinitymegamall.infinity.ViewHolders;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.infinitymegamall.infinity.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ChildCategoryVH extends CheckableChildViewHolder {

    CheckedTextView title;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ChildCategoryVH(View itemView) {
        super(itemView);
        title = (CheckedTextView)itemView.findViewById(R.id.cat_list_child);
    }

    @Override
    public Checkable getCheckable() {
        return title;
    }
}
