package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class UserInfoResponse {
    public String status;
    public Msg msg;



    public static class Msg{
        public User user;
        public List<Follower> follower;
        public List<Following> following;
        public int collectedStoriesCount;
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
    public static class follow{
        public String createdAt;
        public String updatedAt;
        public int followerID;
        public int StarID;
    }

    public static class Follower{
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
        public follow Follow;
    }

    public static class Following{
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
        public follow Follow;

    }
}
