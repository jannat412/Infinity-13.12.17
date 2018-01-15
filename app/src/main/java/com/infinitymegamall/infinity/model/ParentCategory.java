package com.infinitymegamall.infinity.model;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ParentCategory extends SingleCheckExpandableGroup {


    public ParentCategory(String title, List items) {
        super(title, items);

    }

}
