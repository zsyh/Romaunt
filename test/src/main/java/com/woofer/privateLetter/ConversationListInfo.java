package com.woofer.privateLetter;

/**
 * Created by YOURFATHERME on 2016/7/10.
 */
public class ConversationListInfo {
    private int list_img;
    private String list_text;
    private Boolean isme;

    public ConversationListInfo(int list_img,String list_text,Boolean isme) {
        this.list_img = list_img;
        this.list_text = list_text;
        this.isme=isme;

    }
    public int getList_img() {
        return list_img;
    }


    public String getList_text() {
        return list_text;
    }

    public Boolean getList_isme() {
        return isme;
    }

}