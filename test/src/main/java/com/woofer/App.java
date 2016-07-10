package com.woofer;


import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.StatusRecognize;
import com.woofer.refreshlayout.engine.Engine;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class  App extends Application{
    private  static  App sInstance;
    private Engine mEngine;
    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;

        mEngine = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);


        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                // try the request
                Response originalResponse = chain.proceed(request);

                /**通过如下的办法曲线取到请求完成的数据
                 *
                 * 原本想通过  originalResponse.body().string()
                 * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
                 *
                 * 然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
                 */
                ResponseBody responseBody = originalResponse.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = Charset.forName("UTF8");
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset();
                }

                String bodyString = buffer.clone().readString(charset);

                //Log.e("Romaunt", "Interceptor中的body:"+bodyString);

                //token已过期
                if(bodyString.indexOf("LoginToken")>=0){

                    Boolean status = StatusRecognize.getStatus(bodyString);
                    if(status==false) {

                        Log.e("Romaunt", "Interceptor判定LoginToken已过期，开始重新获取");

                        //取出本地的token
                        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);

                        String token = sp.getString("TOKEN", "");

                        if(!token.equals("")) {
                            RomauntNetWork romauntNetWork = new RomauntNetWork();
                            Object object = romauntNetWork.getTokenSync(token);

                            String newLoginToken = "";
                            String newToken = "";
                            if (object instanceof LoginResponse) {
                                LoginResponse loginResponse = (LoginResponse) object;
                                newLoginToken = loginResponse.msg.LoginToken;
                                newToken = loginResponse.msg.token;
                                Log.e("Romaunt", "Interceptor获取的新token：" + newToken);
                                Log.e("Romaunt", "Interceptor获取的新LoginToken：" + newLoginToken);
                            } else {
                                Log.e("Romaunt", "Interceptor 尝试获取新LoginToken时返回不是LoginResponse");
                                return originalResponse;//抛出status false
                            }


                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("LOGINTOKEN", newLoginToken);
                            editor.putString("TOKEN", newToken);


                            // create a new request and modify it accordingly using the new token
                            Request newRequest = request.newBuilder().header("LoginToken", newLoginToken)
                                    .build();

                            // retry the request

                            originalResponse.body().close();
                            return chain.proceed(newRequest);
                        }
                        else {//token为空
                            return originalResponse;
                        }

                    }

                }



                // otherwise just pass the original response on
                return originalResponse;
            }



        }).build();

        OkHttpUtils.initClient(okHttpClient);

    }
    public static  App getInstance(){return  sInstance;}

    public Engine getEngine(){return mEngine;}
}