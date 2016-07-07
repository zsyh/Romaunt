package com.woofer.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import woofer.com.test.R;
import com.woofer.adapter.ViewPagerAdapter;


public class Activity_three extends Activity {
    private ListView listView=null;
    private long firstbacktime = 0;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ViewPager vp;


    private LocalActivityManager manager;
    private ViewPagerAdapter viewPagerAdapter;
    private OnClickListener clickListener;
    private OnPageChangeListener pageChangeListener;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RomauntAlarmTest","Activity_three onDestroy()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("RomauntAlarmTest","Activity_three onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RomauntAlarmTest","Activity_three onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RomauntAlarmTest","Activity_three onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RomauntAlarmTest","Activity_three onPause()");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_three);

        Log.e("RomauntAlarmTest","Activity_three initView()");

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        vp=(ViewPager)findViewById(R.id.vPager1);

      /*  listView=(ListView)findViewById(R.id.listview);
        listView.setDivider(null);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new ActivitymyhomepageAdapter(this, list));
*/
        InitView();

//        titleBar.setRightImageResource(R.drawable.img_add);
//        titleBar.rightButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
    private void InitView(){
        textView = (TextView)findViewById(R.id.act_three_inform);
        textView1 = (TextView)findViewById(R.id.act_three_privateletter);
        textView2 = (TextView)findViewById(R.id.act_three_informTV);
        textView2.setBackgroundColor(Color.rgb(255, 255, 255));
        textView3 = (TextView)findViewById(R.id.act_three_privateTV);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0);
            }
        });
        textView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });

        clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.act_three_inform:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.act_three_privateletter:
                        vp.setCurrentItem(1);
                        break;
                }
            }
        };
        InitPager();

    }
    private  void InitPager(){
        pageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        textView3.setBackgroundColor(Color.rgb(25, 142, 123));
                        textView2.setBackgroundColor(Color.rgb(255, 255, 255));
                        break;
                    case 1:
                        textView2.setBackgroundColor(Color.rgb(25, 142, 123));
                        textView3.setBackgroundColor(Color.rgb(255, 255, 255));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        AddActivitiesToViewPager();
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(pageChangeListener);
    }
    private void AddActivitiesToViewPager(){
        List<View> mViews = new ArrayList<View>();
        Intent intent = new Intent();

        intent.setClass(this, InformActivity.class);
        intent.putExtra("id", 1);
        mViews.add(getView("QualityActivity1", intent));

        intent.setClass(this, PrivateLetterActivity.class);
        intent.putExtra("id", 2);
        mViews.add(getView("QualityActivity2", intent));

        viewPagerAdapter = new ViewPagerAdapter(mViews);
        vp.setAdapter(viewPagerAdapter);


    }
    private View getView (String id, Intent intent){
        return manager.startActivity(id, intent).getDecorView();
    }


   /* public List<Map<String, Object>> getData(){
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
    }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long currentTime=System.currentTimeMillis();
            if(currentTime - firstbacktime <= 2000){
                this.finish();
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstbacktime = currentTime;
            }
        }
        return true;
    }
}
