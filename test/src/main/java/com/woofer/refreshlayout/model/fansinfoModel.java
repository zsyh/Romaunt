package com.woofer.refreshlayout.model;


public class FansinfoModel {
    public int userID;
    public String username;
    public String sign;
    public int sex;
    public String avater;
    public FansinfoModel() {
    }


    public FansinfoModel(int userID , String username , String sign , int sex , String avater) {
        this.userID = userID;
        this.username = username;
        this.sign = sign;
        this.sex = sex;
        this.avater = avater;
    }
}