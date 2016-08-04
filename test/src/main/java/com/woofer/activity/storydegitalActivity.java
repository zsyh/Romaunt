package com.woofer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import woofer.com.test.R;

import com.woofer.net.GetStoryResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.ui.popupwindow.SelectPicPopupWindow;
import com.woofer.util.Utils;
import com.woofer.titlebar.TitleBar;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StorydegitalActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private ImageView avater;
    private TextView username;
    private TextView signatrue;
    private TextView thumbNUM;
    private TextView lable;
    private EditText Content;
    private String Id;
    private int UserId;
    private String LoginToken;
    private String avaterurl;
    private String Signature;
    private URL url;
    private String UserName;
    private int sex;
    private int followingEnable;
    private int fansEnable;
    private int noticeEnable;

    private boolean isown;

    private ImageButton collectbtn;
    private ImageButton commentbtn;
    private ImageButton transmit;
    private String content;
    private String time;
    private String title;

    SelectPicPopupWindow menuwindow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storydegital);

        Initcompement();

        final Intent intent = getIntent();
        Id = intent.getStringExtra("ID");
        UserId = intent.getIntExtra("USERID", 0);
        LoginToken = intent.getStringExtra("LoginToken");
        setStoryInfo();

        /*SharedPreferences sp  = getSharedPreferences("userinfo",SigninActivity.MODE_PRIVATE);
        avaterurl = sp.getString("AVATERURL", "");*/


        avater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
                SharedPreferences sp1 = StorydegitalActivity.this.getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putInt("FOLLOWINGENABLE", followingEnable);
                editor.putInt("FANSENABLE", fansEnable);
                editor.apply();

                String LoginToken = sp.getString("LOGINTOKEN", "");
                Intent intent1 = new Intent(StorydegitalActivity.this, OtherUserHomePage.class);
                intent1.putExtra("LoginToken", LoginToken);
                intent1.putExtra("UserID", UserId);
                intent1.putExtra("Avater", avaterurl);
                intent1.putExtra("Sign", Signature);
                intent1.putExtra("Username", UserName);
                intent1.putExtra("Sex", sex);
                intent1.putExtra("FOLLOWINGENABLE", followingEnable);
                intent1.putExtra("FANSENABLE", fansEnable);
                startActivity(intent1);
            }
        });


    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuwindow.dismiss();
            switch (v.getId()) {
                case R.id.popupwindow_wxcycle:
                    Toast.makeText(StorydegitalActivity.this, "点击了朋友圈", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_wechat:
                    Toast.makeText(StorydegitalActivity.this, "点击了微信", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_qq:
                    Toast.makeText(StorydegitalActivity.this, "qq", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_sinaweibo:
                    Toast.makeText(StorydegitalActivity.this, "sina", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_url:
                    Toast.makeText(StorydegitalActivity.this, "链接已复制到剪切板", Toast.LENGTH_SHORT).show();
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText("ID:"+Id+",USERRID:"+UserId);
                    break;
                case R.id.popupwindow_sms:
                          Uri smsToUri = Uri.parse( "smsto:" );
                          Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
                           /*sendIntent.putExtra("address", "123456");
                            默认电话号码
                             */
                          sendIntent.putExtra( "sms_body" ,  "分享自:"+username.getText()+" 的“"+title+"”"+"\n来自Romaunt的分享\n"+"ID:"+Id+",USERRID:"+UserId
                          +"\n(复制地址到浏览器中打开)");
                          sendIntent.setType( "vnd.android-dir/mms-sms" );
                          startActivityForResult(sendIntent, 1002 );
                    break;
                case R.id.popupwindow_douban:
                    Toast.makeText(StorydegitalActivity.this, "douban", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.popupwindow_email:
                    Intent email =  new  Intent(android.content.Intent.ACTION_SEND);
                    email.setType( "plain/text" );
                    String  emailSubject =  "分享自:"+username.getText()+" 的“"+title+"”";
                    //设置邮件默认地址
                    // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
                    //设置邮件默认标题
                    email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
                    //设置要默认发送的内容

                    email.putExtra(android.content.Intent.EXTRA_TEXT, Content.getText()+"\n\n\n\n\n\n\n\n来自Romaunt的分享n\n"+"ID:"+Id+",USERRID:"+UserId
                            +"\n(复制地址到浏览器中打开)");
                    //调用系统的邮件系统
                    startActivityForResult(Intent.createChooser(email,  "分享至" ), 1001 );
                    break;
                default:
                    break;
            }


        }

    };

    private void Initcompement() {
        transmit = (ImageButton)findViewById(R.id.story_degital_transmit);
        transmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuwindow = new SelectPicPopupWindow(StorydegitalActivity.this, itemsOnClick);
                menuwindow.showAtLocation(StorydegitalActivity.this.findViewById(R.id.activity_storydegital_container), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                //设置layout在PopupWindow中显示的位置  )
            }
        });
        commentbtn=(ImageButton)findViewById(R.id.story_degital_comment);
        commentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StorydegitalActivity.this, CommentActivity.class);
                intent.putExtra("LOGINTOKEN",LoginToken);
                intent.putExtra("USERID",UserId);
                intent.putExtra("Username",UserName);
                intent.putExtra("Content",content);
                intent.putExtra("Time", time);
                intent.putExtra("Content" ,content);
                intent.putExtra("ID",Id);
                startActivity(intent);
            }
        });
        collectbtn = (ImageButton)findViewById(R.id.story_degital_collect);
        collectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isown){
                    isown=!isown;
                    collectbtn.setImageResource(R.drawable.collect);

                    RomauntNetWork romauntNetWork = new RomauntNetWork();
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {
                            Toast.makeText(StorydegitalActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(StorydegitalActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    romauntNetWork.storyCollect(LoginToken, Integer.toString(UserId), "0");

                }else{
                    isown=!isown;
                    collectbtn.setImageResource(R.drawable.collect_yellow);

                    RomauntNetWork romauntNetWork = new RomauntNetWork();
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {
                            Toast.makeText(StorydegitalActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(StorydegitalActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    romauntNetWork.storyCollect(LoginToken, Integer.toString(UserId), "1");

                }
            }
        });
        titleBar = (TitleBar) findViewById(R.id.degital_act_titlebar);

        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorydegitalActivity.this.finish();
            }
        });

        avater = (ImageView) findViewById(R.id.degital_act_avater);
        username = (TextView) findViewById(R.id.degital_act_writer);
        signatrue = (TextView) findViewById(R.id.degital_act_signaure);
        thumbNUM = (TextView) findViewById(R.id.degital_act_thumbNUM);
        lable = (TextView) findViewById(R.id.degital_act_lable);
        Content = (EditText) findViewById(R.id.degital_act_content);
    }

    private void setStoryInfo() {

        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                UserName = userInfoResponse.msg.user.userName;
                avaterurl = userInfoResponse.msg.user.avatar;
                sex = userInfoResponse.msg.user.sex;
                Signature = userInfoResponse.msg.user.sign;
                username.setText(UserName);
                signatrue.setText(Signature);
                followingEnable = userInfoResponse.msg.user.followingEnable;
                fansEnable = userInfoResponse.msg.user.followerEnable;
                noticeEnable = userInfoResponse.msg.user.noticeEnable;
                int userid = userInfoResponse.msg.user.id;
                /**storydegital 中不读取本地缓存 为的是更新用户头像和本地缓存 针对用户上传了新头像的情况*/
                if (!avaterurl.equals("")) {
                    try {
                        url = new URL(avaterurl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return;
                    }
                    Utils.onLoadImage(url, new Utils.OnLoadImageListener() {
                        @Override
                        public void OnLoadImage(Bitmap bitmap, String bitmapPath,int userid) {
                            if (bitmap != null) {
                                avater.setImageBitmap(bitmap);
                            }
                        }
                    },userid);
                }
            }

            @Override
            public void onError(Object error) {

            }
        });
        romauntNetWork1.getUserInfo(LoginToken, Integer.toString(UserId));
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                GetStoryResponse getStoryResponse = (GetStoryResponse) response;
                if (getStoryResponse.msg.story.publicEnable == 1) {
                    final int likeNUM = getStoryResponse.msg.likeCount;
                    Log.e("publicEnable", Integer.toString(getStoryResponse.msg.story.publicEnable));
                    content = getStoryResponse.msg.story.content;
                    title = getStoryResponse.msg.story.title;
                    final String flag = getStoryResponse.msg.story.flags;
                    isown = getStoryResponse.msg.story.isOwn;
                    time = datetotime(getStoryResponse.msg.story.createdAt);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleBar.setTitle(title);
                            thumbNUM.setText(Integer.toString(likeNUM));
                            Content.setText(content);
                            lable.setText(flag);
                            if(isown){
                                collectbtn.setImageResource(R.drawable.collect_yellow);
                            }
                        }
                    });

                } else {
                    Content.setText("该用户设置不可查看");
                }
            }

            @Override
            public void onError(Object error) {
            }
        });
        romauntNetWork.getStory(LoginToken, Id, Integer.toString(UserId));


    }
    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
}
