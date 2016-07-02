package com.woofer.refreshlayout;


import android.app.Application;

import com.woofer.refreshlayout.engine.Engine;

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
    }
    public static  App getInstance(){return  sInstance;}

    public Engine getEngine(){return mEngine;}
}