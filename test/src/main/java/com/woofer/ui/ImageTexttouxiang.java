package com.woofer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;

public class ImageTexttouxiang extends RelativeLayout{
    private TextView textView1;
    private ImageView imageView1;
    private ImageView imageView2;
    public ImageTexttouxiang(Context context){
        super(context, null);
    }
    public ImageView getAvatter(){
        return imageView1;
    }
    public ImageTexttouxiang(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.img_text_touxiang, this, true);
        this.textView1=(TextView)findViewById(R.id.img_text__touxiang_TV1);
        this.imageView1=(ImageView)findViewById(R.id.img_text_touxiang_IV1);
        this.imageView2=(ImageView)findViewById(R.id.img_text_touxiang_IV2);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setText(String text){
        this.textView1.setText(text);
    }
    public void setImage(int id, int resourseID){
        switch (id){
            case 0:
                this.imageView1.setImageResource(resourseID);
                break;
            case 1:
                this.imageView2.setImageResource(resourseID);
                break;
        }

    }
}
