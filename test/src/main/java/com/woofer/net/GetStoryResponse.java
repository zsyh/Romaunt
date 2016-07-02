package com.woofer.net;

/**
 * Created by Administrator on 2016/6/27.
 */
public class GetStoryResponse {

    public String status;
    public Msg msg;
    public static class Msg{
        public Story story;

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
        public int likeCount;
        public boolean isOwn;

    }
}
