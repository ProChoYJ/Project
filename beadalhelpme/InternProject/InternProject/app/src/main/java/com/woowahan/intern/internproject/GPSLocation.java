package com.woowahan.intern.internproject;

import java.sql.Statement;

/**
 * Created by user on 2015. 6. 10..
 */
public class GPSLocation {


    private String Lat;
    private String Lng;
    private static GPSLocation sGPSLocation = new GPSLocation();

    private GPSLocation(){
    }

    public static GPSLocation getInstance(){
        return sGPSLocation;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
}
