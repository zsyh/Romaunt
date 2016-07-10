package com.woofer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;

public class Followbtnstyle extends RelativeLayout{
    private TextView textView1;
    private ImageView imageView1;
   public Followbtnstyle(Context context){
        super(context, null);
    }

    public Followbtnstyle(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.followbtnstyle, this, true);
        this.imageView1=(ImageView)findViewById(R.id.imgtextswitch_pic);
        this.textView1=(TextView)findViewById(R.id.imgtextswitch_hint);
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
