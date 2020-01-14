package com.example.user.myapplication_bonapptit;

public class items {

    private String item_ID;
    private String item_Name;
    private String item_Description;
    private String item_Price;
    private String item_RestaurantName;
    private String item_HealthStatus;
    private String item_HealthStatus2;
    private String item_Time;
    private String item_Image;

    public items() {
    }

    public items(String item_ID, String item_Name, String item_Description, String item_Price, String item_RestaurantName, String item_HealthStatus, String item_HealthStatus2, String item_Time, String item_Image) {
        this.item_ID = item_ID;
        this.item_Name = item_Name;
        this.item_Description = item_Description;
        this.item_Price = item_Price;
        this.item_RestaurantName = item_RestaurantName;
        this.item_HealthStatus = item_HealthStatus;
        this.item_HealthStatus2 = item_HealthStatus2;
        this.item_Time = item_Time;
        this.item_Image = item_Image;
    }

    public String getItem_ID() {
        return item_ID;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public String getItem_Description() {
        return item_Description;
    }

    public String getItem_Price() {
        return item_Price;
    }

    public String getItem_RestaurantName() {
        return item_RestaurantName;
    }

    public String getItem_HealthStatus() {
        return item_HealthStatus;
    }

    public String getItem_HealthStatus2() {
        return item_HealthStatus2;
    }

    public String getItem_Time() {
        return item_Time;
    }

    public String getItem_Image() {
        return item_Image;
    }
}
