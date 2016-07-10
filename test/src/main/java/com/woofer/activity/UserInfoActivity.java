package com.woofer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.util.Utils;
import com.woofer.ui.CustomDialogsexchoose;
import com.woofer.ui.ImageText;
import com.woofer.ui.ImageTexttouxiang;
import com.woofer.ui.Texttextimg;

import java.net.MalformedURLException;
import java.net.URL;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class UserInfoActivity extends AppCompatActivity {
    private ImageTexttouxiang avatar;
    private ImageText nicheng;
    private ImageText msex;
    private Texttextimg sign;

    private ImageText imageText2;
    private ImageText imageText3;

    private TitleBar titleBar;

    private URL url;

    public static SharedPreferences.Editor editor;

    private String logintoken;
    private String username;
    private String avacterurl;
    private String signature;
    private int sex;
    private int updatenotice;
    private int noticeenable;
    private int followingenable;
    private int followerenable;
    private int aboutnotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final SharedPreferences sp  = getSharedPreferences("userinfo", MODE_PRIVATE);
        logintoken = sp.getString("LOGINTOKEN","");
        username = sp.getString("USERNAME","");
        avacterurl = sp.getString("AVATERURL", "");
        /**！！！！！！！*/
        signature = sp.getString("USERSIGN", "")+" ";
        sex = sp.getInt("SEX", 0);
        updatenotice = sp.getInt("UPDATENOTICE", 1);
        noticeenable = sp.getInt("NOTICEENABLE",1);
        followingenable = sp.getInt("FOLLOWINGENABLE",1);
        followerenable = sp.getInt("FOLLOWERENABLE",1);
        aboutnotice = sp.getInt("ABOUTENABLE",1);

        nicheng =(ImageText)findViewById(R.id.user_info_username);
        nicheng.setText(0, "昵称");
        nicheng.setText(1, sp.getString("USERNAME", ""));
        nicheng.setImgResource(R.drawable.btn_right);
        /**昵称 用户名*/
        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
                View conView = layoutInflater.inflate(R.layout.dialog_layoutforlabel, null);
                final EditText editText = (EditText)conView.findViewById(R.id.dialog_ET);
                TextView textView =(TextView)conView.findViewById(R.id.title);
                textView.setText("修改昵称");
                new AlertDialog.Builder(UserInfoActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)
                        .setView(conView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        username = editText.getText().toString();
                        nicheng.setText(1, username);
                        editor = sp.edit();
                        editor.putString("USERNAME", username);
                        editor.apply();
                        dialog.dismiss();

                        RomauntNetWork romauntNetWork = new RomauntNetWork();
                        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                if(response instanceof UserInfoResponse) {
                                    Toast.makeText(UserInfoActivity.this, "用户名更新成功", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent("com.zaizai1.broadcast.userInfoUpdated");
                                    sendBroadcast(i);
                                }else{
                                    Toast.makeText(UserInfoActivity.this, "用户名更新失败,请重试", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });
                        romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(sex), Integer.toString(updatenotice),
                                Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });


        msex =(ImageText)findViewById(R.id.user_info_sex);
        msex.setText(0, "性别");
        if(sex==1){
            msex.setText(1, "男");
        }else if(sex==2){
            msex.setText(1, "女");
        }else{
            msex.setText(1, "");
        }

        msex.setImgResource(R.drawable.btn_right);
        /**性别*/
        msex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogsexchoose.Builder builder = new CustomDialogsexchoose.Builder(UserInfoActivity.this);
                builder.setPositiveButton("男", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        msex.setText(1,"男");
                        editor = sp.edit();
                        editor.putInt("SEX", 1);
                        editor.apply();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RomauntNetWork romauntNetWork = new RomauntNetWork();
                                romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        if(response instanceof UserInfoResponse) {
                                            Toast.makeText(UserInfoActivity.this, "性别更新成功", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(UserInfoActivity.this, "性别更新失败,请重试", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Object error) {

                                    }
                                });
                                romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(1), Integer.toString(updatenotice),
                                        Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));
                            }
                        }).start();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("女",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                msex.setText(1,"女");
                                editor = sp.edit();
                                editor.putInt("SEX", 2);
                                editor.apply();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        RomauntNetWork romauntNetWork = new RomauntNetWork();
                                        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                            @Override
                                            public void onResponse(Object response) {
                                                if(response instanceof UserInfoResponse) {
                                                    Toast.makeText(UserInfoActivity.this, "性别更新成功", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    Toast.makeText(UserInfoActivity.this, "性别更新失败,请重试", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onError(Object error) {

                                            }
                                        });
                                        romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(2), Integer.toString(updatenotice),
                                                Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));
                                    }
                                }).start();
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }
        });

        /**头像*/
        avatar =(ImageTexttouxiang)findViewById(R.id.user_info_avatar);
        avatar.setText("头像");
        avatar.setImage(0, R.drawable.img_defaultavatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogsexchoose.Builder builder = new CustomDialogsexchoose.Builder(UserInfoActivity.this);
                builder.setPositiveButton("照相机", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("从相册中选择",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
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
                        avatar.getAvatter().setImageBitmap(bitmap);
                    }
                }
            });
        }

        imageText2=(ImageText)findViewById(R.id.user_info_IT3);
        imageText2.setText(0, "绑定手机号");
        imageText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        imageText3=(ImageText)findViewById(R.id.user_info_IT4);
        imageText3.setText(0,"绑定邮箱");
        imageText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, signatureActivity.class);
                //startActivityForResult();
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        titleBar=(TitleBar)findViewById(R.id.actionbar_userinfo);
        titleBar.setLeftImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        sign =(Texttextimg)findViewById(R.id.user_info_sign);
        sign.setText(0, "个性签名");

        sign.setText(1, signature);
        /**个性签名*/
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
                View conView = layoutInflater.inflate(R.layout.dialog_layoutforlabel, null);
                final EditText editText = (EditText)conView.findViewById(R.id.dialog_ET);
                TextView textView =(TextView)conView.findViewById(R.id.title);
                textView.setText("修改签名");
                new AlertDialog.Builder(UserInfoActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)
                        .setView(conView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        signature = editText.getText().toString();
                        if (!signature.equals("")) {
                            sign.setText(1, signature);
                            editor = sp.edit();
                            editor.putString("USERSIGN", signature);
                            editor.apply();
                            dialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    RomauntNetWork romauntNetWork = new RomauntNetWork();
                                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                        @Override
                                        public void onResponse(Object response) {
                                            if (response instanceof UserInfoResponse) {
                                                Toast.makeText(UserInfoActivity.this, "签名更新成功", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent("com.zaizai1.broadcast.userInfoUpdated");
                                                sendBroadcast(i);
                                            } else {
                                                Toast.makeText(UserInfoActivity.this, "签名更新失败 请重试 ", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onError(Object error) {

                                        }
                                    });
                                    romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(sex), Integer.toString(updatenotice),
                                            Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));
                                }
                            }).start();

                        } else {
                            Toast.makeText(UserInfoActivity.this, "签名不能为空", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }
}
