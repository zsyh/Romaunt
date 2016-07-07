package com.woofer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.userInfo;

import woofer.com.test.R;

public class signinActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private String Tag ="signinAc";
    private Button buttonSignIn;
    private EditText password;
    private EditText phone;
    private String username;
    private String key;
    private String logintoken;
    private String token;
    private String userID;

    private SharedPreferences sp;
    private CheckBox storagekey;
    private CheckBox autologin;
    private CheckBox showkey;
    /*编辑sharedpreferences中的对象 用editor*/
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

         /*存储密码*/
        sp = this.getSharedPreferences("userinfo",MainActivity.MODE_PRIVATE);

        phone =(EditText)findViewById(R.id.signin_phone);

        password =(EditText)findViewById(R.id.signin_password);

        storagekey = (CheckBox)findViewById(R.id.signin_CB1);
        storagekey.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sp.edit().putBoolean("CBONECHECK",true).apply();
                }
                else {
                        /*选中保存密码则为false*/
                    sp.edit().putBoolean("CBONECHECK",false).apply();
                }
            }
        });

        autologin = (CheckBox)findViewById(R.id.signin_CB2);
        autologin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sp.edit().putBoolean("CBTWOCHECK",true).apply();
                }
                else{
                    sp.edit().putBoolean("CBTWOCHECK",false).apply();
                }
            }
        });

        showkey = (CheckBox)findViewById(R.id.signin_CB3);
        showkey.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imageButton = (ImageButton)findViewById(R.id.activity_login_ibtn1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signinActivity.this.finish();
            }
        });



        final RomauntNetWork romauntNetWork = new RomauntNetWork();

        buttonSignIn=(Button)findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = phone.getText().toString();
                key = password.getText().toString();
                if (username.equals("") | key.equals("")) {
                    Toast.makeText(signinActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {

                            LoginResponse loginResponse = (LoginResponse) response;
                            System.out.println("status:" + loginResponse.status);
                            System.out.println(loginResponse.msg.userID);
                            System.out.println("LoginToken:" + loginResponse.msg.LoginToken);
                            System.out.println("token:" + loginResponse.msg.token);

                            logintoken = loginResponse.msg.LoginToken;
                            token = loginResponse.msg.token;
                            userID = loginResponse.msg.userID;
                            userInfo.status = 1;
                            saveInfo();
                            Intent intent = new Intent(signinActivity.this, MainActivity.class);
                            startActivity(intent);
                            signinActivity.this.finish();


                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(signinActivity.this, "网络无连接，登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    romauntNetWork.login(username, key);
                }
            }
        });

        if(sp.getBoolean("CBONECHECK",false)){
            storagekey.setChecked(true);
            /*params 第一个为sharepreference 中的key 第二个是缺省值*/
            phone.setText(sp.getString("USERNAME",""));
            password.setText(sp.getString("PASSWORD",""));
            if(sp.getBoolean("CBTWOCHECK",false)){
                username = phone.getText().toString();
                key = password.getText().toString();
                if (username.equals("") | key.equals("")) {
                    Toast.makeText(signinActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {

                            LoginResponse loginResponse = (LoginResponse) response;
                            System.out.println("status:" + loginResponse.status);
                            System.out.println("userID:" + loginResponse.msg.userID);
                            System.out.println("LoginToken:" + loginResponse.msg.LoginToken);
                            System.out.println("token:" + loginResponse.msg.token);
                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(signinActivity.this, "网络无连接，登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    romauntNetWork.login(username, key);
                }
            }
        }
    }

    private void saveInfo(){
        if(storagekey.isChecked()){
            editor = sp.edit();
            userInfo.username = username;
            userInfo.key = key;
            editor.putString("USERID",userID );
            editor.putString("USERNAME", username);
            editor.putString("LOGINTOKEN",logintoken);
            editor.putString("TOKEN",token);


            editor.apply();
        }
    }




}