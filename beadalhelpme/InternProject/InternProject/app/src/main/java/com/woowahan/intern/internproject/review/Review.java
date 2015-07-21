package com.woowahan.intern.internproject.review;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 8..
 */
public class Review {

    @SerializedName("Reg_Date")
    private String Date;

    @SerializedName("Score")
    private String Score;

    @SerializedName("Nick_Nm")
    private String Name;

    @SerializedName("Cmnt")
    private String content;

    public Review(String date, String score, String name, String content) {
        Date = date;
        Score = score;
        Name = name;
        this.content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
