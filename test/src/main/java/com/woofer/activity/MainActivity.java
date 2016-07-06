package com.woofer.activity;

import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.StatusFalseResponse;
import com.woofer.net.UserInfoResponse;
import com.woofer.userInfo;

import woofer.com.test.R;
import com.woofer.adapter.ViewPagerAdapter;

/*测试结果
* viewpager可以进行嵌套 目测是拦截了滑动事件*/
public class MainActivity extends Activity {

    //控件
    private ImageView img1, img2, img3, img4;
    private ViewPager vp;
    private String logintoken;
    private String token;
    private String userID;
    private String signature;
    private String avaterurl;
    private int followerNUM;
    private int followingNUM;
    private String num1;
    private String num2;




    //LocalActivityManager用来获取每个activity的view,放于Adapter中
    //MyViewPageAdapter用来放viewpager的内容
    //OnClickListener设置底部图片的点击事件
    //OnPageChangeListener设置图片的滑动事件
    private LocalActivityManager manager;
    private ViewPagerAdapter viewPageAdapter;
    private OnClickListener clickListener;
    private OnPageChangeListener pageChangeListener;

    public static SharedPreferences.Editor editor;

    private BroadcastReceiver mBroadcastReceiver;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RomauntAlarmTest","MainActivity onDestroy()");
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("RomauntAlarmTest","MainActivity onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RomauntAlarmTest","MainActivity onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RomauntAlarmTest","MainActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RomauntAlarmTest","MainActivity onPause()");
    }

    /*拿到存储的shareperference
                    并且判断是否失效 失效重获*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("RomauntAlarmTest","MainActivity onCreate()");


        //设定定时器的广播接收器
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //异步拿logintoken

            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zaizai1.broadcast.getLoginToken");
        registerReceiver(mBroadcastReceiver, intentFilter);

        SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);
        logintoken = sp.getString("LOGINTOKEN","");
        Log.e("Romaunt", "LoginToken:" + logintoken);
        token = sp.getString("TOKEN","");
        Log.e("Romaunt","token:"+token);
        userID = sp.getString("USERID", "");

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        vp = (ViewPager) findViewById(R.id.vPager);




        if(logintoken.equals("")){
            Log.e("Romaunt","本地未存储有LoginToken");
        }else{

            //启动新线程！
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final RomauntNetWork romauntNetWork = new RomauntNetWork();

                    Object response = romauntNetWork.getUserInfoSync(logintoken, userID);
                    if(response==null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT).show();
                            }
                        });
                        return ;
                    }
                    if (response instanceof UserInfoResponse) {
                        UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                        Log.e("Romaunt", userInfoResponse.msg.user.mobile);
                        signature = userInfoResponse.msg.user.sign;
                        avaterurl = userInfoResponse.msg.user.avatar;
                        String username = userInfoResponse.msg.user.userName;
                        int sex = userInfoResponse.msg.user.sex;
                        Log.e("Romaunt", "&&&&&&&&&&&&&&&&&&&&&&&");
                        //int follower  = userInfoResponse.msg.follower.size();

                        SharedPreferences sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);
                        editor = sp.edit();
                            /*editor.putString("FOLLOWERNUM",num1);
                            editor.putString("FOLLOWINGNUM", num2);*/
                        editor.putString("NICHENG",username);
                        editor.putInt("SEX",sex);
                        editor.putString("AVATERURL", avaterurl);
                        editor.putString("USERSIGN", signature);

                        editor.apply();
                        Log.e("AVA", avaterurl);
                    }
                    else if(response instanceof StatusFalseResponse && ((StatusFalseResponse) response).msg.equals("LoginToken"))
                    {
                        Object response1 =romauntNetWork.getTokenSync(token);
                        if(response1==null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT).show();
                                }
                            });
                            return ;
                        }
                        if (response1 instanceof LoginResponse) {
                            LoginResponse loginResponse =(LoginResponse)response1;

                            logintoken = loginResponse.msg.LoginToken;
                            token = loginResponse.msg.token;
                            userID = loginResponse.msg.userID;

                            SharedPreferences sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);
                            userInfo.status = 1;
                            editor = sp.edit();
                            editor.putString("LOGINTOKEN", logintoken);
                            editor.putString("TOKEN", token);

                            editor.apply();

                            Object response2 = romauntNetWork.getUserInfoSync(logintoken, userID);
                            if(response2==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return ;
                            }
                            if(response2 instanceof UserInfoResponse){
                                UserInfoResponse userInfoResponse = (UserInfoResponse) response2;
                                Log.e("Romaunt", userInfoResponse.msg.user.mobile);
                                signature = userInfoResponse.msg.user.sign;
                                avaterurl = userInfoResponse.msg.user.avatar;
                                String username = userInfoResponse.msg.user.userName;
                                int sex = userInfoResponse.msg.user.sex;

                                sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);
                                editor = sp.edit();
                            /*editor.putString("FOLLOWERNUM",num1);
                            editor.putString("FOLLOWINGNUM", num2);*/
                                editor.putString("NICHENG",username);
                                editor.putInt("SEX",sex);
                                editor.putString("AVATERURL", avaterurl);
                                editor.putString("USERSIGN", signature);

                                editor.apply();
                                Log.e("AVA", avaterurl);

                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT);
                                    }
                                });
                            }

                        }
                        else if(response instanceof StatusFalseResponse)
                        {
                            //网络无连接
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT);
                                }
                            });
                        }


                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"网络无连接",Toast.LENGTH_SHORT);
                            }
                        });
                    }


                }
            }).start();



        }


        InitView();

    }

    private void InitView() {
        // TODO Auto-generated method stub
        img1 = (ImageView) findViewById(R.id.main_img1);
        img2 = (ImageView) findViewById(R.id.main_img2);
        img3 = (ImageView) findViewById(R.id.main_img3);
        img4 = (ImageView) findViewById(R.id.main_img4);
        clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.main_img1:
                        /*设置转场效果
                        * params two*/
                        vp.setCurrentItem(0,true);
                        break;
                    case R.id.main_img2:
                        vp.setCurrentItem(1,true);
                        break;
                    case R.id.main_img3:
                        vp.setCurrentItem(2,true);
                        break;
                    case R.id.main_img4:
                        vp.setCurrentItem(3,true);
                        break;
                }
            }
        };

        img1.setOnClickListener(clickListener);
        img2.setOnClickListener(clickListener);
        img3.setOnClickListener(clickListener);
        img4.setOnClickListener(clickListener);
        InitPager();

    }

    private void InitPager() {
        // TODO Auto-generated method stub
        pageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                switch (arg0) {
                    case 0:
                        img1.setImageResource(R.drawable.img_story_choose);
                        img2.setImageResource(R.drawable.img_findings);
                        img3.setImageResource(R.drawable.img_message);
                        img4.setImageResource(R.drawable.img_my);
                        break;
                    case 1:
                        img1.setImageResource(R.drawable.img_story);
                        img2.setImageResource(R.drawable.img_findings_choose);
                        img3.setImageResource(R.drawable.img_message);
                        img4.setImageResource(R.drawable.img_my);
                        break;
                    case 2:
                        img1.setImageResource(R.drawable.img_story);
                        img2.setImageResource(R.drawable.img_findings);
                        img3.setImageResource(R.drawable.img_message_choose);
                        img4.setImageResource(R.drawable.img_my);
                        break;
                    case 3:
                        img1.setImageResource(R.drawable.img_story);
                        img2.setImageResource(R.drawable.img_findings);
                        img3.setImageResource(R.drawable.img_message);
                        img4.setImageResource(R.drawable.img_my_choose);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        };

        AddActivitiesToViewPager();
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(pageChangeListener);
    }

    private void AddActivitiesToViewPager() {
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();

        intent.setClass(this, Activity_one.class);
        intent.putExtra("id", 1);
        mViews.add(getView("QualityActivity1", intent));

        intent.setClass(this, Activity_two.class);
        intent.putExtra("id", 2);
        mViews.add(getView("QualityActivity2", intent));

        intent.setClass(this, Activity_three.class);
        intent.putExtra("id", 3);
        mViews.add(getView("QualityActivity3", intent));

        intent.setClass(this, Activity_four.class);
        intent.putExtra("id", 4);
        mViews.add(getView("QualityActivity4", intent));

        viewPageAdapter = new ViewPagerAdapter(mViews);
        vp.setAdapter(viewPageAdapter);

    }

    private View getView(String id, Intent intent) {

        return manager.startActivity(id, intent).getDecorView();

    }


}

