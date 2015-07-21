package com.woowahan.intern.internproject.menu;

/**
 * Created by user on 2015. 6. 9..
 */
public class Parent {


    private int Group_Id;
    private String Name;

    public Parent(int group_Id, String name) {
        Group_Id = group_Id;
        Name = name;
    }

    public int getGroup_Id() {
        return Group_Id;
    }

    public void setGroup_Id(int group_Id) {
        Group_Id = group_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
