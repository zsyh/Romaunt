package com.woofer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.net.RomauntNetWork;
import com.woofer.net.StatusFalseResponse;
import com.woofer.net.UserInfoResponse;
import com.woofer.util.Utils;
import com.woofer.ui.imagetextimage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import woofer.com.test.R;

public class Activity_four extends Activity {
    /*private final Handler handler = new Handler();
    private WebView webView;*/

    private ImageView avatarimg;
    private long firstbacktime = 0;
    private TextView signTV;
    private TextView followernumTV;
    private TextView follingernumTV;

    private Button Loginbtn;
    private Button addressbtn;

    private imagetextimage diybtn_myhomepage;
    private imagetextimage ditbtn_personaldata;
    private imagetextimage diybtn_aboutus;
    private imagetextimage diybtn_collect;
    private imagetextimage diybtn_work;
    private imagetextimage diybtn_configAndlogout;

    private LinearLayout fansandfollowingArea;

    private URL url;
    private BroadcastReceiver broadcastReceiverUserInfo;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RomauntAlarmTest","Activity_four onDestroy()");
        unregisterReceiver(broadcastReceiverUserInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("RomauntAlarmTest","Activity_four onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RomauntAlarmTest","Activity_four onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RomauntAlarmTest", "Activity_four onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RomauntAlarmTest","Activity_four onPause()");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_activity_four);

        Log.e("RomauntAlarmTest", "Activity_four onCreate()");

