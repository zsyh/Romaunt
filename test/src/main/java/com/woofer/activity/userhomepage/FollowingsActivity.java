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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.woofer.activity.OtherUserHomePage;
import com.woofer.activity.SigninActivity;
import com.woofer.activity.StorydegitalActivity;

import java.util.List;
import java.util.Map;

import woofer.com.test.R;
import com.woofer.adapter.FansAdapter;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;

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
        relativeLayout = (RelativeLayout) findViewById(R.id.OT_home_follow_Rlayout);

        mDataLV = (ListView) findViewById(R.id.OT_fans_data);


        SharedPreferences EnableSp = getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);

        followingEnable = EnableSp.getInt("FOLLOWINGENABLE", 1);
        if (followingEnable == 0) {
            mDataLV.setBackgroundResource(R.drawable.followingunavalible);
        } else {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    dataList = OtherUserHomePage.otherUserHomePageTransfer.followingList;
                    mDataLV.setAdapter(new FansAdapter(FollowingsActivity.this, dataList));

                    //unregisterReceiver(mBroadcastReceiver);
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.zaizai1.broadcast.notifyFollowingsGot");
            registerReceiver(mBroadcastReceiver, intentFilter);

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

                            SharedPreferences sp1 = FollowingsActivity.this.getSharedPreferences("ENABLE", StorydegitalActivity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp1.edit();
                            editor.putInt("FOLLOWINGENABLE",  userInfoResponse.msg.user.followingEnable);
                            editor.putInt("FANSENABLE",  userInfoResponse.msg.user.followerEnable);
                            editor.apply();

                            Intent intent = new Intent(FollowingsActivity.this, OtherUserHomePage.class);
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
                        Toast.makeText(FollowingsActivity.this, "网络连接失败，请检查您的网路", Toast.LENGTH_SHORT).show();
                    }
                });
                romauntNetWork.getUserInfo(LoginToken, Integer.toString((Integer) dataList.get(position).get("USERID")));

            }
        });
    }

}