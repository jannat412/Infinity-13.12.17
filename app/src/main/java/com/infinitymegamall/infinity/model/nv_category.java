package com.infinitymegamall.infinity.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jannat Mostafiz on 12/5/2017.
 */

public class nv_category implements Parcelable {
    String categoryName;
    int id;

    public nv_category(String categoryName, int id) {
        this.categoryName = categoryName;
        this.id = id;
    }

    public nv_category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    protected nv_category(Parcel in) {
        categoryName = in.readString();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryName);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<nv_category> CREATOR = new Parcelable.Creator<nv_category>() {
        @Override
        public nv_category createFromParcel(Parcel in) {
            return new nv_category(in);
        }

        @Override
        public nv_category[] newArray(int size) {
            return new nv_category[size];
        }
    };
}
