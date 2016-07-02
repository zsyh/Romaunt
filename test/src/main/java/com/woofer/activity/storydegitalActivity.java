package com.woofer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import woofer.com.test.R;

import com.woofer.net.GetStoryResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.titlebar.TitleBar;

public class storydegitalActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storydegital);

        Initcompement();

        Intent intent  = getIntent();
        Id = intent.getStringExtra("ID");
        UserId = intent.getIntExtra("USERID", 0);
        LoginToken =intent.getStringExtra("LoginToken");
        setStoryInfo();

    }
    private void setStoryInfo(){
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                GetStoryResponse getStoryResponse = (GetStoryResponse) response;
                final int likeNUM = getStoryResponse.msg.likeCount;
                final String content = getStoryResponse.msg.story.content;
                final String title = getStoryResponse.msg.story.title;
                final String flag = getStoryResponse.msg.story.flags;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        titleBar.setTitle(title);
                        thumbNUM.setText(Integer.toString(likeNUM));
                        Content.setText(content);
                        lable.setText(flag);
                    }
                });

            }

            @Override
            public void onError(Object error) {
            }
        });
        romauntNetWork.getStory(LoginToken, Id, Integer.toString(UserId));
        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                String UserName = userInfoResponse.msg.user.userName;
                avaterurl = userInfoResponse.msg.user.avatar;
                Signature = userInfoResponse.msg.user.sign;
                username.setText(UserName);
                signatrue.setText(Signature);

            }

            @Override
            public void onError(Object error) {

            }
        });
        romauntNetWork1.getUserInfo(LoginToken,Integer.toString(UserId));
    }


    private void  Initcompement(){
        titleBar =(TitleBar)findViewById(R.id.degital_act_titlebar);

        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storydegitalActivity.this.finish();
            }
        });

        avater  = (ImageView)findViewById(R.id.degital_act_avater);
        username = (TextView)findViewById(R.id.degital_act_writer);
        signatrue = (TextView)findViewById(R.id.degital_act_signaure);
        thumbNUM = (TextView)findViewById(R.id.degital_act_thumbNUM);
        lable = (TextView)findViewById(R.id.degital_act_lable);
        Content = (EditText)findViewById(R.id.degital_act_content);


    }
}
