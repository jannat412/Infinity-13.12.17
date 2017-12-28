package com.infinitymegamall.infinity.model;

/**
 * Created by Jannat Mostafiz on 12/27/2017.
 */

public class Gallery {
    int  galleryImage;
    int id;

    public Gallery(int galleryImage, int id) {
        this.galleryImage = galleryImage;
        this.id = id;
    }

    public Gallery() {
    }

    public int getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(int galleryImage) {
        this.galleryImage = galleryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
