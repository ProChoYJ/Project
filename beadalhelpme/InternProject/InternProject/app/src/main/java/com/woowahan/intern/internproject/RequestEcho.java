package com.woowahan.intern.internproject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 14..
 */
public class RequestEcho {

    @SerializedName("Nick_Nm")
    private String reviewName;

    @SerializedName("Shop_No")
    private String reviewId;

    @SerializedName("Cmnt")
    private String reviewContent;

    @SerializedName("Score")
    private String reviewGrade;

    @SerializedName("Reg_Date")
    private String date;

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewGrade() {
        return reviewGrade;
    }

    public void setReviewGrade(String reviewGrade) {
        this.reviewGrade = reviewGrade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
