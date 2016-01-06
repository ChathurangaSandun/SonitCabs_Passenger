package com.example.chathuranga_pamba.sonitcabs_passenger.models;

/**
 * Created by pamba on 10/18/2015.
 */
public class JobDetail {


    private int jobId;
    private String plateNumber;
    private double latitude;
    private double longtiitude;
    private String firstName;
    private String lastName;
    private int telephoneNumber;


    public JobDetail() {
    }

    public JobDetail(int jobId, String plateNumber, double latitude, double longtiitude, String firstName, String lastName, int telephoneNumber) {
        this.jobId = jobId;
        this.plateNumber = plateNumber;
        this.latitude = latitude;
        this.longtiitude = longtiitude;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtiitude() {
        return longtiitude;
    }

    public void setLongtiitude(double longtiitude) {
        this.longtiitude = longtiitude;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(int telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
