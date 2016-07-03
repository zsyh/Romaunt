package com.woofer.net;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3.
 */
public class GetChatResponse {
    public String status;
    public Msg msg;
    public static class Msg{
        public List<Msgs> msgs;
    }
    public static class Msgs{
        public int id;
        public String updatedAt;
        public String createdAt;
        public int sendID;
        public int revID;
        public String content;

    }
}
