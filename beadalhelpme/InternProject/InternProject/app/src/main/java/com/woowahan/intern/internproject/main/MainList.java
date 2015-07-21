package com.woowahan.intern.internproject.main;

import com.google.gson.annotations.SerializedName;
import com.woowahan.intern.internproject.business.Business;
import com.woowahan.intern.internproject.business.BusinessList;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 9..
 */
public class MainList {

    @SerializedName("Recommend")
    private Recommend recommend;

    @SerializedName("NewBusiness")
    private NewBusiness newBusiness;

    @SerializedName("List")
    private ArrayList<Business> businessList;


    public MainList(Recommend recommend, NewBusiness newBusiness, ArrayList<Business> businessList) {
        this.recommend = recommend;
        this.newBusiness = newBusiness;
        this.businessList = businessList;
    }

    public Recommend getRecommend() {
        return recommend;
    }

    public void setRecommend(Recommend recommend) {
        this.recommend = recommend;
    }

    public NewBusiness getNewBusiness() {
        return newBusiness;
    }

    public void setNewBusiness(NewBusiness newBusiness) {
        this.newBusiness = newBusiness;
    }

    public ArrayList<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(ArrayList<Business> businessList) {
        this.businessList = businessList;
    }
}
