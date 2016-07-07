package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import com.woofer.activity.OtherUserHomePage;
import com.woofer.adapter.OtherUserHomePageTransfer;
import com.woofer.adapter.ParasAdapter;
import com.woofer.refreshlayout.model.ParhsModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import woofer.com.test.R;
public class ParhsActivity extends Activity{
    private ListView mDataLV;
    private int headofadapter = 0;
    private BroadcastReceiver mBroadcastReceiver;
    private List<ParhsModel> dataList;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parhs);
        mDataLV = (ListView)findViewById(R.id.OT_parhs_data);


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                dataList= OtherUserHomePage.otherUserHomePageTransfer.parhsList;
                mDataLV.setAdapter(new ParasAdapter(ParhsActivity.this,dataList));

                unregisterReceiver(mBroadcastReceiver);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zaizai1.broadcast.notifyParasGot");
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

}
