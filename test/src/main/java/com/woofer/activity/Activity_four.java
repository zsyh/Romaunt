package com.woofer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.net.RomauntNetWork;
import com.woofer.net.StatusFalseResponse;
import com.woofer.net.UserInfoResponse;
import com.woofer.util.Utils;
import com.woofer.ui.imagetextimage;
import com.woofer.userInfo;

import java.net.MalformedURLException;
import java.net.URL;

import woofer.com.test.R;

public class Activity_four extends Activity {
    private final Handler handler = new Handler();
    private WebView webView;
    private ImageView imageView;
    private long firstbacktime = 0;
    private TextView textView;
    private TextView tvfollower;
    private TextView tvfollinger;

    private ListView listView = null;
    private Button btn1;

    private imagetextimage img1;
    private imagetextimage img2;
    private imagetextimage img3;
    private imagetextimage img4;
    private imagetextimage img5;
    private imagetextimage img6;


    private URL url;

    private BroadcastReceiver broadcastReceiverUserInfo;

    private RomauntNetWork romauntNetWork=new RomauntNetWork();

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
        Log.e("RomauntAlarmTest","Activity_four onResume()");
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
                SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);
                String sign = sp.getString("USERSIGN","");
                String username = sp.getString("USERNAME","");
                btn1.setText(username);
                textView.setText(sign);

            }
        };
        registerReceiver(broadcastReceiverUserInfo, new IntentFilter("com.zaizai1.broadcast.userInfoUpdated"));


        final SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);

        textView = (TextView)findViewById(R.id.activity_four_tV2);
        textView.setText(sp.getString("USERSIGN",""));

        tvfollower = (TextView)findViewById(R.id.act_four_followerNUM);
        tvfollinger = (TextView)findViewById(R.id.act_four_followingNUM);

        img1 = (imagetextimage)findViewById(R.id.act_four_tit_one);
        img2 = (imagetextimage)findViewById(R.id.act_four_tit_two);
        img3 = (imagetextimage)findViewById(R.id.act_four_tit_three);
        img4 = (imagetextimage)findViewById(R.id.act_four_tit_four);
        img5 = (imagetextimage)findViewById(R.id.act_four_tit_five);
        img6 = (imagetextimage)findViewById(R.id.act_four_tit_six);


    /*private class WebViewClientDemo extends WebViewClient {
        @Override
        // 在WebView中而不是默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.trim().equals("https://www.config.com/")) {
                Intent intent = new Intent(Activity_four.this, configActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                *//**不要对Activity进行finish操作 留在栈中*//*
            } else if(url.trim().equals("https://www.aboutus.com/")){
                Intent intent = new Intent(Activity_four.this, aboutusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(url.trim().equals("https://www.user_info.com/")){
                Intent intent = new Intent(Activity_four.this, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else if(url.trim().equals("https://www.myhomepage.com/")){
                Intent intent = new Intent(Activity_four.this, personHomeActivity.class);
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
                        Intent intent = new Intent(Activity_four.this, personHomeActivity.class);
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
                        intent = new Intent(Activity_four.this, aboutusActivity.class);
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
                        intent = new Intent(Activity_four.this, configActivity.class);
                        startActivity(intent);
                        break;


                }
            }
        };
        img1.setOnClickListener(clickListener);
        img2.setOnClickListener(clickListener);
        img3.setOnClickListener(clickListener);
        img4.setOnClickListener(clickListener);
        img5.setOnClickListener(clickListener);
        img6.setOnClickListener(clickListener);
        img1.setImage(R.drawable.icon_my_homepage);
        img1.setText("我的主页");
        img2.setImage(R.drawable.img_defaultavatar);
        img2.setText("个人资料");

        img3.setText("关于");
        img3.setImage(R.drawable.about);
        img4.setText("收藏");
        img4.setImage(R.drawable.icon_collection);
        img5.setText("作品");
        img5.setImage(R.drawable.icon_open_book);
        img6.setText("设置与登出");
        img6.setImage(R.drawable.icon_setup);




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








        btn1=(Button)findViewById(R.id.activity_four_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_four.this, chooseActivity.class);
                startActivity(intent);
            }
        });



        if(!sp.getString("TOKEN","").equals("")){
            //若已登录
            String username = sp.getString("USERNAME", "");
            String signatre = sp.getString("USERSIGN", "");
            btn1.setText(username);
            textView.setText(signatre);

            btn1.setClickable(false);
        }else{
            //若未登录
            btn1.setClickable(true);
            btn1.setText("   点此登录");
        }
        imageView=(ImageView)findViewById(R.id.activity_four_img1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sp = getSharedPreferences("userinfo",MODE_PRIVATE);

                if(sp.getString("TOKEN","").equals("")) {
                    Toast.makeText(Activity_four.this,"你尚未登录!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Activity_four.this, UserInfoActivity.class);
                startActivity(intent);

            }
        });


        String avacterurl = sp.getString("AVATERURL", "");
        if(!avacterurl.equals("")) {
            try {
                url = new URL(avacterurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Utils.onLoadImage(url, new Utils.OnLoadImageListener() {
                @Override
                public void OnLoadImage(Bitmap bitmap, String bitmapPath) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
        }
/*
        有错
        if(!avacterurl.equals("")){
            Toast.makeText(Activity_four.this, avacterurl, Toast.LENGTH_SHORT).show();
            Bitmap bitmap = getHttpBitmap(avacterurl);
            imageView.setImageBitmap(bitmap);
        }
*/



        String logintoken = sp.getString("LOGINTOKEN","");
        String userID=sp.getString("USERID","");

        if(logintoken.equals("")){
            Log.e("Romaunt","本地未存储有LoginToken");
        }else{

            //启动新线程！
            new Thread(new Runnable() {
                @Override
                public void run() {

                    final RomauntNetWork romauntNetWork = new RomauntNetWork();

                    String logintoken = sp.getString("LOGINTOKEN","");
                    String userID=sp.getString("USERID","");

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

                        final int sex = userInfoResponse.msg.user.sex;
                        final int noticeEnable =userInfoResponse.msg.user.noticeEnable;
                        final int followingEnable =userInfoResponse.msg.user.followingEnable;
                        final int followerEnable =userInfoResponse.msg.user.followerEnable;
                        final int aboutNotice =userInfoResponse.msg.user.aboutNotice;
                        final int updateNotice =userInfoResponse.msg.user.updateNotice;


                        SharedPreferences sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
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
                                btn1.setText(username);
                                textView.setText(signature);

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
