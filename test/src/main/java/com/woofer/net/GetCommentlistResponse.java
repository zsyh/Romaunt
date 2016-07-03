package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 */
public class GetCommentlistResponse {
    public String status;
    public Msg msg;
    public static class Msg{
        public List<Comment> comment;
    }
    public static class Comment{
        public int id;
        public String content;
        public String createdAt;
        public String updatedAt;
        public String RevCommentId;
        public int UserId;
        public String StoryId;
        public List<revComment> RevComment;

    }
    public static class revComment{
        public int id;
        public String content;
        public String createdAt;
        public String updatedAt;
        public String RevCommentId;
        public int UserId;
        public String StoryId;
    }
}
