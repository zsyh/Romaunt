package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PersonStoryListResponse {
    public String status;
    public Msg msg;


    public static class Msg{
        public User user;
        public List<Stories> stories;
        public int likeCount;
    }

    public static class User{
        public int id;
        public String mobile;
        public String avatar;
        public String sign;
        public String userName;
        public String token;
        public int sex;
        public int noticeEnable;
        public int followingEnable;
        public int followerEnable;
        public int aboutNotice;
        public int updateNotice;
    }
    public static class Stories{
        public String id;
        public String title;
        public String flags;
        public String content;
        public int publicEnable;
        public String createdAt;
        public String updatedAt;
        public int AuthorID;

    }
}
