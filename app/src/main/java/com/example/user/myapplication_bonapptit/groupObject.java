package com.example.user.myapplication_bonapptit;

import java.io.Serializable;
import java.util.ArrayList;

public class groupObject implements Serializable{
    private String groupId;

    private ArrayList<UserObject> userObjectArrayList = new ArrayList<>();

    public groupObject(String groupId){
        this.groupId=groupId;
    }

    public String getgroupId() {
        return groupId;
    }
    public ArrayList<UserObject> getUserObjectArrayList() {
        return userObjectArrayList;
    }




    public void addUserToArrayList(UserObject mUser){
        userObjectArrayList.add(mUser);
    }
}
