package com.woofer.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.woofer.Bitmaptools.BitmapTools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSpanEdit extends EditText{

    public StringSpanEdit(Context context){
        super(context);
    }

    public StringSpanEdit(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /*
    * 往文本里插入图片
    * */
    public void insertImgInText(Bitmap bitmap, String imgpath){
        /*getBitMap*/
        ImageSpan imageSpan =new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);
        SpannableString ss=new SpannableString(imgpath);
        /* @param 插入对象, 起始位置，终止位置，标记
        * Spannable.SPAN_INCLUSIVE_EXCLUSIVE 会删除缓冲区中原有的imgpath*/
        ss.setSpan(imageSpan, 0, imgpath.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
        append("\n");
        /*设置光标在最后显示*/
        setSelection(this.getText().toString().length());
        setSpanContext(this.getText().toString());
    }

    /*为文本框设置图文混排的内容
    * @param contect 要设置的内容
    * */
    public void setSpanContext(String context){

       /* String patternStr = Environment.getExternalStorageDirectory()
							+ "/" +EditNoteActivity.IMG_DIR + "/.+?\\.\\w{3}";
        Pattern pattern = Pattern.compile(patternStr);
        *//**模板匹配*//*
        Matcher m = pattern.matcher(context);
        *//**富文本对象*//*
        SpannableString ss = new SpannableString(context);
        while(m.find()){
            Bitmap bmp = BitmapFactory.decodeFile(m.group());
            Bitmap bitmap = BitmapTools.getScaleBitmap(bmp, 0.2f, 0.2f);
            if(bmp != null){
                bmp.recycle();
            }
            ImageSpan imgSpan = new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);
            ss.setSpan(imgSpan, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        this.setText(ss);*/


        String patternStr =Environment.getExternalStorageDirectory()+"(.*jpg)";
                //"/external/images/media/60302";//
        Pattern pattern = Pattern.compile(patternStr);
        //*模板匹配
        Matcher m = pattern.matcher(context);
        //*富文本对象
        SpannableString ss = new SpannableString(context);
        while(m.find()){
            Bitmap bmp = BitmapFactory.decodeFile(m.group());
            Bitmap bitmap = BitmapTools.getScaleBitmap(bmp, 0.5f, 0.5f);
            if(bmp != null){
                bmp.recycle();
            }
            ImageSpan imgSpan = new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);
            ss.setSpan(imgSpan, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        this.setText(ss);
    }
}
