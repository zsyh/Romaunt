package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;
import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.storydegitalActivity;
import com.woofer.adapter.fansAdapter;

import java.util.List;
import java.util.Map;
import woofer.com.test.R;


public class FansActivity extends Activity{
    private ListView mDataLV;
    private int fansEnable ;
    private List<Map<String,Object>> dataList;
    private BroadcastReceiver mBroadcastReceiver;
    private BroadcastReceiver BroadcastReceiverFansListRefresh;
    private fansAdapter mfansAdapter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        unregisterReceiver(BroadcastReceiverFansListRefresh);
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
        SharedPreferences EnableSp = getSharedPreferences("ENABLE", storydegitalActivity.MODE_PRIVATE);
        fansEnable = EnableSp.getInt("FANSENABLE", 1);
        if(fansEnable==0){
            mDataLV.setBackgroundResource(R.drawable.fansunavilible);
        }else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    dataList = OtherUserHomePage.otherUserHomePageTransfer.fansList;
                    mfansAdapter=new fansAdapter(FansActivity.this, dataList);
                    mDataLV.setAdapter(mfansAdapter);
                    unregisterReceiver(mBroadcastReceiver);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.zaizai1.broadcast.notifyFansGot");
            registerReceiver(mBroadcastReceiver, intentFilter);


            BroadcastReceiverFansListRefresh = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mfansAdapter.notifyDataSetChanged();
                    Log.e("Romaunt","FansActivity的fanslist更新广播接收");
                }
            };
            IntentFilter intentFilterFansListRefresh = new IntentFilter();
            intentFilterFansListRefresh.addAction("com.zaizai1.broadcast.notifyFansRefresh");
            registerReceiver(BroadcastReceiverFansListRefresh, intentFilterFansListRefresh);





        }


    }
}
