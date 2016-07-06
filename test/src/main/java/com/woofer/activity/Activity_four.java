
package com.woofer.activity;

import android.app.Activity;
import android.content.Intent;
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
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.net.RomauntNetWork;
import com.woofer.refreshlayout.util.Utils;
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
    private String loginToken;
    private String token;



    private String username;
    private String signatre;
    private String avacterurl;
    private URL url;
    private String following;
    private String follower;
    public static SharedPreferences.Editor editor;


    private RomauntNetWork romauntNetWork=new RomauntNetWork();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RomauntAlarmTest","Activity_four onDestroy()");
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

        Log.e("RomauntAlarmTest","Activity_four onCreate()");

        textView = (TextView)findViewById(R.id.activity_four_tV2);
        textView.setText(userInfo.signature);

        tvfollower = (TextView)findViewById(R.id.act_four_followerNUM);
        tvfollinger = (TextView)findViewById(R.id.act_four_followingNUM);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        try {
            webView.loadUrl("file:///android_asset/miaov_demo.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        //webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLoadWithOverviewMode(true);
        //webView.getSettings().setBuiltInZoomControls(true); //显示放大缩小
        //webView.getSettings().setSupportZoom(true); //可以缩放
        //webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);//默认缩放模式
        //webView.setInitialScale(1000);
        webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        /**设置webview监听事件，监听URL*/
        webView.setWebViewClient(new WebViewClientDemo());
        btn1=(Button)findViewById(R.id.activity_four_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_four.this, chooseActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);
        username = sp.getString("USERNAME", "");
        if(btn1.getText().equals("   点此登录")&&!username.equals("")){
            btn1.setText(username);
            loginToken=sp.getString("LOGINTOKEN","");
            token= sp.getString("TOKEN", "");
            signatre = sp.getString("USERSIGN", "");
//            follower = sp.getString("FOLLOWERNUM", "");
//            following = sp.getString("FOLLOWINGNUM", "");

            textView.setText(signatre);
//            tvfollinger.setText(following);
//            tvfollower.setText(follower);
            btn1.setClickable(false);
        }else{
            btn1.setClickable(true);
            userInfo.username="";

            Toast.makeText(Activity_four.this, "当前登录状态"+userInfo.username, Toast.LENGTH_SHORT).show();
            btn1.setText("   点此登录");
        }
        imageView=(ImageView)findViewById(R.id.activity_four_img1);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_four.this, UserInfoActivity.class);
                startActivity(intent);

            }
        });
        avacterurl = sp.getString("AVATERURL", "");
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


    private class WebViewClientDemo extends WebViewClient {
        @Override
        // 在WebView中而不是默认浏览器中显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.trim().equals("https://www.config.com/")) {
                Intent intent = new Intent(Activity_four.this, configActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                /**不要对Activity进行finish操作 留在栈中*/
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
    }
    @Override
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
        /**不要写成
         *return super.onKeyDown(keyCode, event);
         *有毒
         */
        return true;
    }
}
