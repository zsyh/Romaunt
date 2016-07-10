package com.woofer.commentandreply.date;

/**
 * Created by Admin on 2016/5/27.
 */
public class Replydate {
    public int id;					//内容ID
    public int replyAccount;	//回复人id
    public String replyNickname;	//回复人昵称
    public String commentAccount;	//被回复人id
    public String commentNickname;	//被回复人昵称
    public String replyContent;	//回复的内容
    public Replydate(){

    }

    public Replydate(int id, int replyAccount, String replyNickname, String commentAccount, String commentNickname, String replyContent){
        this.id = id;
        this.replyAccount = replyAccount;
        this.replyNickname = replyNickname;
        this.commentAccount = commentAccount;
        this.commentNickname = commentNickname;
        this.replyContent = replyContent;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReplyAccount() {
        return replyAccount;
    }
    public void setReplyAccount(int replyAccount) {
        this.replyAccount = replyAccount;
    }
    public String getReplyNickname() {
        return replyNickname;
    }
    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }
    public String getCommentAccount() {
        return commentAccount;
    }
    public void setCommentAccount(String commentAccount) {
        this.commentAccount = commentAccount;
    }
    public String getCommentNickname() {
        return commentNickname;
    }
    public void setCommentNickname(String commentNickname) {
        this.commentNickname = commentNickname;
    }
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
