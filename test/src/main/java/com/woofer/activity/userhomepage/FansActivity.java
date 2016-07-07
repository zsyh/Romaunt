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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.woofer.activity.BaseActivity;
import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.signinActivity;
import com.woofer.adapter.ParasAdapter;
import com.woofer.adapter.fansAdapter;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.model.ParhsModel;
import com.woofer.refreshlayout.model.fansinfoModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import woofer.com.test.R;


public class FansActivity extends Activity{
    private ListView mDataLV;
    private List<Map<String,Object>> dataList;
    /**记录当前适listview中item个数 去重 为多步加载做准备*/

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_fans);
        mDataLV = (ListView)findViewById(R.id.data);



        //List<Map<String,Object>> list = getData();


       // mDataLV.setAdapter(new fansAdapter(this , list));

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                dataList= OtherUserHomePage.otherUserHomePageTransfer.fansList;
                mDataLV.setAdapter(new fansAdapter(FansActivity.this,dataList));

               // unregisterReceiver(mBroadcastReceiver);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zaizai1.broadcast.notifyFansGot");
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

}
