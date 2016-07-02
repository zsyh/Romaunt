package com.woofer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.woofer.ui.CustomDialog;
import com.woofer.ui.ImageTextforconfig;
import com.woofer.userInfo;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class configActivity extends AppCompatActivity {
    private TitleBar titleBar;
    private ImageTextforconfig imageTextforconfig1;
    private ImageTextforconfig imageTextforconfig2;
    private ImageTextforconfig imageTextforconfig3;
    private ImageTextforconfig imageTextforconfig4;
    private ImageTextforconfig imageTextforconfig5;
    private ImageTextforconfig imageTextforconfig6;

    private Button btn1;
    private String  flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);
        initconpement();


    }
    private void initconpement(){
        titleBar=(TitleBar)findViewById(R.id.activity_config_titlebar);
        imageTextforconfig1=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext);

        imageTextforconfig2=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext2);
        imageTextforconfig3=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext3);
        imageTextforconfig4=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext4);
        imageTextforconfig5=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext5);
        imageTextforconfig6=(ImageTextforconfig)findViewById(R.id.activity_config_Imagetext6);
        imageTextforconfig1.setText("新消息通知");
        imageTextforconfig2.setText("清除缓存");
        imageTextforconfig3.setText("意见反馈");
        imageTextforconfig4.setText("关于我们");
        imageTextforconfig5.setText("检查更新");
        titleBar.setLeftImageResource(R.drawable.icon_return_white);
        titleBar.setLeft(10);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configActivity.this.finish();
            }
        });
        imageTextforconfig6.setText("喜欢我？五星好评鼓励一下");
        imageTextforconfig6.setTextColor();
        btn1=(Button)findViewById(R.id.activity_config_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo.status==0){
                    CustomDialog.Builder builder = new CustomDialog.Builder(configActivity.this);
                    builder.setMessage("您还没有登录,是否现在登录？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tosigninActivity();
                        }
                    });
                    builder.setNegativeButton("取消",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                    userInfo.status = 0;
                    Intent intent = new Intent(configActivity.this, MainActivity.class);
                    startActivity(intent);
                    configActivity.this.finish();
                }

            }
        });

    }
    private void tosigninActivity(){
        Intent intent = new Intent(configActivity.this, signinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        configActivity.this.finish();
    }
}
