package com.woofer.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;


import com.woofer.activity.userhomepage.FansActivity;
import com.woofer.activity.userhomepage.FollowingsActivity;
import com.woofer.activity.userhomepage.ParhsActivity;
import com.woofer.adapter.OtherUserHomePageTransfer;
import com.woofer.adapter.ViewPagerAdapter;
import com.woofer.net.AddFollowResponse;
import com.woofer.net.GetStoryResponse;
import com.woofer.net.PersonStoryListResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.model.ParhsModel;
import com.woofer.titlebar.TitleBar;
import com.woofer.userInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import woofer.com.test.R;

public class OtherUserHomePage extends Activity {
    private TextView fans, followings,parhs;
    private TextView tv1 ,tv2 ,tv3;
    private ViewPager vp;
    private TitleBar titleBar;


    private String LoginToken;
    private int UserId;


    private TextView UserName;
    private ImageView imgsex;
    private TextView sign;
    private TextView InformTitle;


    private TextView Toptv1;
    private TextView Toptv2;
    private TextView Toptv3;
    private TextView buttomtv;

    private int followinsNUM;
    private int fansNUM;
    private int prahsNUM;
    private ImageButton followImgbtn;
    private Button followbtn;

    private LocalActivityManager manager;
    private ViewPagerAdapter viewPageAdapter;
    private OnClickListener clickListener;
    private OnPageChangeListener pageChangeListener;

    private SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    private boolean hasmyself = false;

    private int followingEnable;
    private int fansEnable ;


