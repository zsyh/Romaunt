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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.SigninActivity;
import com.woofer.activity.StorydegitalActivity;
import com.woofer.adapter.FansAdapter;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;

import java.util.List;
import java.util.Map;
import woofer.com.test.R;


public class FansActivity extends Activity{
    private ListView mDataLV;
    private int fansEnable ;
    private List<Map<String,Object>> dataList;
    private BroadcastReceiver mBroadcastReceiver;
    private BroadcastReceiver BroadcastReceiverFansListRefresh;
    private FansAdapter mfansAdapter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        unregisterReceiver(BroadcastReceiverFansListRefresh);
    }
    @Override
    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_fans);
        mDataLV = (ListView)findViewById(R.id.data);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        SharedPreferences EnableSp = getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);
        fansEnable = EnableSp.getInt("FANSENABLE", 1);
        if(fansEnable==0){
            mDataLV.setBackgroundResource(R.drawable.fansunavilible);
        }else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    dataList = OtherUserHomePage.otherUserHomePageTransfer.fansList;
                    mfansAdapter=new FansAdapter(FansActivity.this, dataList);
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

                }
            };
            IntentFilter intentFilterFansListRefresh = new IntentFilter();
            intentFilterFansListRefresh.addAction("com.zaizai1.broadcast.notifyFansRefresh");
            registerReceiver(BroadcastReceiverFansListRefresh, intentFilterFansListRefresh);
        }

        mDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences sp = getSharedPreferences("userinfo", SigninActivity.MODE_PRIVATE);
                final String LoginToken = sp.getString("LOGINTOKEN", "");
                RomauntNetWork romauntNetWork = new RomauntNetWork();
                romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                    @Override
                    public void onResponse(Object response) {
                        if (response instanceof UserInfoResponse) {
                            UserInfoResponse userInfoResponse = (UserInfoResponse) response;

                            SharedPreferences sp1 = FansActivity.this.getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.putInt("FOLLOWINGENABLE",  userInfoResponse.msg.user.followingEnable);
                            editor.putInt("FANSENABLE",  userInfoResponse.msg.user.followerEnable);
                            editor.apply();

                            Intent intent = new Intent(FansActivity.this, OtherUserHomePage.class);
                            intent.putExtra("LoginToken", LoginToken);
                            intent.putExtra("UserID", userInfoResponse.msg.user.id);
                            intent.putExtra("Avater", userInfoResponse.msg.user.avatar);
                            intent.putExtra("Sign", userInfoResponse.msg.user.sign);
                            intent.putExtra("Username", userInfoResponse.msg.user.userName);
                            intent.putExtra("Sex", userInfoResponse.msg.user.sex);
                            intent.putExtra("FOLLOWINGENABLE", userInfoResponse.msg.user.followingEnable);
                            intent.putExtra("FANSENABLE", userInfoResponse.msg.user.followerEnable);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onError(Object error) {
                        Toast.makeText(FansActivity.this, "网络连接失败，请检查您的网路", Toast.LENGTH_SHORT).show();
                    }
                });
                romauntNetWork.getUserInfo(LoginToken, Integer.toString((Integer) dataList.get(position).get("USERID")));

            }
        });
    }
}
