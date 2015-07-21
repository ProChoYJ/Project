package com.woowahan.intern.internproject.menu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 7..
 */
public class menu {


    @SerializedName("parent")
    private String Parent;

    @SerializedName("childList")
    private ArrayList<Child> Childs;

    public menu(String parent, ArrayList<Child> childs) {

        this.Parent = parent;
        Childs = childs;
    }


    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        this.Parent = parent;
    }

    public ArrayList<Child> getChilds() {
        return Childs;
    }

    public void setChilds(ArrayList<Child> childs) {
        Childs = childs;
    }
}
