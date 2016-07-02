package com.woofer.refreshlayout.model;


public class RefreshModel {
    public String title;
    public int userID;

    public String detail;
    public String authorname;
    public String sign;
    public String id;

    public RefreshModel() {
    }


    public RefreshModel(String title, String detail, String authorname, String sign, int userID,String id) {
        this.title = title;
        this.detail = detail;
        this.authorname = authorname;
        this.sign = sign;
        this.userID = userID;
        this.id=id;
    }
}