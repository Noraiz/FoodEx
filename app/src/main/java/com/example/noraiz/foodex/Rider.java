package com.example.noraiz.foodex;

/**
 * Created by Noraiz on 4/14/2018.
 */

public class Rider {

    private int riderId= 01;
    private double latitude=1;
    private double longitude=1;

    public  Rider(){

    }
    public Rider( int riderId,double latitude, double longitude) {
        this.riderId=riderId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getRider_id() {
        return riderId;
    }

    public void setRider_id(int rider_id) {
        this.riderId = rider_id;
    }

    public double getlatitude() {
        return latitude;
    }

    public void setlatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }
}
