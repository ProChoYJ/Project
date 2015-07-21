package com.woowahan.intern.internproject.main;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 7..
 */
public class Recommend {

    @SerializedName("Ct_Nm")
    private String Name;

    @SerializedName("Ct_Cd")
    private int category;

    public Recommend(String name, String img, int category) {
        Name = name;
//        Img = img;
        this.category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
