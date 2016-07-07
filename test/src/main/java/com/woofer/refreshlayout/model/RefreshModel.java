package com.woofer.refreshlayout.model;


import android.graphics.Bitmap;

public class RefreshModel {
    public String title;
    public int userID;

    public String detail;
    public String authorname;
    public String sign;
    public String avater;
    public String id;

    public String time;
    public String lable;
    public Bitmap bitmap;

    public RefreshModel() {
    }


    public RefreshModel(String title, String detail, String authorname, String sign, int userID,String id,String avater,String time ,String lable) {
        this.title = title;
        this.detail = detail;
        this.authorname = authorname;
        this.sign = sign;
        this.userID = userID;
        this.id=id;
        this.avater = avater;
        this.time = time;
        this.lable = lable;

    }
}