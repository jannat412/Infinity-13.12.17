package com.infinitymegamall.infinity.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by shuvo on 13-Jan-18.
 */

public class ParentCategory extends ExpandableGroup{

    public ParentCategory(String title, List items) {
        super(title, items);
    }
}