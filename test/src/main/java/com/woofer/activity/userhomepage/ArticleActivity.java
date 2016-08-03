package com.woofer.activity.userhomepage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.SigninActivity;
import com.woofer.activity.StorydegitalActivity;
import com.woofer.adapter.ParasAdapter;
import com.woofer.refreshlayout.model.ParhsModel;

import java.util.List;

import woofer.com.test.R;
public class ArticleActivity extends Activity{
    private ListView mDataLV;
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
                mDataLV.setAdapter(new ParasAdapter(ArticleActivity.this,dataList));

                unregisterReceiver(mBroadcastReceiver);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zaizai1.broadcast.notifyParasGot");
        registerReceiver(mBroadcastReceiver, intentFilter);
        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sp = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
                SharedPreferences sp1 = getSharedPreferences("USERID", OtherUserHomePage.MODE_PRIVATE);
                Intent intent = new Intent(ArticleActivity.this, StorydegitalActivity.class);
                intent.putExtra("USERID", sp1.getInt("USERID",0));
                intent.putExtra("ID", dataList.get(position).storyid);
                intent.putExtra("LoginToken", sp.getString("LOGINTOKEN", ""));
                startActivity(intent);
            }
        });
    }
}
