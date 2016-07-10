package com.woofer.commentandreply.date;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2016/5/27.
 */
public class Commentdate {
    public int positon;					    //记录listview中位置
    public int commnetAccount;	       //评论人账号
    public String commentNickname;	   //评论人昵称
    public String commentTime;		   //评论时间
    public String commentContent;	   //评论内容
    public String sign;
    public String avatar;               //获取头像
    public int commentid;                 //评论id

    /**用来检索评论下的回复个数 目前好像用不到 因为发表回复的时候不能指定人
     * 只能回复发表达当前评论的人
     * */
    public int replynum;

    public List<Replydate> replyList = new ArrayList<Replydate>();
    //回复内容列表
    public Commentdate (){


    }

    public Commentdate (int positon,int commnetAccount, String commentNickname, String commentTime, String commentContent, String sign, String avatar,int commentid){
        this.positon = positon;
        this.commnetAccount = commnetAccount;
        this.commentNickname = commentNickname;
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.sign = sign;
        this.commentid = commentid;

        this.avatar = avatar;
    }
    public int getStoryid(){
        return commentid;
    }
    public  void setStoryid(int commentid){
        this.commentid = commentid;
    }


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
