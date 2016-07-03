package com.woofer.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woofer.adapter.ViewPagerAdapter;

import woofer.com.test.R;

public class OtherUserHomePage extends AppCompatActivity {
    private ImageView img1,img2 ,img3;
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
        InitView();

    }
    private void InitView() {
        // TODO Auto-generated method stub
        img1 = (ImageView) findViewById(R.id.main_img1);
        img2 = (ImageView) findViewById(R.id.main_img2);
        img3 = (ImageView) findViewById(R.id.main_img3);
        clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.main_img1:
                        /*设置转场效果
                        * params two*/
                        vp.setCurrentItem(0,true);
                        break;
                    case R.id.main_img2:
                        vp.setCurrentItem(1,true);
                        break;
                    case R.id.main_img3:
                        vp.setCurrentItem(2,true);
                        break;
                    case R.id.main_img4:
                        vp.setCurrentItem(3,true);
                        break;
                }
            }
        };

}
