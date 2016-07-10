package com.woofer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.woofer.net.LoginResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.StatusFalseResponse;

import woofer.com.test.R;

public class LoginActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private Button loginbtn;
    private EditText usernameET;
    private EditText passwordET;
    private EditText passwordreET;
    private EditText pheneNUMET;
    private String username;
    private String password;
    private String passwordre;
    private String phone;

    private RomauntNetWork romauntNetWork=new RomauntNetWork();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText)findViewById(R.id.login_username);
        pheneNUMET = (EditText)findViewById(R.id.login_phoneNUM);
        passwordET = (EditText)findViewById(R.id.login_key);
        passwordreET = (EditText)findViewById(R.id.login_rekey);
        loginbtn = (Button)findViewById(R.id.login_loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameET.getText().toString();
                phone = pheneNUMET.getText().toString();
                password = passwordET.getText().toString();
                passwordre = passwordreET.getText().toString();
                if(!passwordre.equals(password) ){
                    //对话框
                }else{


                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                        public void onResponse(Object response) {

                            if(response instanceof LoginResponse){
                                LoginResponse loginResponse=(LoginResponse)response;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            else if(response instanceof StatusFalseResponse){
                                StatusFalseResponse statusFalseResponse=(StatusFalseResponse)response;
                                System.out.println("status:"+statusFalseResponse.status);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this,"注册失败,该用户名已被注册",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else {
                                Log.e("Romaunt","注册时返回对象类型不符");
                            }

                        }

                        @Override
                        public void onError(Object error) {
                            Toast.makeText(LoginActivity.this,"网络无连接，注册失败",Toast.LENGTH_SHORT).show();

                        }
                    });

                    romauntNetWork.register(phone,
                            password,
                            username,
                            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1463815936&di=c39284f832381f8e887e2e690f5f48e5&src=http://www.yooyoo360.com/photo/2009-1-1/20090112130427812.jpg"
                    );



                }


            }
        });

        imageButton=(ImageButton)findViewById(R.id.activity_login_ibtn1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });



    }
}
