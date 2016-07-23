/*
 * Copyright (C) 2013 AChep@xda <artemchep@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.woofer.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.DiyScrollview.PullScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woofer.adapter.ActivitymyhomepageAdapter;

import woofer.com.test.R;

public class PersonHomeActivity extends Activity implements PullScrollView.OnTurnListener {
    /**就是监听ScrollView的滚动条设定头部的透明度
     */
    //private NotifyingScrollView scrollview;
    private LinearLayout layouthead;
    private ImageButton imageButton;
    private  ImageButton imageButton1;

    private  ImageView imageView;

    private PullScrollView mSrollView;
    private ImageView mHeadVIew;
    private TableLayout mMainLayout;
    private ImageView backgroundIMG ;
    private ViewPager vp;
    private ListView listView=null;
    private double a = 0.5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degitalinfo);


        listView=(ListView)findViewById(R.id.degitalifo_lv);
        listView.setDivider(null);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new ActivitymyhomepageAdapter(this, list));


        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        imageView=(ImageView)findViewById(R.id.user_avatar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonHomeActivity.this,"点击了头像", Toast.LENGTH_SHORT).show();
            }
        });
        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

        layouthead = (LinearLayout)findViewById(R.id.ll_head);
        layouthead.getBackground().setAlpha(0);

        imageButton = (ImageButton)findViewById(R.id.userinfo_return_Ibt);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonHomeActivity.this.finish();
            }
        });
        backgroundIMG=(ImageView)findViewById(R.id.background_img);
        imageButton1 = (ImageButton)findViewById(R.id.userinfo_more_ibt);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        initView();
        mSrollView.setOnScrollChangedListener(new PullScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl,
                                        int oldt) {

                //define it for scroll height
                /**设定至全部显示的高度差*/
                int lHeight = 500;
                if (t <= lHeight) {
                    int progress = (int) (new Float(t) / new Float(lHeight) * 255);
                    backgroundIMG.setAlpha(255-progress);
                    //layouthead.getBackground().setAlpha(255);
                } else {
                    backgroundIMG.setAlpha(0);
                    //layouthead.getBackground().setAlpha(200);
                }
                /*
                 *另一种效果
                 *
                 * if (t <= lHeight) {
                    int progress = (int) (new Float(t) / new Float(lHeight) * 225);

                } else {
                    layouthead.getBackground().setAlpha(225);
                }
                 * */

            }
        });
        showTable();
    }
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image",  R.drawable.img_warning);
            map.put("image1", R.drawable.img_warning);
            map.put("image2", R.drawable.img_warning);
            map.put("image3", R.drawable.img_warning);
            map.put("image4", R.drawable.img_warning);

            map.put("textView", "有一个");
            map.put("textView1", "14:14");
            map.put("textView2", "昵称");
            map.put("textView3", "快让自己的签名变得长一点吧");
            map.put("textView4", "从前有座山，山里有座庙，庙里有个老和尚和一个小和尚。");
            list.add(map);
        }
        return list;
    }

    protected   void initView(){
        mSrollView = (PullScrollView) findViewById(R.id.scroll_view);
        mHeadVIew = (ImageView)findViewById(R.id.background_img);
        mSrollView.setHeader(mHeadVIew);
        mSrollView.setOnTurnListener(this);
    }
    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {


            TableRow tableRow = new TableRow(this);
            TextView textView7 = new TextView(this);
            textView7.setText("魔花" + i);
            textView7.setTextSize(18);
            textView7.setTextColor(Color.rgb(0, 0, 0));

            textView7.setPadding(15, 0, 15, 0);
            tableRow.addView(textView7, layoutParams);
            tableRow.setBackgroundColor(Color.WHITE);

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonHomeActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);

            TableRow tableRow1 = new TableRow(this);
            TextView textView1 = new TextView(this);
            textView1.setText("Test pull down scrollvsfadasdsadsasadsadsadsadsadasdsadasdasdsadsaddasdiew " + i);
            textView1.setTextSize(16);
            textView1.setPadding(15, 0, 15, 15);
            tableRow1.addView(textView1, layoutParams);
            tableRow1.setBackgroundColor(Color.WHITE);

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PersonHomeActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });
            mMainLayout.addView(tableRow1);

