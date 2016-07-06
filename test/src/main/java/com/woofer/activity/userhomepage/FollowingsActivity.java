package com.woofer.activity.userhomepage;

import android.content.Intent;
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

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import woofer.com.test.R;


public class FollowingsActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mDataLV;
    private fansAdapter mAdapter;
    private int mMorePageNumber = 0;
    private String loginToken;
    private String token;
    private  int userid;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_followers);
        mRefreshLayout = (getViewById(R.id.OT_fans_refreshLayout));
        mDataLV = getViewById(R.id.OT_fans_data);
        SharedPreferences sp  = getSharedPreferences("USERID",OtherUserHomePage.MODE_PRIVATE);
        userid = sp.getInt("USERID",0);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
        mDataLV.setOnItemClickListener(this);

        mAdapter = new fansAdapter(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    private List<fansinfoModel> listLogic;
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(mApp, true);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorgray);
        stickinessRefreshViewHolder.setRotateImage(R.mipmap.bga_refresh_stickiness);

        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);
        mDataLV.setAdapter(mAdapter);
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        token = sp.getString("TOKEN", "");

        if(!loginToken.equals("")){
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final UserInfoResponse userInfoResponse =(UserInfoResponse)response;
                    listLogic = new ArrayList<>();
                    for(int i = 0 ;i <userInfoResponse.msg.following.size();i++){
                        listLogic.add(new fansinfoModel(userInfoResponse.msg.following.get(i).id,userInfoResponse.msg.following.get(i).userName,
                                userInfoResponse.msg.following.get(i).sign,userInfoResponse.msg.following.get(i).sex,userInfoResponse.msg.
                                following.get(i).avatar));
                    }
                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getUserInfo(loginToken,Integer.toString(userid));
        }
        mAdapter.setDatas(listLogic);
    }

    private List<fansinfoModel> listNewData;
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoadingDialog();
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        if(!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(final Object response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.endRefreshing();
                            dismissLoadingDialog();
                            final UserInfoResponse userInfoResponse = (UserInfoResponse)response;
                            listNewData = new ArrayList<>();

                            boolean hassame = false;
                            int count = userInfoResponse.msg.following.size();
                            for(int i = 0;i<userInfoResponse.msg.following.size();i++){
                                if(userInfoResponse.msg.following.get(i).userName.equals(mAdapter.getItem(0).username));
                                    hassame = true;
                                    count = count -1;
                            }
                            if(!hassame){
                                mAdapter.clear();
                            }
                            for(int i = 0; i<count; i++){
                                listNewData.add(new fansinfoModel(userInfoResponse.msg.following.get(i).id,userInfoResponse.msg.following.get(i).userName,
                                        userInfoResponse.msg.following.get(i).sign,userInfoResponse.msg.following.get(i).sex,userInfoResponse.msg.
                                        following.get(i).avatar));
                            }
                        }
                    });
                }

                @Override
                public void onError(Object error) {
                    mRefreshLayout.endRefreshing();
                }
            });
            romauntNetWork.getUserInfo(loginToken, Integer.toString(userid));
        }
    }

    private List<fansinfoModel> listMoreData;
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        showLoadingDialog();

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        if(!loginToken.equals("")){
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final UserInfoResponse userInfoResponse = (UserInfoResponse)response;
                    listMoreData = new ArrayList<>();
                    for(int i = 0 ;i <userInfoResponse.msg.following.size();i++) {
                        listMoreData.add(new fansinfoModel(userInfoResponse.msg.following.get(i).id, userInfoResponse.msg.following.get(i).userName,
                                userInfoResponse.msg.following.get(i).sign, userInfoResponse.msg.following.get(i).sex, userInfoResponse.msg.
                                following.get(i).avatar));
                    }

                }

                @Override
                public void onError(Object error) {
                    Log.e("Following", "");
                    mRefreshLayout.endLoadingMore();
                }
            });
            romauntNetWork.getUserInfo(loginToken, Integer.toString(userid));
        }
        mRefreshLayout.endLoadingMore();
        dismissLoadingDialog();
        mAdapter.addMoreDatas(listMoreData);

        return true;
    }
    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
            return;
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View view, int i) {
        return false;
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);

        String LoginToken = sp.getString("LOGINTOKEN", "");
        Intent intent1 = new Intent(FollowingsActivity.this, OtherUserHomePage.class);
        intent1.putExtra("LoginToken", LoginToken);
        intent1.putExtra("UserID", mAdapter.getItem(position).userID);
        intent1.putExtra("Avater", mAdapter.getItem(position).avater);
        intent1.putExtra("Sign", mAdapter.getItem(position).sign);
        intent1.putExtra("Username", mAdapter.getItem(position).username);
        intent1.putExtra("Sex", mAdapter.getItem(position).sex);
        startActivity(intent1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
