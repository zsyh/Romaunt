package com.woofer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;

public class imagetextimage extends RelativeLayout{
    private TextView textView1;
    private ImageView imageView1;
    private ImageView imageView2;
    public imagetextimage(Context context){
        super(context, null);
    }

    public imagetextimage(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.img_text_img, this, true);
        this.imageView1=(ImageView)findViewById(R.id.act_four_img);
        this.textView1=(TextView)findViewById(R.id.act_four_text);
        this.imageView2=(ImageView)findViewById(R.id.img_text_touxiang_IV2);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setText(String text){
        this.textView1.setText(text);
    }
    public void setImage(int resourseID){
        this.imageView1.setImageResource(resourseID);
    }
}
