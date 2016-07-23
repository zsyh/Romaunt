package com.woofer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.ui.CustomDialog;
import com.woofer.ui.imagetextswichbtn;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class ConfigActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private Button btn1;
    private String  flag;
    private imagetextswichbtn fansavailble;
    private imagetextswichbtn followingavailble;
    private imagetextswichbtn noticethumb;
    private imagetextswichbtn noticecommit;
    private imagetextswichbtn noticeislike;
    private imagetextswichbtn syncinwifionly;

    /**为了减少网络的访问 用了多个全局变量 在最后finish当前activity的时候统一上传*/
    private int NOTICEENABLE;
    private int FOLLOWINGENABLE;
    private int FOLLOWERENABLE;
    private int ABOUTENABLE;
    private int UPDATENOTICE;

    public static SharedPreferences.Editor editor;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
         sp  = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
        editor=sp.edit();
        initconpement();
    }
    private void initconpement(){

        fansavailble = (imagetextswichbtn)findViewById(R.id.config_fans_availble);
        followingavailble = (imagetextswichbtn)findViewById(R.id.config_following_availble);
        noticethumb = (imagetextswichbtn)findViewById(R.id.config_notice_thumb);
        noticecommit = (imagetextswichbtn)findViewById(R.id.config_notice_commit);
        noticeislike = (imagetextswichbtn)findViewById(R.id.config_notice_storyislike);
        syncinwifionly = (imagetextswichbtn)findViewById(R.id.config_notice_wifistnc_only);
        fansavailble.setText("是否公开粉丝");
        fansavailble.setImage(R.drawable.fansopen);
        followingavailble.setText("是否公开关注的人");
        followingavailble.setImage(R.drawable.openfollowing);
        noticethumb.setText("是否通知赞");
        noticethumb.setImage(R.drawable.noticethumb);
        noticecommit.setText("是否通知评论");
        noticecommit.setImage(R.drawable.noticecomment);

        noticeislike.setText("是否通知收藏");
        noticeislike.setImage(R.drawable.noticecollect);
        syncinwifionly.setText("仅wifi下上传");
        syncinwifionly.setImage(R.drawable.wifisynconly);

        NOTICEENABLE = sp.getInt("NOTICEENABLE", 1);
        FOLLOWINGENABLE = sp.getInt("FOLLOWINGENABLE", 1);
        FOLLOWERENABLE = sp.getInt("FOLLOWERENABLE", 1);
        ABOUTENABLE = sp.getInt("ABOUTENABLE",1);
        UPDATENOTICE = sp.getInt("UPDATENOTICE",1);

        if(FOLLOWERENABLE==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                fansavailble.mSwitch.setChecked(true);
            }
        }
        if(FOLLOWINGENABLE==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                followingavailble.mSwitch.setChecked(true);
            }
        }
        /**这三个对应的不清楚是哪一个：
         * 收到赞
         * 收到评论
         * 文章被收藏
         */
        if(NOTICEENABLE==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                noticethumb.mSwitch.setChecked(true);
            }
        }

        if(ABOUTENABLE==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                noticeislike.mSwitch.setChecked(true);
            }
        }
        if(UPDATENOTICE==1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                noticecommit.mSwitch.setChecked(true);
            }
        }

        fansavailble.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FOLLOWERENABLE =1-FOLLOWERENABLE;
                    editor.putInt("FOLLOWERENABLE" ,1);
                    editor.apply();
                }else{
                    FOLLOWERENABLE =1-FOLLOWERENABLE;

                    editor.putInt("FOLLOWERENABLE" ,0);
                    editor.apply();
                }

            }
        });
        followingavailble.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FOLLOWINGENABLE = 1-FOLLOWINGENABLE;

                    editor.putInt("FOLLOWINGENABLE" ,1);
                    editor.apply();
                }else{
                    FOLLOWINGENABLE = 1-FOLLOWINGENABLE;

                    editor.putInt("FOLLOWINGENABLE" ,0);
                    editor.apply();
                }
            }
        });
        noticethumb.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NOTICEENABLE = 1-NOTICEENABLE;

                    editor.putInt("NOTICEENABLE" ,1);
                    editor.apply();
                }else{
                    NOTICEENABLE = 1-NOTICEENABLE;

                    editor.putInt("NOTICEENABLE" ,0);
                    editor.apply();
                }
            }
        });
        noticecommit.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UPDATENOTICE = 1-UPDATENOTICE;

                    editor.putInt("UPDATENOTICE",1);
                    editor.apply();
                }else{
                    UPDATENOTICE = 1-UPDATENOTICE;

                    editor.putInt("UPDATENOTICE",0);
                    editor.apply();
                }
            }
        });
        noticeislike.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ABOUTENABLE = 1-ABOUTENABLE;

                    editor.putInt("ABOUTENABLE",1);
                    editor.apply();
                }else{
                    ABOUTENABLE = 1-ABOUTENABLE;

                    editor.putInt("ABOUTENABLE",0);
                    editor.apply();
                }
            }
        });
        syncinwifionly.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    editor.apply();
                }else{

                    editor.apply();
                }
            }
        });
        titleBar = (TitleBar) findViewById(R.id.activity_config_titlebar);
        titleBar.setLeftImageResource(R.drawable.icon_return_white);
        titleBar.setLeft(10);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigActivity.this.finish();
            }
        });
        btn1=(Button)findViewById(R.id.activity_config_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sp.getString("TOKEN","").equals("")){
                    CustomDialog.Builder builder = new CustomDialog.Builder(ConfigActivity.this);
                    builder.setMessage("是否确认退出登录？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            editor.putString("LOGINTOKEN","");
                            editor.putString("TOKEN","");
                            editor.putString("USERNAME","");
                            editor.putString("USERSIGN","");
                            editor.apply();
                            dialog.dismiss();
                            Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            ConfigActivity.this.finish();

                        }
                    });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                  //token为空，即不处于登录状态
                    Toast.makeText(ConfigActivity.this,"您尚未登录!",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
//    private void tosigninActivity(){
//        Intent intent = new Intent(ConfigActivity.this, SigninActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        ConfigActivity.this.finish();
//
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

                    RomauntNetWork romauntNetWork = new RomauntNetWork();
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {
                            if (response instanceof UserInfoResponse) {
                                Toast.makeText(ConfigActivity.this, "设置更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ConfigActivity.this, "设置更新失败,请重试", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Object error) {

                        }
                    });
                    String logintoken = sp.getString("LOGINTOKEN","");
                    String username = sp.getString("USERNAME","");
                    String avacterurl = sp.getString("AVATERURL","");
                    String signature = sp.getString("USERSIGN"," ");
                    int sex = sp.getInt("SEX",1);

                    romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(sex), Integer.toString(UPDATENOTICE),
                            Integer.toString(NOTICEENABLE), Integer.toString(FOLLOWINGENABLE), Integer.toString(FOLLOWERENABLE), Integer.toString(ABOUTENABLE));

                this.finish();
        }
        return true;
    }
}
