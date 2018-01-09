package com.infinitymegamall.infinity.model;

/**
 * Created by shuvo on 09-Jan-18.
 */

public class UserProfile {

    String fisrtName;
    String lastName;
    String district;
    String city;
    String streetAddress;
    String email;
    String mobile;

    public UserProfile() {
    }

    public UserProfile(String fisrtName, String lastName, String district, String city, String streetAddress, String email, String mobile) {
        this.fisrtName = fisrtName;
        this.lastName = lastName;
        this.district = district;
        this.city = city;
        this.streetAddress = streetAddress;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
