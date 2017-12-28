package com.infinitymegamall.infinity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class NewArrival implements Parcelable {
    String newArrival;
    int id;

    public NewArrival(String newArrival, int id) {
        this.newArrival = newArrival;
        this.id = id;
    }
    public NewArrival(){}

    public String getNewArrival() {
        return newArrival;
    }

    public void setNewArrival(String newArrival) {
        this.newArrival = newArrival;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected NewArrival(Parcel in) {
        newArrival = in.readString();
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newArrival);
        dest.writeInt(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NewArrival> CREATOR = new Parcelable.Creator<NewArrival>() {
        @Override
        public NewArrival createFromParcel(Parcel in) {
            return new NewArrival(in);
        }

        @Override
        public NewArrival[] newArray(int size) {
            return new NewArrival[size];
        }
    };
}