package com.woofer.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.woofer.adapter.ViewPagerAdapter;

import woofer.com.test.R;

public class OtherUserHomePage extends AppCompatActivity {
    private TextView fans,followers ,parhs;
    private TextView tv1 ,tv2 ,tv3;
    private ViewPager vp;

    private LocalActivityManager manager;
    private ViewPagerAdapter viewPageAdapter;
    private View.OnClickListener clickListener;
    private ViewPager.OnPageChangeListener pageChangeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_home_page);
        Intent intent = getIntent();

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        vp = (ViewPager) findViewById(R.id.OT_home_viewpager);
        String Id = intent.getStringExtra("ID");
        int UserId = intent.getIntExtra("USERID", 0);
        tv1 = (TextView)findViewById(R.id.OT_home_tv1);
        tv2 = (TextView)findViewById(R.id.OT_home_tv2);
        tv3 = (TextView)findViewById(R.id.OT_home_tv3);
        InitView();

    }
    private void InitView() {
        // TODO Auto-generated method stub
        fans = (TextView) findViewById(R.id.OT_home_tv_fans);
        followers = (TextView) findViewById(R.id.OT_home_tv_following);
        parhs = (TextView) findViewById(R.id.OT_home_tv_parhs);
        clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.OT_home_tv_fans:
                        /*设置转场效果
                        * params two*/
                        vp.setCurrentItem(0, true);
                        break;
                    case R.id.OT_home_tv_following:
                        vp.setCurrentItem(1, true);
                        break;
                    case R.id.OT_home_tv_parhs:
                        vp.setCurrentItem(2, true);
                        break;
                }
            }
        };
        fans.setOnClickListener(clickListener);
        followers.setOnClickListener(clickListener);
        parhs.setOnClickListener(clickListener);
        InitPager();
    }

    private void InitPager(){
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tv1.setBackgroundColor(rgb.);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }
    }

}
