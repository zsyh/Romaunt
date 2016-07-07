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
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.adapter.fansAdapter;
import com.woofer.refreshlayout.model.fansinfoModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import woofer.com.test.R;

public class FansActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mDataLV;
    private fansAdapter mAdapter;
    private String loginToken;
    private String token;
    private  int userid;
    /**记录当前适listview中item个数 去重 为多步加载做准备*/
    private int headofadapater = 0;

    @Override
    protected void initView(Bundle saveInstanceState) {
        setContentView(R.layout.activity_fans);
        mRefreshLayout = getViewById(R.id.refreshLayout);
        mDataLV = getViewById(R.id.data);
        SharedPreferences sp  = getSharedPreferences("USERID",OtherUserHomePage.MODE_PRIVATE);
        userid = sp.getInt("USERID", 0);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);

        mDataLV.setOnItemClickListener(this);

        mAdapter = new fansAdapter(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    private List<fansinfoModel> listLogic;
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(mApp, true);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorwrite);
        stickinessRefreshViewHolder.setRotateImage(R.mipmap.bga_refresh_00ffffff);

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
                    for(int i = 0 ;i <userInfoResponse.msg.follower.size();i++){
                        listLogic.add(new fansinfoModel(userInfoResponse.msg.follower.get(i).id,userInfoResponse.msg.follower.get(i).userName,
                                userInfoResponse.msg.follower.get(i).sign,userInfoResponse.msg.follower.get(i).sex,userInfoResponse.msg.
                                follower.get(i).avatar));
                        headofadapater++;
                    }
                    mAdapter.setDatas(listLogic);
                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getUserInfo(loginToken,Integer.toString(userid));
        }


    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {

    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View view, int i) {
        return false;
    }

    private List<fansinfoModel> listNewData;
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();
        showLoadingDialog();
        dismissLoadingDialog();
        /*RomauntNetWork romauntNetWork = new RomauntNetWork();
        if(!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(final Object response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.endRefreshing();
                            dismissLoadingDialog();
                            final UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                            listNewData = new ArrayList<>();

                            boolean hassame = false;
                            int count = userInfoResponse.msg.follower.size();
                            for (int i = 0; i < userInfoResponse.msg.follower.size(); i++) {
                                if (userInfoResponse.msg.follower.get(i).userName.equals(mAdapter.getItem(0).username))
                                    ;
                                hassame = true;
                                count = count - 1;
                            }
                            if (!hassame) {
                                mAdapter.clear();
                            }
                            for (int i = 0; i < count; i++) {
                                listNewData.add(new fansinfoModel(userInfoResponse.msg.follower.get(i).id, userInfoResponse.msg.follower.get(i).userName,
                                        userInfoResponse.msg.follower.get(i).sign, userInfoResponse.msg.follower.get(i).sex, userInfoResponse.msg.
                                        follower.get(i).avatar));
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
        }*/
    }

    private List<fansinfoModel> listMoreData;
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        showLoadingDialog();

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        if(!loginToken.equals("")){
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    int j;
                    final UserInfoResponse userInfoResponse = (UserInfoResponse)response;
                    listMoreData = new ArrayList<>();

                    for(int i = 0 ;i <userInfoResponse.msg.follower.size();i++) {
                        for(j = 0; j < headofadapater&&!userInfoResponse.msg.follower.get(i).userName
                                .equals(mAdapter.getItem(j).username); j++){

                        }
                        if(j==headofadapater){
                            listMoreData.add(new fansinfoModel(userInfoResponse.msg.follower.get(i).id, userInfoResponse.msg.follower.get(i).userName,
                                    userInfoResponse.msg.follower.get(i).sign, userInfoResponse.msg.follower.get(i).sex, userInfoResponse.msg.
                                    follower.get(i).avatar));
                        }

                    }
                    mRefreshLayout.endLoadingMore();
                    dismissLoadingDialog();
                    mAdapter.addMoreDatas(listMoreData);

                }

                @Override
                public void onError(Object error) {
                    Log.e("fans", "");
                    mRefreshLayout.endLoadingMore();
                }
            });
            romauntNetWork.getUserInfo(loginToken, Integer.toString(userid));
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sp = getSharedPreferences("userinfo", signinActivity.MODE_PRIVATE);

        String LoginToken = sp.getString("LOGINTOKEN", "");
        Intent intent1 = new Intent(FansActivity.this, OtherUserHomePage.class);
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
