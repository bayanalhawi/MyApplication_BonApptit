package com.example.user.myapplication_bonapptit;

public class users {

    private String username,phonenumber,health_status;

     public users() {
    }

    public users(String username, String phonenumber, String health_status) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.health_status = health_status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

}

