package com.woowahan.intern.internproject.review;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 8..
 */
public class Reviews {

    @SerializedName("Avg_Grade")
    private float Avg_Grade;

    @SerializedName("reviewList")
    private ArrayList<Review> reviewList;

    public float getAvg_Grade() {
        return Avg_Grade;
    }

    public void setAvg_Grade(float avg_Grade) {
        Avg_Grade = avg_Grade;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
