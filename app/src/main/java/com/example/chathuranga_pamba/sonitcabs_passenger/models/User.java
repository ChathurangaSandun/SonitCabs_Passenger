package com.example.chathuranga_pamba.sonitcabs_passenger.models;

/**
 * Created by pamba on 8/29/2015.
 */
public class User {

    private String userName;
    private String userType;

    public User() {
    }

    public User(String userName, String userType) {
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
