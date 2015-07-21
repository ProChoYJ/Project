package com.woowahan.intern.internproject.business;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 9..
 */
public class BusinessList {

    @SerializedName("List")
    ArrayList<Business> businessList;

    public BusinessList(ArrayList<Business> businessList) {
        this.businessList = businessList;
    }

    public ArrayList<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(ArrayList<Business> businessList) {
        this.businessList = businessList;
    }
}
