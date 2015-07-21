package com.woowahan.intern.internproject.business;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 7..
 */
public class Business{

    @SerializedName("Sales_Tm_B")
    private String start;

    @SerializedName("Sales_Tm_E")
    private String end;

    @SerializedName("status")
    private int Status;

    @SerializedName("Shop_No")
    private int Id;

    @SerializedName("Shop_Nm")
    private String Name;

    @SerializedName("Avg_Score")
    private float aScore;

    @SerializedName("Review_Cnt")
    private int reCount;

    @SerializedName("Distance")
    private float Distance;

    public Business(int status, int id, String name, float aScore, int reCount, float distance) {
        Status = status;
        Id = id;
        Name = name;
        this.aScore = aScore;
        this.reCount = reCount;
        Distance = distance;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getaScore() {
        return aScore;
    }

    public void setaScore(float aScore) {
        this.aScore = aScore;
    }

    public int getReCount() {
        return reCount;
    }

    public void setReCount(int reCount) {
        this.reCount = reCount;
    }

    public float getDistance() {
        return Distance;
    }

    public void setDistance(float distance) {
        Distance = distance;
    }


        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }


}