/*
public class MainActivity extends Activity implements OnClickListener,
        OnPageChangeListener {
    // 定义ViewPager对象
    private ViewPager viewPager;

    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;

    // 定义一个ArrayList来存放View
    private ArrayList<View> views;

    // 引导图片资源
    private static final int[] pics = { R.drawable.guide1, R.drawable.guide2,
            R.drawable.guide3, R.drawable.guide4 };

    // 底部小点的图片
    private ImageView[] points;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化组件
        initView();
        // 初始化数据
        initData();
    }

    *//**
     * 初始化组件
     *//*
    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();

        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);
    }

    *//**
     * 初始化数据
     *//*
    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }

        // 设置数据
        //前面的views中没有数据 在前面的循环中才插入数据 而此时vpAdapter中已经有数据说明
        //初始化adapter的时候 参数传递是传引用
        viewPager.setAdapter(vpAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);

        // 初始化底部小点
        initPoint();
    }

    *//**
     * 初始化底部小点
     *//*
    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        points = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }

    *//**
     * 当滑动状态改变时调用
     *//*
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    *//**
     * 当当前页面被滑动时调用
     *//*

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    *//**
     * 当新的页面被选中时调用
     *//*

    @Override
    public void onPageSelected(int position) {
        // 设置底部小点选中状态
        setCurDot(position);
    }

    *//**
     * 通过点击事件来切换当前的页面
     *//*
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    *//**
     * 设置当前页面的位置
     *//*
    private void setCurView(int position) {
        // 排除异常情况
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    *//**
     * 设置当前的小点的位置
     *//*
    private void setCurDot(int positon) {
        // 排除异常情况
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);

        currentIndex = positon;
    }

}
*/