package com.woofer.net;


/**
 * Created by Administrator on 2016/7/3.
 */
public class SendChatResponse {
    public String status;
    public Msg msg;
    public static class Msg{
        public MsgID msgID;
    }
    public static class MsgID{
        public String updatedAt;
        public String createdAt;
        public int revID;
        public int id;
        public int sendID;
        public String content;

    }
}
