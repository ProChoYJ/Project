package com.woowahan.intern.internproject.imformation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 9..
 */
public class BusinessInfomation {

    @SerializedName("Addr")
    private String Address;

    @SerializedName("Sales_Tm_B")
    private String BusinessHour;

    @SerializedName("Sales_Tm_E")
    private String BusinessHourE;

    @SerializedName("Open_Dt")
    private String OpenDate;

    @SerializedName("Tel_No")
    private String Phone;

    @SerializedName("Ord_Cnt")
    private int OrderCount;

    @SerializedName("Shop_Info")
    private String comment;

    public BusinessInfomation(String address, String businessHour, String openDate, String Phone, int orderCount, String comment) {
        Address = address;
        BusinessHour = businessHour;
        OpenDate = openDate;
        this.Phone = Phone;
        OrderCount = orderCount;
        this.comment = comment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBusinessHour() {
        return BusinessHour;
    }

    public void setBusinessHour(String businessHour) {
        BusinessHour = businessHour;
    }

    public String getOpenDate() {
        return OpenDate;
    }

    public void setOpenDate(String openDate) {
        OpenDate = openDate;
    }

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int orderCount) {
        OrderCount = orderCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBusinessHourE() {
        return BusinessHourE;
    }

    public void setBusinessHourE(String businessHourE) {
        BusinessHourE = businessHourE;
    }
}
