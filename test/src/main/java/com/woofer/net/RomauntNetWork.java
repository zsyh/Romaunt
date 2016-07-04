package com.woofer.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woofer.activity.MainActivity;
import com.woofer.activity.signinActivity;
import com.woofer.userInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/25.
 */
public class RomauntNetWork {

    private RomauntNetworkCallback romauntNetworkCallback;


    public void setRomauntNetworkCallback(RomauntNetworkCallback romauntNetworkCallback) {
        this.romauntNetworkCallback = romauntNetworkCallback;
    }

    public void register(String mobile,String password,String userName,String avatar){

        String url ="http://139.129.131.240:3000/api/register";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mobile",mobile)
                .addParams("password", password)
                .addParams("userName", userName)
                .addParams("avatar",avatar)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);

                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<LoginResponse>() {}.getType();
                            LoginResponse loginResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(loginResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }

                    }
                });
    }

    public void login(String mobile,String password){

        String url ="http://139.129.131.240:3000/api/login";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mobile",mobile)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());
                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                    }

                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<LoginResponse>() {}.getType();
                            LoginResponse loginResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(loginResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }
                });

    }

    public void getPersonStoryList(String LoginToken,String userID,String page,String Count){


        String url ="http://139.129.131.240:3000/api/token/storylist/"+page+"/"+Count;

        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("userID",userID)
                .addParams("page", page)
                .addParams("Count", Count)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<PersonStoryListResponse>() {}.getType();
                            PersonStoryListResponse personStoryListResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(personStoryListResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void getPublicStoryList(String LoginToken,String timestamps,String page,String Count){
        String url ="http://139.129.131.240:3000/api/token/public/storylist/"+page+"/"+Count;

        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("timestamps",timestamps)
                .addParams("page", page)
                .addParams("Count", Count)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<PublicStoryListResponse>() {}.getType();
                            PublicStoryListResponse publicStoryListResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(publicStoryListResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void getToken(String token){

        String url ="http://139.129.131.240:3000/api/getToken";

        OkHttpUtils
                .post()
                .url(url)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<LoginResponse>() {}.getType();
                            LoginResponse loginResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(loginResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }


                });

    }

    public void getUserInfo(String LoginToken, String AuthorID){
        String url ="http://139.129.131.240:3000/api/token/userinfo";


        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("AuthorID",AuthorID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);

                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<UserInfoResponse>() {}.getType();
                            UserInfoResponse userInfoResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(userInfoResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }

                    }
                });



    }

    public void uploadStory(String LoginToken,String title,String flags,String content,String publicEnable){
        String url ="http://139.129.131.240:3000/api/token/addstory";

        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("title",title)
                .addParams("flags", flags)
                .addParams("content", content)
                .addParams("publicEnable",publicEnable)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<UploadStoryResponse>() {}.getType();
                            UploadStoryResponse uploadStoryResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(uploadStoryResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void getStory(String LoginToken,String storyID,String AuthorID)
    {
        String url ="http://139.129.131.240:3000/api/token/story";

        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("storyID", storyID)
                .addParams("AuthorID",AuthorID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<GetStoryResponse>() {}.getType();
                            GetStoryResponse getStoryResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(getStoryResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });
    }

    public void addFollow(String LoginToken,String starID){
        String url="http://139.129.131.240:3000/api/token/userinfo/addfollow";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("starID", starID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<AddFollowResponse>() {}.getType();
                            AddFollowResponse addFollowResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(addFollowResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void delFollow(String LoginToken,String starID){
        String url="http://139.129.131.240:3000/api/token/userinfo/delfollow";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("starID", starID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<AddFollowResponse>() {}.getType();
                            AddFollowResponse addFollowResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(addFollowResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }

                    }

                });

    }


    public void getChat(String LoginToken,String revID){
        String url="http://139.129.131.240:3000/api/token/chat/get";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("revID", revID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<GetChatResponse>() {}.getType();
                            GetChatResponse getChatResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(getChatResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void sendChat(String LoginToken,String revID,String content){
        String url="http://139.129.131.240:3000/api/token/chat/send";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("revID", revID)
                .addParams("content", content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<SendChatResponse>() {}.getType();
                            SendChatResponse sendChatResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(sendChatResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    /**
     * @param isLike 数字0代表取消赞，1代表点赞*/

    public void storyLike(String LoginToken,String storyID,String isLike){
        String url="http://139.129.131.240:3000/api/token/story/like";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("storyID", storyID)
                .addParams("isLike", isLike)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);

                        Gson gson = new Gson();


                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }





                    }

                });

    }

    /**
     * @param isCollect 数字0代表取消收藏，1代表收藏*/
    public void storyCollect(String LoginToken,String storyID,String isCollect){
        String url="http://139.129.131.240:3000/api/token/story/collect";
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("storyID", storyID)
                .addParams("isCollect", isCollect)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);

                        Gson gson = new Gson();


                        java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                        StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onResponse(statusFalseResponse);
                        }





                    }

                });

    }

    public void getCommentlist(String LoginToken,String storyID){
        String url="http://139.129.131.240:3000/api/token/commentlist/"+storyID;
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("storyID", storyID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<GetCommentlistResponse>() {}.getType();
                            GetCommentlistResponse getCommentlistResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(getCommentlistResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void comment(String LoginToken,String storyID,String commentContent){
        String url="http://139.129.131.240:3000/api/token/comment/"+storyID;
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("commentContent", commentContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<CommentResponse>() {}.getType();
                            CommentResponse commentResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(commentResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }

    public void commentReply(String LoginToken,String storyID,String replyContent){
        String url="http://139.129.131.240:3000/api/token/reply/"+storyID;
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("LoginToken",LoginToken)
                .addParams("replyContent", replyContent)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e("NetWorkTest","Error:"+e.getMessage());

                        if(romauntNetworkCallback!=null){
                            romauntNetworkCallback.onError(e);
                        }

                    }

                    @Override

                    public void onResponse(String s) {
                        Log.e("NetWorkTest","onResponse:"+s);


                        Boolean status=StatusRecognize.getStatus(s);
                        Gson gson = new Gson();

                        if(status==true){
                            java.lang.reflect.Type type = new TypeToken<CommentResponse>() {}.getType();
                            CommentResponse commentResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(commentResponse);
                            }

                        }
                        else
                        {
                            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
                            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

                            if(romauntNetworkCallback!=null){
                                romauntNetworkCallback.onResponse(statusFalseResponse);
                            }

                        }



                    }

                });

    }


    public Object getUserInfoSync(String LoginToken, String AuthorID){
        String url ="http://139.129.131.240:3000/api/token/userinfo";

        Response response;
        String s;
        try {
            response =OkHttpUtils
                    .post()
                    .url(url)
                    .addHeader("LoginToken",LoginToken)
                    .addParams("AuthorID",AuthorID)
                    .build()
                    .execute();
            s =response.body().string();



        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }


        Log.e("NetWorkTest","SyncResponse:"+s);
        Boolean status=StatusRecognize.getStatus(s);
        Gson gson = new Gson();

        if(status==true){
            java.lang.reflect.Type type = new TypeToken<UserInfoResponse>() {}.getType();
            UserInfoResponse userInfoResponse = gson.fromJson(s, type);

            return userInfoResponse;

        }
        else
        {
            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

            return statusFalseResponse;

        }




    }
    public Object getTokenSync(String token){
        String url ="http://139.129.131.240:3000/api/getToken";
        Response response;
        String s;
        try {
            response =OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("token", token)
                    .build()
                    .execute();
            s =response.body().string();



        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }


        Log.e("NetWorkTest","SyncResponse:"+s);
        Boolean status=StatusRecognize.getStatus(s);
        Gson gson = new Gson();

        if(status==true){
            java.lang.reflect.Type type = new TypeToken<LoginResponse>() {}.getType();
            LoginResponse loginResponse = gson.fromJson(s, type);

            return loginResponse;

        }
        else
        {
            java.lang.reflect.Type type = new TypeToken<StatusFalseResponse>() {}.getType();
            StatusFalseResponse statusFalseResponse = gson.fromJson(s, type);

            return statusFalseResponse;

        }

    }




}
