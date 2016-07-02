package com.woofer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.woofer.refreshlayout.util.Utils;
import com.woofer.ui.ImageText;
import com.woofer.ui.ImageTexttouxiang;
import com.woofer.ui.Texttextimg;

import java.net.MalformedURLException;
import java.net.URL;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class UserInfoActivity extends AppCompatActivity {
    private ImageText imageText;
    private ImageText imageText1;
    private ImageText imageText2;
    private ImageText imageText3;
    private ImageTexttouxiang imageTexttouxiang;
    private TitleBar titleBar;
    private Texttextimg texttextimg;
    private String avacterurl;
    private URL url;
    private int sexflag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        SharedPreferences sp  = getSharedPreferences("userinfo", MODE_PRIVATE);

        imageText=(ImageText)findViewById(R.id.user_info_IT1);
        imageText.setText(0, "昵称");
        imageText.setText(1, sp.getString("NICHENG", ""));
        imageText.setImgResource(R.drawable.btn_right);
        imageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        imageText1=(ImageText)findViewById(R.id.user_info_IT2);
        imageText1.setText(0, "性别");
        sexflag = sp.getInt("SEX",Integer.parseInt("3"));
        if(sexflag==0){
            imageText1.setText(1, "男");
        }else if(sexflag==1){
            imageText1.setText(1, "女");
        }else{
            imageText1.setText(1, "");
        }

        imageText1.setImgResource(R.drawable.btn_right);
        imageText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });


        imageTexttouxiang=(ImageTexttouxiang)findViewById(R.id.user_info_ITT1);
        imageTexttouxiang.setText("头像");
        imageTexttouxiang.setImage(0, R.drawable.img_defaultavatar);
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
                        imageTexttouxiang.getAvatter().setImageBitmap(bitmap);
                    }
                }
            });
        }
        imageTexttouxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        texttextimg=(Texttextimg)findViewById(R.id.user_info_qianming);
        texttextimg.setText(0,"个性签名");

        texttextimg.setText(1,sp.getString("USERSIGN",""));
        texttextimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfoActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
