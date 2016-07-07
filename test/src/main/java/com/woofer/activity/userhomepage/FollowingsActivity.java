package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.woofer.activity.BaseActivity;
import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.signinActivity;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        mDataLV = (ListView) findViewById(R.id.OT_fans_data);




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