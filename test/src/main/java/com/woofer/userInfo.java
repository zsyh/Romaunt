package com.woofer;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/26.
 */
public class userInfo extends Application{
    public static  String username = "";
    public static  String key = "";
    public static  String signature = "点此登录，享受私人定制的阅读服务";
    public static  String a = "";
    public static  int flag = 0;

    public static int nowPage=1;

    /*登录状态*/
    public static int status = 0;


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                return null;
            }
        }).build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
