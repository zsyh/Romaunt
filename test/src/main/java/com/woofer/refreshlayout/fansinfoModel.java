package com.woofer.refreshlayout;


public class fansinfoModel {
    public int userID;
    public String username;
    public String sign;
    public int sex;
    public String avater;
    public fansinfoModel() {
    }


    public fansinfoModel(int userID , String username ,String sign ,int sex , String avater) {
        this.userID = userID;
        this.username = username;
        this.sign = sign;
        this.sex = sex;
        this.avater = avater;
    }
}