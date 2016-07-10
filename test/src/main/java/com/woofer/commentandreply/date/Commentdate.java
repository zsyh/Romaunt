package com.woofer.commentandreply.date;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/5/27.
 */
public class Commentdate {
    public int positon;					    //评论记录ID
    public int commnetAccount;	       //评论人账号
    public String commentNickname;	   //评论人昵称
    public String commentTime;		   //评论时间
    public String commentContent;	   //评论内容
    public String sign;
    public String avatar;               //获取头像
    public int storyid;
    public Commentdate (){


    }

    public Commentdate (int positon,int commnetAccount, String commentNickname, String commentTime, String commentContent, String sign, String avatar,int storyid){
        this.positon = positon;
        this.commnetAccount = commnetAccount;
        this.commentNickname = commentNickname;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.sign = sign;
        this.storyid = storyid;

        this.avatar = avatar;
    }
    public int getStoryid(){
        return storyid;
    }
    public  void setStoryid(int storyid){
        this.storyid = storyid;
    }


    private List<Replydate> replyList = new ArrayList<Replydate>();
    //回复内容列表
    public int getPositon() {
        return positon;
    }
    public void setId(int positon) {
        this.positon = positon;
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
