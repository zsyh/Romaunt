package com.woofer.refreshlayout.model;


public class ParhsModel {
    public String storyid;
    public String flags;
    public String title;
    public String time;
    public int thumbNUM;
    public String content;
    public ParhsModel() {
    }


    public ParhsModel(String flags, String title, String time, int thumbNUM, String content, String storyid) {
        this.flags = flags;
        this.title = title;
        this.time = time;
        this.thumbNUM = thumbNUM;
        this.content = content;
        this.storyid = storyid;
    }
}