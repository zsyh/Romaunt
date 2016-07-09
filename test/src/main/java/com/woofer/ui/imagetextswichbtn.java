package com.woofer.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import woofer.com.test.R;

public class imagetextswichbtn extends RelativeLayout{
    private TextView hint;
    private ImageView picture;
    public Switch mSwitch;
    public imagetextswichbtn(Context context){
        super(context, null);
    }

    @SuppressLint("WrongViewCast")
    public imagetextswichbtn(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.img_text_switchbtn, this, true);
        this.picture =(ImageView)findViewById(R.id.imgtextswitch_pic);
        this.hint =(TextView)findViewById(R.id.imgtextswitch_hint);
        this.mSwitch=(Switch)findViewById(R.id.imgtextswitch_switch);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setText(String text){
        this.hint.setText(text);
    }
    public void setImage(int resourseID){
        this.picture.setImageResource(resourseID);
    }
}
