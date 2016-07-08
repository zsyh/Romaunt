package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.woofer.activity.BaseActivity;
import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.signinActivity;
import com.woofer.activity.storydegitalActivity;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.adapter.fansAdapter;
import com.woofer.refreshlayout.model.fansinfoModel;
import com.woofer.net.RomauntNetworkCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import woofer.com.test.R;


public class FollowingsActivity extends Activity{
    private ListView mDataLV;
    private List<Map<String,Object>> dataList;
    private BroadcastReceiver mBroadcastReceiver;
    private int followingEnable;
    private RelativeLayout relativeLayout;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        relativeLayout = (RelativeLayout)findViewById(R.id.OT_home_follow_Rlayout);

        mDataLV = (ListView) findViewById(R.id.OT_fans_data);

        SharedPreferences sp1 = getSharedPreferences("ENABLE", storydegitalActivity.MODE_PRIVATE);
        followingEnable = sp1.getInt("FOLLOWINGENABLE", 1);
        if(followingEnable==0){
            mDataLV.setBackgroundResource(R.drawable.followingunavalible);
        }
        else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    dataList= OtherUserHomePage.otherUserHomePageTransfer.followingList;
                    mDataLV.setAdapter(new com.woofer.adapter.fansAdapter(FollowingsActivity.this,dataList));

                    // unregisterReceiver(mBroadcastReceiver);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.zaizai1.broadcast.notifyFollowingsGot");
            registerReceiver(mBroadcastReceiver, intentFilter);

        }
    }
}