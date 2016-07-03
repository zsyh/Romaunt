package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class GetStoryResponse {

    public String status;
    public Msg msg;
    public static class Msg{
        public Story story;
        public int likeCount;
    }
    public static class Story{
        public String id;
        public String title;
        public String flags;
        public String content;
        public int publicEnable;
        public String createdAt;
        public String updatedAt;
        public int AuthorID;
        public List<users> Users;
        public boolean isOwn;

    }
    public static class users{

    }
}
