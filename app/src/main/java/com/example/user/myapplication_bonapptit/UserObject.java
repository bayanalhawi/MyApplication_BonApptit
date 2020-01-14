package com.example.user.myapplication_bonapptit;

import java.io.Serializable;

public class UserObject implements Serializable {

    private String  uid,
            name,
            phone;

    private Boolean selected = false;

    public UserObject(String uid){
        this.uid = uid;
    }

    public UserObject(String uid, String name, String phone){
        this.uid = uid;
        this.name = name;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