    public static OtherUserHomePageTransfer otherUserHomePageTransfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_home_page);

        otherUserHomePageTransfer=new OtherUserHomePageTransfer();
        otherUserHomePageTransfer.fansList=new ArrayList<>();
        otherUserHomePageTransfer.followingList=new ArrayList<>();
        otherUserHomePageTransfer.parhsList=new ArrayList<>();


        Intent intent = getIntent();
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        vp = (ViewPager) findViewById(R.id.OT_home_viewpager);


        String Id = intent.getStringExtra("ID");
        UserId = intent.getIntExtra("UserID", 0);

        SharedPreferences sp1 = getSharedPreferences("ENABLE", storydegitalActivity.MODE_PRIVATE);
        followingEnable = sp1.getInt("FOLLOWINGENABLE", 1);
        fansEnable = sp1.getInt("FANSENABLE", 1);

        sp = this.getSharedPreferences("USERID", OtherUserHomePage.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("USERID", UserId);
        editor.apply();
        LoginToken = intent.getStringExtra("LoginToken");


        String Username = intent.getStringExtra("Username");
        UserName = (TextView)findViewById(R.id.OT_home_username);
        UserName.setText(Username);

        String Sign = intent.getStringExtra("Sign");
        sign = (TextView)findViewById(R.id.OT_home_sign);
        sign.setText(Sign);

        String Avater = intent.getStringExtra("Avater");

        int sex = intent.getIntExtra("Sex", 3);
        imgsex = (ImageView)findViewById(R.id.OT_home_sex);
        if(sex==3){
            imgsex.setVisibility(View.INVISIBLE);
        }else if(sex==0){
            imgsex.setImageResource(R.drawable.img_small_male);
        }
        else
            imgsex.setImageResource(R.drawable.img_small_female);

        InitView();
        titleBar = (TitleBar)findViewById(R.id.OT_home_title);
        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherUserHomePage.this.finish();
            }
        });



    }
    protected void InitView() {
        // TODO Auto-generated method stub
        followImgbtn = (ImageButton)findViewById(R.id.OT_home_follow_btn);
        followbtn =(Button)findViewById(R.id.OT_home_addfollow_btn);
        followbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (hasmyself) {
                        /**取消关注*/

                                RomauntNetWork romauntNetWork = new RomauntNetWork();
                                romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        AddFollowResponse addFollowResponse = (AddFollowResponse) response;
                                        Log.e("cancelfollow", addFollowResponse.status);




                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                followImgbtn.setImageResource(R.drawable.icon_plus_grey);
                                                hasmyself = false;
                                                SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);


                                                String username = sp.getString("USERNAME","");
                                                List<Map<String,Object>> fansList=OtherUserHomePage.otherUserHomePageTransfer.fansList;
                                                for(int i =0 ;i<fansList.size();i++) {
                                                    if(fansList.get(i).get("USERNAME").equals(username)) {
                                                        fansList.remove(i);
                                                    }
                                                }
                                                Intent i = new Intent("com.zaizai1.broadcast.notifyFansRefresh");
                                                sendBroadcast(i);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Object error) {
                                        Toast.makeText(OtherUserHomePage.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                romauntNetWork.delFollow(LoginToken, Integer.toString(UserId));



                    } else {

                                RomauntNetWork romauntNetWork = new RomauntNetWork();
                                romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        if (!(response instanceof AddFollowResponse)) {
                                            return;
                                        }
                                        AddFollowResponse addFollowResponse = (AddFollowResponse) response;
                                        if (addFollowResponse.status.equals("true")) {


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    followImgbtn.setImageResource(R.drawable.icon_plus_green);
                                                    hasmyself = true;
                                                    SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);

                                                    String username = sp.getString("USERNAME","");
                                                    String sign= sp.getString("USERSIGN", "");

                                                    Map<String, Object> map=new HashMap<>();
                                                    map.put("AVATAR",  R.drawable.img_warning);
                                                    map.put("SEX", R.drawable.img_warning);
                                                    map.put("USERNAME", username);
                                                    map.put("SIGN", sign);
                                                    OtherUserHomePage.otherUserHomePageTransfer.fansList.add(map);


                                                    Intent i = new Intent("com.zaizai1.broadcast.notifyFansRefresh");
                                                    sendBroadcast(i);

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onError(Object error) {
                                        Toast.makeText(OtherUserHomePage.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                romauntNetWork.addFollow(LoginToken, Integer.toString(UserId));

                    }

            }
        });
        InformTitle = (TextView)findViewById(R.id.OT_home_item);

        tv1 = (TextView)findViewById(R.id.OT_home_tv1);
        tv2 = (TextView)findViewById(R.id.OT_home_tv2);
        tv3 = (TextView)findViewById(R.id.OT_home_tv3);
        buttomtv = (TextView)findViewById(R.id.OT_home_item);
        buttomtv.setText("粉丝");
        Toptv1 = (TextView)findViewById(R.id.OT_home_fans);
        Toptv2 = (TextView)findViewById(R.id.OT_home_following);
        Toptv3 = (TextView)findViewById(R.id.OT_home_parhs);
        fans = (TextView) findViewById(R.id.OT_home_tv_fans);
        followings = (TextView) findViewById(R.id.OT_home_tv_following);
        parhs = (TextView) findViewById(R.id.OT_home_tv_parhs);
        fans.setTextColor(Color.rgb(25, 142, 123));
        tv1.setBackgroundColor(Color.rgb(25, 142, 123));
        Toptv1.setTextColor(Color.rgb(25, 142, 123));
        clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.OT_home_tv_fans:
                        /*设置转场效果
                        * params two*/
                        vp.setCurrentItem(0);
                        break;
                    case R.id.OT_home_tv_following:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.OT_home_tv_parhs:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.OT_home_tv1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.OT_home_tv2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.OT_home_tv3:
                        vp.setCurrentItem(2);
                        break;
                }
            }
        };
        fans.setOnClickListener(clickListener);
        followings.setOnClickListener(clickListener);
        parhs.setOnClickListener(clickListener);
        tv1.setOnClickListener(clickListener);
        tv2.setOnClickListener(clickListener);
        tv3.setOnClickListener(clickListener);
        InitPager();


        getuserInfo();
        getParas();

    }

    private void InitPager(){
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        Toptv1.setTextColor(Color.rgb(25, 142, 123));
                        Toptv2.setTextColor(Color.rgb(139, 139, 139));
                        Toptv3.setTextColor(Color.rgb(139, 139, 139));
                        fans.setTextColor(Color.rgb(25, 142, 123));
                        buttomtv.setText("粉丝");
                        tv1.setBackgroundColor(Color.rgb(25, 142, 123));
                        followings.setTextColor(Color.rgb(139, 139, 139));
                        tv2.setBackgroundColor(Color.rgb(255, 255, 255));
                        parhs.setTextColor(Color.rgb(139, 139, 139));
                        tv3.setBackgroundColor(Color.rgb(255,255,255));
                        break;
                    case 1:
                        Toptv1.setTextColor(Color.rgb(139, 139, 139));
                        Toptv2.setTextColor(Color.rgb(25, 142, 123));
                        Toptv3.setTextColor(Color.rgb(139, 139, 139));
                        fans.setTextColor(Color.rgb(139, 139, 139));
                        buttomtv.setText("关注");
                        tv1.setBackgroundColor(Color.rgb(255, 255, 255));
                        followings.setTextColor(Color.rgb(25, 142, 123));
                        tv2.setBackgroundColor(Color.rgb(25, 142, 123));
                        tv3.setBackgroundColor(Color.rgb(255, 255, 255));
                        parhs.setTextColor(Color.rgb(139, 139, 139));

                        break;
                    case 2:
                        Toptv1.setTextColor(Color.rgb(139, 139, 139));
                        Toptv2.setTextColor(Color.rgb(139, 139, 139));
                        Toptv3.setTextColor(Color.rgb(25, 142, 123));
                        parhs.setTextColor(Color.rgb(25, 142, 123));
                        followings.setTextColor(Color.rgb(139, 139, 139));
                        fans.setTextColor(Color.rgb(139, 139, 139));
                        buttomtv.setText("文章");
                        tv3.setBackgroundColor(Color.rgb(25,142,123));
                        tv2.setBackgroundColor(Color.rgb(255,255,255));
                        tv1.setBackgroundColor(Color.rgb(255,255,255));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        AddActivitiesToViewPager();
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(pageChangeListener);
    }
    private void AddActivitiesToViewPager() {
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();

        intent.setClass(this, FansActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("QualityActivity1", intent));

        intent.setClass(this, FollowingsActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("QualityActivity2", intent));

        intent.setClass(this, ParhsActivity.class);
        intent.putExtra("id", 3);
        mViews.add(getView("QualityActivity3", intent));

        viewPageAdapter = new ViewPagerAdapter(mViews);
        vp.setAdapter(viewPageAdapter);
    }
    private View getView(String id, Intent intent) {

        return manager.startActivity(id, intent).getDecorView();

    }
    private void getuserInfo(){
        //启动新线程！
          new Thread(new Runnable() {
            @Override
            public void run() {

                final RomauntNetWork romauntNetWork = new RomauntNetWork();

                Object response = romauntNetWork.getUserInfoSync(LoginToken, Integer.toString(UserId));
                if(response==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OtherUserHomePage.this, "网络无连接", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return ;
                }
                if (response instanceof UserInfoResponse) {
                    final UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                    if(userInfoResponse.msg.following!=null){
                        followinsNUM = userInfoResponse.msg.following.size();
                        fansNUM = userInfoResponse.msg.follower.size();
                        Log.e("followinsNUM", Integer.toString(followinsNUM));

                        for(int i = 0 ; i < fansNUM ; i++){
                            Map<String, Object> map=new HashMap<String, Object>();
                            map.put("AVATAR",  R.drawable.img_warning);
                            map.put("SEX", R.drawable.img_warning);
                            map.put("USERNAME", userInfoResponse.msg.follower.get(i).userName);
                            map.put("SIGN", userInfoResponse.msg.follower.get(i).sign);
                            otherUserHomePageTransfer.fansList.add(map);
                        }

                        Intent i1 = new Intent("com.zaizai1.broadcast.notifyFansGot");
                        sendBroadcast(i1);

                        for(int i = 0 ; i < followinsNUM ; i++){
                            Map<String, Object> map=new HashMap<String, Object>();
                            map.put("AVATAR",  R.drawable.img_warning);
                            map.put("SEX", R.drawable.img_warning);
                            map.put("USERNAME", userInfoResponse.msg.following.get(i).userName);
                            map.put("SIGN", userInfoResponse.msg.following.get(i).sign);
                            otherUserHomePageTransfer.followingList.add(map);
                        }

                        Intent i2 = new Intent("com.zaizai1.broadcast.notifyFollowingsGot");
                        sendBroadcast(i2);


                        SharedPreferences sp  = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);
                        String username = sp.getString("USERNAME", "");

                        Map<String, Object> map1=new HashMap<String, Object>();
                        map1.put("USERNAME", username);

                        OtherUserHomePageTransfer otherUserHomePageTransfer1=new OtherUserHomePageTransfer();
                        otherUserHomePageTransfer1.fansList=new ArrayList<>();
                        otherUserHomePageTransfer1.fansList.add(map1);
                        Log.e("map1", "1");
                        boolean hasMe = false;
                        for(int i=0; i<otherUserHomePageTransfer.fansList.size();i++){
                            if(otherUserHomePageTransfer.fansList.get(i).get("USERNAME").equals(otherUserHomePageTransfer1.fansList.get(0).get("USERNAME"))){
                                hasMe=true;
                                hasmyself = true;
                            }
                        }
                        if(hasMe) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    followImgbtn.setImageResource(R.drawable.icon_plus_green);
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    followImgbtn.setImageResource(R.drawable.icon_plus_grey);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                followings.setText(Integer.toString(followinsNUM));
                                fans.setText(Integer.toString(fansNUM));
                            }
                        });

                    }else{
                        followinsNUM = 0;
                        fansNUM  = 0;
                    }

                    String username = userInfoResponse.msg.user.userName;
                    int sex = userInfoResponse.msg.user.sex;

                }
                else
                {
                   Log.e("Romaunt","status false");
                }


            }
        }).start();
    }







    public void getParas(){



        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        final String loginToken= sp.getString("LOGINTOKEN", "");

        SharedPreferences sp1 = getSharedPreferences("USERID", OtherUserHomePage.MODE_PRIVATE);
        final int userid = sp1.getInt("USERID", 0);


        if (!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final PersonStoryListResponse personStoryListResponse = (PersonStoryListResponse) response;
                    for (int i = 0; i < personStoryListResponse.msg.stories.size(); i++) {
                        otherUserHomePageTransfer.parhsList.add(new ParhsModel(
                                personStoryListResponse.msg.stories.get(i).flags,
                                personStoryListResponse.msg.stories.get(i).title,
                                datetotime(personStoryListResponse.msg.stories.get(i).createdAt),
                                0,
                                personStoryListResponse.msg.stories.get(i).content,
                                personStoryListResponse.msg.stories.get(i).id));
                        Log.e("listLogic", personStoryListResponse.msg.stories.get(i).content);
                    }
                    for (int i = 0; i < personStoryListResponse.msg.stories.size(); i++) {
                        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                final GetStoryResponse storyResponse = (GetStoryResponse) response;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        parhs.setText(Integer.toString(otherUserHomePageTransfer.parhsList.size()));
                                    }
                                });

                                for (int j = 0; j < otherUserHomePageTransfer.parhsList.size(); j++) {
                                    /**比较之前的结果和刷新*/
                                    if (otherUserHomePageTransfer.parhsList.get(j).storyid.equals(storyResponse.msg.story.id)) {
                                        otherUserHomePageTransfer.parhsList.get(j).thumbNUM = storyResponse.msg.likeCount;
                                    }
                                }
                                //数据加载完毕，发送广播通知子activity去获取数据
                                Intent i = new Intent("com.zaizai1.broadcast.notifyParasGot");
                                sendBroadcast(i);


                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });
                        romauntNetWork1.getStory(loginToken, personStoryListResponse.msg.stories.get(i).id, Integer.toString(userid));
                    }


                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getPersonStoryList(loginToken, Integer.toString(userid), "1", "100");
        }



    }


    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
}
