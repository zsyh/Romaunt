package com.woofer.net;

/**
 * Created by Administrator on 2016/6/26.
 */
public class LoginResponse {
    public String status;
    public Msg msg;

    public static class Msg{

        public String userID;
        public String LoginToken;
        public String token;

    }


}
