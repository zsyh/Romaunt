package com.woofer.commentandreply.date;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/5/27.
 */
public class Commentdate {
    private int id;					//评论记录ID
    private int commnetAccount;	//评论人账号
    private String commentNickname;	//评论人昵称
    private String commentTime;		//评论时间
    private String commentContent;	//评论内容
    private String sign;
    private String avatar;              //获取头像
    public Commentdate (){

    }

    public Commentdate (int id,int commnetAccount, String commentNickname, String commentTime, String commentContent, String sign, String avatar){
        this.id = id;
        this.commnetAccount = commnetAccount;
        this.commentNickname = commentNickname;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.sign = sign;

        this.avatar = avatar;
    }
    public String getSign() {
        return sign;
    }

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
            this.avatar = avatar;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private List<Replydate> replyList = new ArrayList<Replydate>();
    //回复内容列表
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCommnetAccount() {
        return commnetAccount;
    }
    public void setCommnetAccount(int commnetAccount) {
        this.commnetAccount = commnetAccount;
    }
    public String getCommentNickname() {
        return commentNickname;
    }
    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }
    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public List<Replydate> getReplyList() {
        return replyList;
    }
    public void setReplyList(List<Replydate> replyList) {
        this.replyList = replyList;
    }

}
