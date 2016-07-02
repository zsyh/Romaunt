package com.woofer.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;

public class ImageTextforconfig extends RelativeLayout{
    private TextView textView1;
    private ImageView imageView1;
    public ImageTextforconfig(Context context){
        super(context, null);
    }

    public ImageTextforconfig(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.img_text_config, this, true);
        this.textView1=(TextView)findViewById(R.id.img_text_TV1);
        this.imageView1=(ImageView)findViewById(R.id.img_text_IV1);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceID){
        this.imageView1.setImageResource(resourceID);
    }
    public void setText( String text){
                this.textView1.setText(text);
    }
    public void setTextColor(){
        this.textView1.setTextColor(Color.rgb(141,212,240));
    }

}
