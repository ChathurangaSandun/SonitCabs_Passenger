package com.example.chathuranga_pamba.sonitcabs_passenger.models;

/**
 * Created by pamba on 8/29/2015.
 */
public class User {

    private String userName;
    private String userType;
    private int customerID;

    public User() {
    }

    public User(String userName, String userType,int customerID) {
        this.userName = userName;
        this.userType = userType;
        this.customerID = customerID;
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

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