        broadcastReceiverUserInfo=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sp  = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
                String sign = sp.getString("USERSIGN","");
                String username = sp.getString("USERNAME","");
                Loginbtn.setText(username);
                signTV.setText(sign);

            }
        };
        registerReceiver(broadcastReceiverUserInfo, new IntentFilter("com.zaizai1.broadcast.userInfoUpdated"));


        final SharedPreferences Userinfosp  = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);

        signTV = (TextView)findViewById(R.id.activity_four_tV2);
        signTV.setText(Userinfosp.getString("USERSIGN", ""));

        followernumTV = (TextView)findViewById(R.id.act_four_followerNUM);

        follingernumTV = (TextView)findViewById(R.id.act_four_followingNUM);

        diybtn_myhomepage = (imagetextimage)findViewById(R.id.act_four_tit_one);
        ditbtn_personaldata = (imagetextimage)findViewById(R.id.act_four_tit_two);
        diybtn_aboutus = (imagetextimage)findViewById(R.id.act_four_tit_three);
        diybtn_collect = (imagetextimage)findViewById(R.id.act_four_tit_four);
        diybtn_work = (imagetextimage)findViewById(R.id.act_four_tit_five);
        diybtn_configAndlogout = (imagetextimage)findViewById(R.id.act_four_tit_six);

        fansandfollowingArea =(LinearLayout)findViewById(R.id.activity_four_Lin1);


    /*private class WebViewClientDemo extends WebViewClient {
        @Override
        // 在WebView中而不是默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.trim().equals("https://www.config.com/")) {
                Intent intent = new Intent(Activity_four.this, ConfigActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                *//**不要对Activity进行finish操作 留在栈中*//*
            } else if(url.trim().equals("https://www.aboutus.com/")){
                Intent intent = new Intent(Activity_four.this, AboutusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(url.trim().equals("https://www.user_info.com/")){
                Intent intent = new Intent(Activity_four.this, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(url.trim().equals("https://www.myhomepage.com/")){
                Intent intent = new Intent(Activity_four.this, PersonHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                webView.loadUrl("file:///android_asset/svg.html");
                //view.loadUrl(url);
            }

            return true;
        }
    }*/

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("userinfo",MODE_PRIVATE);

                switch (v.getId()){
                    case R.id.act_four_tit_one:
                        if(sp.getString("TOKEN","").equals("")) {
                            Toast.makeText(Activity_four.this,"你尚未登录!",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        SharedPreferences sp1 = Activity_four.this.getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putInt("FOLLOWINGENABLE", 1);
                        editor.putInt("FANSENABLE", 1);
                        editor.apply();
                        Intent intent = new Intent(Activity_four.this, OtherUserHomePage.class);
                        //Intent intent1 = new Intent(Activity_four.this, PersonHomeActivity.class);
                        intent.putExtra("LoginToken", sp.getString("LOGINTOKEN",""));

                        intent.putExtra("UserID", sp.getInt("userID",0));
                        intent.putExtra("Avater", sp.getString("AVATERURL",""));
                        intent.putExtra("Sign", sp.getString("USERSIGN",""));
                        intent.putExtra("Username", sp.getString("USERNAME",""));
                        intent.putExtra("Sex", sp.getInt("SEX",3));
                        intent.putExtra("FOLLOWINGENABLE", sp.getInt("FOLLOWINGENABLE",3));
                        intent.putExtra("FANSENABLE", sp.getInt("FOLLOWERENABLE",3));
                        startActivity(intent);
                        break;
                    case R.id.act_four_tit_two:
                        if(sp.getString("TOKEN","").equals("")) {
                            Toast.makeText(Activity_four.this,"你尚未登录!",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        intent = new Intent(Activity_four.this, UserInfoActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.act_four_tit_three:
                        intent = new Intent(Activity_four.this, AboutusActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.act_four_tit_four:

                        break;
                    case R.id.act_four_tit_five:
                        break;
                    case R.id.act_four_tit_six:
                        if(sp.getString("TOKEN","").equals("")) {
                            Toast.makeText(Activity_four.this,"你尚未登录!",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        intent = new Intent(Activity_four.this, ConfigActivity.class);
                        startActivity(intent);
                        break;


                }
            }
        };
        diybtn_myhomepage.setOnClickListener(clickListener);
        ditbtn_personaldata.setOnClickListener(clickListener);
        diybtn_aboutus.setOnClickListener(clickListener);
        diybtn_collect.setOnClickListener(clickListener);
        diybtn_work.setOnClickListener(clickListener);
        diybtn_configAndlogout.setOnClickListener(clickListener);
        diybtn_myhomepage.setImage(R.drawable.icon_my_homepage);
        diybtn_myhomepage.setText("我的主页");
        ditbtn_personaldata.setImage(R.drawable.img_defaultavatar);
        ditbtn_personaldata.setText("个人资料");

        diybtn_aboutus.setText("关于");
        diybtn_aboutus.setImage(R.drawable.about);
        diybtn_collect.setText("收藏");
        diybtn_collect.setImage(R.drawable.icon_collection);
        diybtn_work.setText("作品");
        diybtn_work.setImage(R.drawable.icon_open_book);
        diybtn_configAndlogout.setText("设置与登出");
        diybtn_configAndlogout.setImage(R.drawable.icon_setup);




        /*webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.loadUrl("file:///android_asset/miaov_demo.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);*/
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setBuiltInZoomControls(true); //显示放大缩小
        //webView.getSettings().setSupportZoom(true); //可以缩放
        //webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);//默认缩放模式
        //webView.setInitialScale(1000);
        /*webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        *//**设置webview监听事件，监听URL*//*
        webView.setWebViewClient(new WebViewClientDemo());*/

        Loginbtn =(Button)findViewById(R.id.activity_four_btn1);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_four.this, ChooseActivity.class);
                startActivity(intent);
            }
        });
        addressbtn = (Button)findViewById(R.id.activity_four_addressbtn);
        addressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_four.this,GithubAboutActivity.class);
                startActivity(intent);
            }
        });

        avatarimg=(ImageView)findViewById(R.id.activity_four_img1);
        avatarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);

                if (sp.getString("TOKEN", "").equals("")) {
                    Toast.makeText(Activity_four.this, "你尚未登录!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Activity_four.this, UserInfoActivity.class);
                startActivity(intent);

            }
        });



        String logintoken = Userinfosp.getString("LOGINTOKEN","");
        if(logintoken.equals("")){
            Log.e("Romaunt","本地未存储有LoginToken");
        }else{

            //启动新线程！
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final RomauntNetWork romauntNetWork = new RomauntNetWork();

                    String logintoken = Userinfosp.getString("LOGINTOKEN","");
                    String userID=Userinfosp.getString("USERID","");
                    Log.e("LOGINTOKEN",logintoken);

                    Object response = romauntNetWork.getUserInfoSync(logintoken, userID);
                    if(response==null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_four.this,"网络无连接",Toast.LENGTH_SHORT).show();
                            }
                        });
                        return ;
                    }
                    if (response instanceof UserInfoResponse) {
                        UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                        final String signature = userInfoResponse.msg.user.sign;
                        final String avaterurl = userInfoResponse.msg.user.avatar;
                        final String username = userInfoResponse.msg.user.userName;
                        final int fansNUM;
                        final int followingNUM;

                        final int sex = userInfoResponse.msg.user.sex;
                        final int noticeEnable =userInfoResponse.msg.user.noticeEnable;
                        final int followingEnable =userInfoResponse.msg.user.followingEnable;
                        final int followerEnable =userInfoResponse.msg.user.followerEnable;
                        final int aboutNotice =userInfoResponse.msg.user.aboutNotice;
                        final int updateNotice =userInfoResponse.msg.user.updateNotice;
                        final int userid = userInfoResponse.msg.user.id;

                        if(userInfoResponse.msg.follower!=null){
                            fansNUM = userInfoResponse.msg.follower.size();
                        }
                        else{
                            fansNUM = 0;
                        }
                        if(userInfoResponse.msg.following!=null){
                            followingNUM = userInfoResponse.msg.following.size();
                        }
                        else{
                            followingNUM = 0;
                        }
                        SharedPreferences sp = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("userID",userid);
                        editor.putString("USERNAME",username);
                        editor.putInt("SEX", sex);
                        editor.putString("AVATERURL", avaterurl);
                        editor.putString("USERSIGN", signature);
                        editor.putInt("NOTICEENABLE",noticeEnable);
                        editor.putInt("FOLLOWINGENABLE",followingEnable);
                        editor.putInt("FOLLOWERENABLE",followerEnable);
                        editor.putInt("ABOUTENABLE",aboutNotice);
                        editor.putInt("UPDATENOTICE",updateNotice);
                        editor.apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Loginbtn.setText(username);
                                signTV.setText(signature);
                                followernumTV.setText(Integer.toString(fansNUM));
                                follingernumTV.setText(Integer.toString(followingNUM));

                            }
                        });
                    }
                    else if(response instanceof StatusFalseResponse)
                    {
                        Log.e("Romaunt","MainActivity中getUserInfo StatusFalse");
                    }
                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_four.this,"网络无连接",Toast.LENGTH_SHORT);
                            }
                        });
                    }
                }
            }).start();
        }

        if(!Userinfosp.getString("TOKEN","").equals("")){
            Log.e("TOKEN",Userinfosp.getString("TOKEN",""));
            //若已登录
            String username = Userinfosp.getString("USERNAME", "");
            String signatre = Userinfosp.getString("USERSIGN", "");
            Loginbtn.setText(username);
            signTV.setText(signatre);

            Loginbtn.setClickable(false);
            final String avacterurl = Userinfosp.getString("AVATERURL", "");
            final int myid =Userinfosp.getInt("userID",0);
            if(!avacterurl.equals("")) {
                File imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + myid + ".png");
                if (imgfile.exists()) {
                    Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + myid + ".png");
                    avatarimg.setImageBitmap(bmp);

                } else {
                    try {
                        url = new URL(avacterurl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Utils.onLoadImage(url, new Utils.OnLoadImageListener() {
                        @Override
                        public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                            if (bitmap != null) {
                                avatarimg.setImageBitmap(bitmap);
                            }
                        }
                    }, myid);
                    Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + myid + ".png");
                    avatarimg.setImageBitmap(bmp);
                }
            }
        }else{
            //若未登录
            Loginbtn.setClickable(true);
            Loginbtn.setText("   点此登录");
            fansandfollowingArea.setVisibility(View.GONE);
        }
    }



/*
    public static Bitmap getHttpBitmap(String url){
               URL myFileURL;
                Bitmap bitmap=null;
                try{
                    myFileURL = new URL(url);
                    //获得连接
                    HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
                    //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
                    conn.setConnectTimeout(6000);
                    //连接设置获得数据流
                    conn.setDoInput(true);
                    //不使用缓存
                    conn.setUseCaches(false);
                    //这句可有可无，没有影响
                    conn.connect();
                    //得到数据流
                    InputStream is = conn.getInputStream();
                    //解析得到图片
                    bitmap = BitmapFactory.decodeStream(is);
                    //关闭数据流
                    is.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
        return bitmap;
    }
*/
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - firstbacktime <= 2000) {
            this.finish();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstbacktime = currentTime;
        }
    }
    return true;
}


   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack()&& (keyCode == KeyEvent.KEYCODE_BACK) ) {
            webView.goBack();
            return true;
        }else{
            long currentTime=System.currentTimeMillis();
            if(currentTime - firstbacktime <= 2000){
                this.finish();

            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstbacktime = currentTime;
            }
        }
        *//**不要写成
         *return super.onKeyDown(keyCode, event);
         *有毒
         *//*
        return true;
    }*/
}
