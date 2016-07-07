package com.woofer.activity.userhomepage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.woofer.activity.BaseActivity;
import com.woofer.activity.OtherUserHomePage;
import com.woofer.net.GetStoryResponse;
import com.woofer.net.PersonStoryListResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.refreshlayout.adapter.NormalAdapterViewAdapter;
import com.woofer.refreshlayout.adapter.parhsAdapter;
import com.woofer.refreshlayout.model.parhsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import woofer.com.test.R;
public class ParhsActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mDataLV;
    private parhsAdapter mAdapter;
    private int mMorePageNumber = 0;
    private String loginToken;
    private String token;
    private int userid;
    private int headofadapter = 0;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_parhs);
        mRefreshLayout = getViewById(R.id.OT_parhs_refreshLayout);
        mDataLV = getViewById(R.id.OT_parhs_data);

        SharedPreferences sp = getSharedPreferences("USERID", OtherUserHomePage.MODE_PRIVATE);
        userid = sp.getInt("USERID", 0);
    }


    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
        mDataLV.setOnItemClickListener(this);

        mAdapter = new parhsAdapter(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }

    private List<parhsModel> listLogic;

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

        if (!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final PersonStoryListResponse personStoryListResponse = (PersonStoryListResponse) response;
                    listLogic = new ArrayList<>();
                    for (int i = 0; i < personStoryListResponse.msg.stories.size(); i++) {
                        listLogic.add(new parhsModel(
                                personStoryListResponse.msg.stories.get(i).flags,
                                personStoryListResponse.msg.stories.get(i).title,
                                datetotime(personStoryListResponse.msg.stories.get(i).createdAt),
                                0,
                                personStoryListResponse.msg.stories.get(i).content,
                                personStoryListResponse.msg.stories.get(i).id));
                            headofadapter++;
                        Log.e("listLogic", personStoryListResponse.msg.stories.get(i).content);
                    }
                    for (int i = 0; i < personStoryListResponse.msg.stories.size(); i++) {
                        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                GetStoryResponse storyResponse = (GetStoryResponse) response;
                                for (int j = 0; j < listLogic.size(); j++) {
                                    /**比较之前的结果和刷新*/
                                    if (listLogic.get(j).storyid.equals(storyResponse.msg.story.id)) {
                                        listLogic.get(j).thumbNUM = storyResponse.msg.likeCount;
                                    }
                                }
                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });
                        romauntNetWork1.getStory(loginToken, personStoryListResponse.msg.stories.get(i).id, Integer.toString(userid));
                    }
                    mAdapter.setDatas(listLogic);

                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getPersonStoryList(loginToken, Integer.toString(userid), "1", "10");
        }
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {

    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View view, int i) {
        return false;
    }

    private List<parhsModel> listNewData;

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoadingDialog();

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        if (!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final Object Response = response;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.endRefreshing();
                            dismissLoadingDialog();
                            final PersonStoryListResponse personStoryListResponse = (PersonStoryListResponse) Response;
                            listNewData = new ArrayList<parhsModel>();

                            boolean hassame = false;
                            int count = personStoryListResponse.msg.stories.size();
                            for (int i = 0; i < personStoryListResponse.msg.stories.size(); i++) {

                                if (personStoryListResponse.msg.stories.get(i).id.equals(mAdapter.getItem(0).storyid)) {
                                    hassame = true;
                                    count = i;
                                }
                            }

                            if (!hassame) {
                                mAdapter.clear();
                            }

                            for (int i = 0; i < count; i++) {
                                listNewData.add(new parhsModel(
                                        personStoryListResponse.msg.stories.get(i).flags,
                                        personStoryListResponse.msg.stories.get(i).title,
                                        datetotime(personStoryListResponse.msg.stories.get(i).createdAt),
                                        0,
                                        personStoryListResponse.msg.stories.get(i).content,
                                        personStoryListResponse.msg.stories.get(i).id));
                                headofadapter++;
                            }
                            for (int k = 0; k < count; k++) {
                                RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                                romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        GetStoryResponse storyResponse = (GetStoryResponse) response;
                                        for (int j = 0; j < listNewData.size(); j++) {
                                            /**比较之前的结果和刷新*/
                                            if (listNewData.get(j).storyid.equals(storyResponse.msg.story.id)) {
                                                listNewData.get(j).thumbNUM = storyResponse.msg.likeCount;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Object error) {
                                        mRefreshLayout.endRefreshing();
                                    }
                                });
                                romauntNetWork1.getStory(loginToken, personStoryListResponse.msg.stories.get(k).id, Integer.toString(userid));
                            }
                            mAdapter.addNewDatas(listNewData);
                        }
                    });
                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getPersonStoryList(loginToken, Integer.toString(userid), "1", "10");
        }
    }

    private List<parhsModel> listMoreData;

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;
        showLoadingDialog();
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        token = sp.getString("TOKEN", "");

        if (!loginToken.equals("")) {

            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    int j;
                    final PersonStoryListResponse personStoryListResponse = (PersonStoryListResponse) response;
                    listMoreData = new ArrayList<>();

                    for(int i = 0; i<personStoryListResponse.msg.stories.size();i++){
                        for(j =0 ;j<headofadapter&&!personStoryListResponse.msg.stories.get(i).id
                                .equals(mAdapter.getItem(j).storyid);j++){
                        }
                       if(j==headofadapter) {
                           listMoreData.add(new parhsModel(
                                   personStoryListResponse.msg.stories.get(i).flags,
                                   personStoryListResponse.msg.stories.get(i).title,
                                   datetotime(personStoryListResponse.msg.stories.get(i).createdAt),
                                   0,
                                   personStoryListResponse.msg.stories.get(i).content,
                                   personStoryListResponse.msg.stories.get(i).id));
                           for (int m = 0; m < personStoryListResponse.msg.stories.size(); m++) {
                               RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                               romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                   @Override
                                   public void onResponse(Object response) {
                                       GetStoryResponse storyResponse = (GetStoryResponse) response;
                                       for (int l = 0; l < listMoreData.size(); l++) {
                                           /**比较之前的结果和刷新*/
                                           if (listMoreData.get(l).storyid.equals(storyResponse.msg.story.id)) {
                                               listMoreData.get(l).thumbNUM = storyResponse.msg.likeCount;
                                           }
                                       }
                                   }
                                   @Override
                                   public void onError(Object error) {

                                   }
                               });
                               romauntNetWork1.getStory(loginToken, personStoryListResponse.msg.stories.get(m).id, Integer.toString(userid));
                           }
                       }
                    }

                    mRefreshLayout.endLoadingMore();
                    dismissLoadingDialog();
                    mAdapter.addMoreDatas(listMoreData);
                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getPersonStoryList(loginToken, Integer.toString(userid), "1", "10");
        }
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("dd-HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }
}
