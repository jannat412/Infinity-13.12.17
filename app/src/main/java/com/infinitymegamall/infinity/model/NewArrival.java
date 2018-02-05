package com.infinitymegamall.infinity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class NewArrival implements Parcelable{
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewArrival)) return false;

        NewArrival that = (NewArrival) o;

        if (getId() != that.getId()) return false;
        return getNewArrival().equals(that.getNewArrival());
    }

    @Override
    public int hashCode() {
        int result = getNewArrival().hashCode();
        result = 31 * result + getId();
        return result;
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

    public static Comparator<NewArrival> namecomparator = new Comparator<NewArrival>(){
        public int compare(NewArrival s1,NewArrival s2){
            String d1 = s1.getNewArrival().toUpperCase();
            String d2 = s2.getNewArrival().toUpperCase();
            return d1.compareTo(d2);
        }
    };

    @Override
    public String toString() {
        return "NewArrival{" +
                "newArrival='" + newArrival + '\'' +
                ", id=" + id +
                '}';
    }
}