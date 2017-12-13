package com.infinitymegamall.infinity.model;

/**
 * Created by Jannat Mostafiz on 12/9/2017.
 */

public class Exclusive {
    String exclusiveText;
    String image;
    int id;

    public Exclusive(String exclusiveText, String image, int id) {
        this.exclusiveText = exclusiveText;
        this.image = image;
        this.id = id;
    }

    public Exclusive() {
    }

    public String getExclusiveText() {
        return exclusiveText;
    }

    public void setExclusiveText(String exclusiveText) {
        this.exclusiveText = exclusiveText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
