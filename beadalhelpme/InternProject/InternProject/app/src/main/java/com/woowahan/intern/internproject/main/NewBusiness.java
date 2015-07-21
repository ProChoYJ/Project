package com.woowahan.intern.internproject.main;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2015. 6. 9..
 */
public class NewBusiness {

    @SerializedName("Shop_Nm")
    private String Name;

    @SerializedName("Shop_No")
    private int Id;

    public NewBusiness(String name, int id) {
        Name = name;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
