package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */
public class PublicStoryListResponse {
    public String status;
    public List<Msg> msg;


    public static class Msg{
        public String id;
        public String title;
        public String flags;
        public String content;
        public int publicEnable;
        public String createdAt;
        public String updatedAt;
        public int AuthorID;
        public List<users> Users;
        public int likeCount;
    }

    public static class users{

    }

}
