package com.woofer.privateLetter;

/**
 * Created by YOURFATHERME on 2016/7/10.
 */
public class ChatListInfo {
    private int list_img;
    private String list_title;
    private String list_message;
    private String list_time;


    public ChatListInfo(int list_img, String list_title,String list_message, String list_time) {
        this.list_img = list_img;
        this.list_title = list_title;
        this.list_message=list_message;
        this.list_time = list_time;
    }



    public int getList_img() {
        return list_img;
    }

    public String getList_title() {
        return list_title;
    }

    public String getList_message() {
        return list_message;
    }

    public String getList_time() {
        return list_time;
    }

}
