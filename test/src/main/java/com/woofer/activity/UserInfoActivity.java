package com.woofer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
import com.woofer.ui.CustomDialogsexchoose;
import com.woofer.ui.ImageText;
import com.woofer.ui.ImageTexttouxiang;
import com.woofer.ui.Texttextimg;

import java.io.File;
import java.net.URL;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class UserInfoActivity extends AppCompatActivity {
    private ImageTexttouxiang item_avatar;
    private ImageText item_username;
    private ImageText item_sex;
    private Texttextimg item_sign;

    private ImageText item_linkphonenum;
    private ImageText item_linkemail;

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
    private int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final SharedPreferences UserinfoSp  = getSharedPreferences("userinfo", MODE_PRIVATE);
        logintoken = UserinfoSp.getString("LOGINTOKEN","");
        username = UserinfoSp.getString("USERNAME","");
        avacterurl = UserinfoSp.getString("AVATERURL", "");
        userid = UserinfoSp.getInt("userID",0);
        /**！！！！！！！*/
        signature = UserinfoSp.getString("USERSIGN", "")+" ";
        sex = UserinfoSp.getInt("SEX", 0);
        updatenotice = UserinfoSp.getInt("UPDATENOTICE", 1);
        noticeenable = UserinfoSp.getInt("NOTICEENABLE",1);
        followingenable = UserinfoSp.getInt("FOLLOWINGENABLE",1);
        followerenable = UserinfoSp.getInt("FOLLOWERENABLE",1);
        aboutnotice = UserinfoSp.getInt("ABOUTENABLE",1);

        item_username =(ImageText)findViewById(R.id.user_info_username);
        item_username.setText(0, "昵称");
        item_username.setText(1, UserinfoSp.getString("USERNAME", ""));
        item_username.setImgResource(R.drawable.btn_right);
        /**昵称 用户名*/
        item_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserinfoSp.getString("TOKEN", "").equals("")) {
                    Toast.makeText(UserInfoActivity.this, "你尚未登录!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
                View conView = layoutInflater.inflate(R.layout.dialog_layoutforlabel, null);
                final EditText editText = (EditText) conView.findViewById(R.id.dialog_ET);
                TextView textView = (TextView) conView.findViewById(R.id.title);
                textView.setText("修改昵称");
                new AlertDialog.Builder(UserInfoActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)
                        .setView(conView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        username = editText.getText().toString();
                        item_username.setText(1, username);
                        editor = UserinfoSp.edit();
                        editor.putString("USERNAME", username);
                        editor.apply();
                        dialog.dismiss();

                        RomauntNetWork romauntNetWork = new RomauntNetWork();
                        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                if (response instanceof UserInfoResponse) {
                                    Toast.makeText(UserInfoActivity.this, "用户名更新成功", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent("com.zaizai1.broadcast.userInfoUpdated");
                                    sendBroadcast(i);
                                } else {
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


        item_sex =(ImageText)findViewById(R.id.user_info_sex);
        item_sex.setText(0, "性别");
        if(sex==1){
            item_sex.setText(1, "男");
        }else if(sex==2){
            item_sex.setText(1, "女");
        }else{
            item_sex.setText(1, "");
        }

        item_sex.setImgResource(R.drawable.btn_right);
        /**性别*/
        item_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserinfoSp.getString("TOKEN", "").equals("")) {
                    Toast.makeText(UserInfoActivity.this, "你尚未登录!", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomDialogsexchoose.Builder builder = new CustomDialogsexchoose.Builder(UserInfoActivity.this);
                builder.setPositiveButton("男", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        item_sex.setText(1, "男");
                        editor = UserinfoSp.edit();
                        editor.putInt("SEX", 1);
                        editor.apply();

                        RomauntNetWork romauntNetWork = new RomauntNetWork();
                        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                if (response instanceof UserInfoResponse) {
                                    Toast.makeText(UserInfoActivity.this, "性别更新成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserInfoActivity.this, "性别更新失败,请重试", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });
                        romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(1), Integer.toString(updatenotice),
                                Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("女",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                item_sex.setText(1, "女");
                                editor = UserinfoSp.edit();
                                editor.putInt("SEX", 2);
                                editor.apply();

                                RomauntNetWork romauntNetWork = new RomauntNetWork();
                                romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        if (response instanceof UserInfoResponse) {
                                            Toast.makeText(UserInfoActivity.this, "性别更新成功", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(UserInfoActivity.this, "性别更新失败,请重试", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Object error) {

                                    }
                                });
                                romauntNetWork.updateUserInfo(logintoken, username, avacterurl, signature, Integer.toString(2), Integer.toString(updatenotice),
                                        Integer.toString(noticeenable), Integer.toString(followingenable), Integer.toString(followerenable), Integer.toString(aboutnotice));

                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }
        });

        /**头像*/
        item_avatar =(ImageTexttouxiang)findViewById(R.id.user_info_avatar);
        item_avatar.setText("头像");
        item_avatar.setImage(0, R.drawable.img_defaultavatar);
        item_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserinfoSp.getString("TOKEN", "").equals("")) {
                    Toast.makeText(UserInfoActivity.this, "你尚未登录!", Toast.LENGTH_SHORT).show();
                    return;
                }
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
            File imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
            if (imgfile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
                item_avatar.getAvatter().setImageBitmap(bmp);

            } else {
                /*try {
                    url = new URL(avacterurl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
>>>>>>> origin/develop
                }
                Utils.onLoadImage(url, new Utils.OnLoadImageListener() {
                    @Override
                    public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                        if (bitmap != null) {
                            item_avatar.getAvatter().setImageBitmap(bitmap);
                        }
                    }
                }, userid);*/
            }
        }

        item_linkphonenum =(ImageText)findViewById(R.id.user_info_IT3);
        item_linkphonenum.setText(0, "绑定手机号");
        item_linkphonenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        item_linkemail =(ImageText)findViewById(R.id.user_info_IT4);
        item_linkemail.setText(0, "绑定邮箱");
        item_linkemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, SignatureActivity.class);
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
        item_sign =(Texttextimg)findViewById(R.id.user_info_sign);
        item_sign.setText(0, "个性签名");

        item_sign.setText(1, signature);
        /**个性签名*/
        item_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserinfoSp.getString("TOKEN", "").equals("")) {
                    Toast.makeText(UserInfoActivity.this, "你尚未登录!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater layoutInflater = LayoutInflater.from(UserInfoActivity.this);
                View conView = layoutInflater.inflate(R.layout.dialog_layoutforlabel, null);
                final EditText editText = (EditText) conView.findViewById(R.id.dialog_ET);
                TextView textView = (TextView) conView.findViewById(R.id.title);
                textView.setText("修改签名");
                new AlertDialog.Builder(UserInfoActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)
                        .setView(conView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        signature = editText.getText().toString();
                        if (!signature.equals("")) {
                            item_sign.setText(1, signature);
                            editor = UserinfoSp.edit();
                            editor.putString("USERSIGN", signature);
                            editor.apply();
                            dialog.dismiss();

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
