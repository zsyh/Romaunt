package com.woofer.net;

/**
 * Created by Administrator on 2016/6/27.
 */
public class UploadStoryResponse {
    public String status;
    public Msg msg;

    public static class Msg{
        public String id;
        public String h5Url;

    }
}
