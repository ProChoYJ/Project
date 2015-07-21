package com.woowahan.intern.internproject.menu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 8..
 */
public class Menus {

    @SerializedName("menuList")
    ArrayList<menu> Menus;

    public Menus(ArrayList<menu> menus) {
        Menus = menus;
    }

    public ArrayList<menu> getMenus() {
        return Menus;
    }

    public void setMenus(ArrayList<menu> menus) {
        Menus = menus;
    }
}