//            TableRow tableRow2 = new TableRow(this);
//            //tableRow2.addView(textView2, layoutParams);
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(R.drawable.icon_reply_t);
//            imageView.setScaleX((float) a);
//            imageView.setScaleY((float) a);
//            imageView.setPadding(0, 0, 0, 0);
//
//            ImageView imageView1 = new ImageView(this);
//            imageView1.setImageResource(R.drawable.icon_thumb_t);
//            imageView1.setScaleX((float) a);
//            imageView1.setScaleY((float) a);
//            imageView1.setPadding(0, 0, 0, 0);
//
//
//            //tableRow2.addView(imageView);
//            tableRow2.addView(imageView1);
//            mMainLayout.addView(tableRow2);
            TableLayout mytable = (TableLayout) findViewById(R.id.degital_textTL);
                    int numberOfRow =1;
                   int numberOfColumn =12;
                    int cellDimension =24;
                    int cellPadding =200;
                    for (int row = 0;row<numberOfRow; row ++){
                        tableRow = new TableRow(this);
                        //tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                        tableRow.setLayoutParams(new TableLayout.LayoutParams((cellDimension + 2 * cellPadding) * numberOfColumn, cellDimension + 2 * cellPadding));


                        for (int column =0;column<numberOfColumn; column++){
                            if(column%3==0){
                                TextView textView = new TextView(this);
                                textView.setTextSize(20);
                                textView.setText("   ");
                                tableRow.addView(textView);
                            }
                            if(column%3==1){
                                if(column==1){
                                    ImageView imageView = new ImageView(this);
                                    imageView.setScaleX((float)0.5);
                                    imageView.setScaleY((float)0.5);
                                    imageView.setImageResource(R.drawable.icon_eye_t);
                                    tableRow.addView(imageView);
                                }
                                if(column==4){
                                    ImageView imageView = new ImageView(this);
                                    imageView.setScaleX((float)0.5);
                                    imageView.setScaleY((float)0.5);
                                    imageView.setImageResource(R.drawable.icon_thumb_t);
                                    tableRow.addView(imageView);
                                }
                                if(column==7){
                                    ImageView imageView = new ImageView(this);
                                    imageView.setScaleX((float)0.5);
                                    imageView.setScaleY((float)0.5);
                                    imageView.setImageResource(R.drawable.icon_reply_t);
                                    tableRow.addView(imageView);
                                }
                                if(column==10){
                                    ImageView imageView = new ImageView(this);
                                    imageView.setScaleX((float)0.5);
                                    imageView.setScaleY((float)0.5);
                                    imageView.setImageResource(R.drawable.icon_pencil_t);
                                    tableRow.addView(imageView);
                                }
                            }
                            if(column%3==2){
                                if(column==2){
                                    TextView textView = new TextView(this);
                                    textView.setText("8");
                                    tableRow.addView(textView);
                                }
                                if(column==5){
                                    TextView textView = new TextView(this);
                                    textView.setText("8");
                                    tableRow.addView(textView);
                                }
                                if(column==8){
                                    TextView textView = new TextView(this);
                                    textView.setText("8");
                                    tableRow.addView(textView);
                                }
                                if(column==11){
                                    TextView textView = new TextView(this);
                                    textView.setText("8");
                                    tableRow.addView(textView);
                                }
                            }

                       }
                        mytable.addView(tableRow,new TableLayout.LayoutParams((cellDimension+2*cellPadding)*numberOfColumn,cellDimension +2*cellPadding));
                        //mytable.addView(tableRow,new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                  }



        }
    }

    @Override
    public void onTurn() {

    }
}
