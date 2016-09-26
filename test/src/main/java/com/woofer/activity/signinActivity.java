package com.woofer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;

import woofer.com.test.R;

public class SigninActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private String Tag ="signinAc";
    private Button buttonSignIn;
    private EditText password;
    private EditText phone;
    private String username;
    private LinearLayout shake_phone;
    private LinearLayout shake_pwd;



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
        sp = this.getSharedPreferences("userinfo",SigninActivity.MODE_PRIVATE);

        phone =(EditText)findViewById(R.id.signin_phone);

        shake_phone = (LinearLayout)findViewById(R.id.signin_shake_phone);
        shake_pwd = (LinearLayout)findViewById(R.id.signin_shake_pwd);

        password =(EditText)findViewById(R.id.signin_password);

        storagekey = (CheckBox)findViewById(R.id.CheckBoxSavePwd);
        storagekey.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sp.edit().putBoolean("SavePwdChecked",true).apply();
                }
                else {
                        /*选中保存密码则为false*/
                    sp.edit().putBoolean("SavePwdChecked",false).apply();
                }
            }
        });



        showkey = (CheckBox)findViewById(R.id.CheckBoxShowPwd);


        /*存储密码*/
        sp = this.getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);

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

                SigninActivity.this.finish();
            }
        });



        final RomauntNetWork romauntNetWork = new RomauntNetWork();

        buttonSignIn=(Button)findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key;
                username = phone.getText().toString();
                key = password.getText().toString();
                if (username.equals("") | key.equals("")) {
                    Toast.makeText(SigninActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {

                            if(response==null) {
                                //用户名或密码错误
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shake_phone.startAnimation(AnimationUtils.loadAnimation(SigninActivity.this,R.anim.shake));
                                        shake_pwd.startAnimation(AnimationUtils.loadAnimation(SigninActivity.this,R.anim.shake));
                                        Toast.makeText(SigninActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            if(response instanceof LoginResponse) {
                                LoginResponse loginResponse = (LoginResponse) response;
                                System.out.println("status:" + loginResponse.status);
                                System.out.println(loginResponse.msg.userID);
                                System.out.println("LoginToken:" + loginResponse.msg.LoginToken);
                                System.out.println("token:" + loginResponse.msg.token);



                                String logintoken = loginResponse.msg.LoginToken;
                                String token = loginResponse.msg.token;
                                String userID = loginResponse.msg.userID;

                                editor = sp.edit();
                                editor.putString("USERID", userID);
                                editor.putString("phone", username);
                                editor.putString("LOGINTOKEN", logintoken);
                                editor.putString("TOKEN", token);

                                editor.apply();

                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                SigninActivity.this.finish();
                            }
                            else {
                                Log.e("Romaunt","登录时response类型不符合LoginResponse");
                            }


                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(SigninActivity.this, "网络无连接，登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    romauntNetWork.login(username, key);
                }
            }
        });

//        if(sp.getBoolean("CBONECHECK",false)){
//            storagekey.setChecked(true);
//            /*params 第一个为sharepreference 中的key 第二个是缺省值*/
//            phone.setText(sp.getString("USERNAME",""));
//            password.setText(sp.getString("PASSWORD",""));


//            if(sp.getBoolean("CBTWOCHECK",false)){
//                username = phone.getText().toString();
//                key = password.getText().toString();
//                if (username.equals("") | key.equals("")) {
//                    Toast.makeText(SigninActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
//                } else {
//                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
//                        @Override
//                        public void onResponse(Object response) {
//
//                            LoginResponse loginResponse = (LoginResponse) response;
//                            System.out.println("status:" + loginResponse.status);
//                            System.out.println("userID:" + loginResponse.msg.userID);
//                            System.out.println("LoginToken:" + loginResponse.msg.LoginToken);
//                            System.out.println("token:" + loginResponse.msg.token);
//                        }
//
//                        @Override
//                        public void onError(Object error) {
//                            Toast.makeText(SigninActivity.this, "网络无连接，登录失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    romauntNetWork.login(username, key);
//                }
//            }
//        }


    }





}