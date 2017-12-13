package com.infinitymegamall.infinity.model;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class NewArrival {
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
}
