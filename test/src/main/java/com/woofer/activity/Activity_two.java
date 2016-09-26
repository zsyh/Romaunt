package com.woofer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import com.woofer.net.PublicStoryListResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.refreshlayout.adapter.NormalAdapterViewAdapter;
import com.woofer.refreshlayout.model.RefreshModel;
import com.woofer.userInfo;
import com.woofer.util.Utils;

import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import woofer.com.test.R;

public class Activity_two extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mDataLV;
    private NormalAdapterViewAdapter mAdapter;
    private int mNewPageNumber = 0;
    private int mMorePageNumber = 0;
    private long firstbacktime = 0;
    private String loginToken;
    private String Authorsign;
    private String Authorname;
    private String token;
    private URL url;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RomauntAlarmTest","Activity_two onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("RomauntAlarmTest","Activity_two onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("RomauntAlarmTest","Activity_two onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RomauntAlarmTest","Activity_two onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RomauntAlarmTest","Activity_two onPause()");
    }




    @Override
    protected void initView(Bundle saveInstanceState) {
        setContentView(R.layout.activity_activity_two);

        Log.e("RomauntAlarmTest","Activity_two initView()");
        mRefreshLayout = getViewById(R.id.refreshLayout);
        mDataLV = getViewById(R.id.data);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);

        // mDataLV.setOnClickListener(this);
        mDataLV.setOnItemClickListener(this);

        mAdapter = new NormalAdapterViewAdapter(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
    }


    private List<RefreshModel> listLogic;

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        /**
         *设置广告
         */
        //mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(mApp),false);

        /**水滴刷新效果
         * 头部刷新
         */
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(mApp, true);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorgray);
        stickinessRefreshViewHolder.setRotateImage(R.mipmap.bga_refresh_stickiness);

        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);

        mDataLV.setAdapter(mAdapter);

//        mEngine.loadInitDatas().enqueue(new Callback<List<RefreshModel>>() {
//            @Override
//            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
//                mAdapter.setDatas(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
//
//            }
//        });


//        List<RefreshModel> list = new ArrayList<>();
//        list.add(new RefreshModel("nihao"," mAdapter.setDatas(list);"));
//        list.add(new RefreshModel("nihao"," mAdapter.setDatas(list);"));
//
//        mAdapter.setDatas(list);

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        token = sp.getString("TOKEN", "");
//
//
        if (!loginToken.equals("")) {
            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {

                    if(! (response instanceof PublicStoryListResponse)) {
                        Log.e("Romaunt","初次加载广场故事列表时返回结果非正确，为防止崩溃直接取消初次加载");
                        return;
                    }

                    final PublicStoryListResponse publicStoryListResponse = (PublicStoryListResponse) response;
                    listLogic = new ArrayList<>();
                    for (int i = 0; i < publicStoryListResponse.msg.size(); i++) {
                        listLogic.add(new RefreshModel(publicStoryListResponse.msg.get(i).title,
                                publicStoryListResponse.msg.get(i).content, "", "", publicStoryListResponse.msg.get(i).AuthorID, publicStoryListResponse.msg.get(i).id,""
                                ,datetotime(publicStoryListResponse.msg.get(i).createdAt),publicStoryListResponse.msg.get(i).flags));
                                Log.e("storyID",  publicStoryListResponse.msg.get(i).id);
                    }


                    for (int i = 0; i < publicStoryListResponse.msg.size(); i++) {
                        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                final UserInfoResponse userInfoResponse = (UserInfoResponse) response;

                                for (int j = 0; j < listLogic.size(); j++) {
                                    if (listLogic.get(j).userID == userInfoResponse.msg.user.id) {
                                        listLogic.get(j).authorname = userInfoResponse.msg.user.userName;
                                        listLogic.get(j).sign = userInfoResponse.msg.user.sign;
                                        listLogic.get(j).avater = userInfoResponse.msg.user.avatar;
                                        Log.e("AVATAR",listLogic.get(j).avater+j);
                                        if(!userInfoResponse.msg.user.avatar.equals("")) {
                                            File  imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.user.id + ".png");
                                            if(imgfile.exists()) {
                                                Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.
                                                        user.id + ".png");
                                                listLogic.get(j).bitmap = bmp;
                                            }
                                            else{
                                                try{
                                                    url = new URL(userInfoResponse.msg.user.avatar);
                                                }
                                                catch (MalformedURLException e){
                                                    e.printStackTrace();
                                                }
                                                Utils.onLoadImage(url,  new Utils.OnLoadImageListener(){

                                                    @Override
                                                    public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                                                        if(bitmap!=null){
                                                            for(int i =0; i<listLogic.size();i++){
                                                                if(listLogic.get(i).userID==userid){
                                                                        listLogic.get(i).bitmap=bitmap;
                                                                }
                                                            }
                                                        }
                                                        else{
                                                            for(int i =0; i<listLogic.size();i++){
                                                                if (listLogic.get(i).userID == userid) {
                                                                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_defaultavatar);
                                                                    listLogic.get(i).bitmap = bitmap1;
                                                                }
                                                            }
                                                        }
                                                    }
                                                },listLogic.get(j).userID);
                                            }
                                        }
                                    }
                                }
                                mAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });


                        romauntNetWork1.getUserInfo(loginToken, Integer.toString(publicStoryListResponse.msg.get(i).AuthorID));

                    }


                    mAdapter.setDatas(listLogic);
                    userInfo.nowPage++;
                }

                @Override
                public void onError(Object error) {

                }
            });
            romauntNetWork.getPublicStoryList(loginToken, Long.toString(new Date().getTime()), "1", "10");
        }
        //"1467033214"

//        long time=new Date().getTime();
//        Log.e("Romaunt",time);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Activity_two.this, StorydegitalActivity.class);
        intent.putExtra("USERID", mAdapter.getItem(position).userID);
        intent.putExtra("ID", mAdapter.getItem(position).id);
        intent.putExtra("LoginToken", loginToken);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       /* showToast(mAdapter.getItem(position).title + mAdapter.getItem(position).id + "  userid：" +
                mAdapter.getItem(position).userID);*/

        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View childView, int position) {
        if (childView.getId() == R.id.itemTV5)
            mAdapter.removeItem(position);
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup viewGroup, View childview, int position) {

        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private List<RefreshModel> listNewData;

    /*手向下拉*/
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        showLoadingDialog();


//        mEngine.loadNewData(mNewPageNumber).enqueue(new Callback<List<RefreshModel>>() {
//            @Override
//            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
//                ThreadUtil.runInUIThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRefreshLayout.endRefreshing();
//                        dismissLoadingDialog();
//                        mAdapter.addNewDatas(response.body());
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                            mDataLV.deferNotifyDataSetChanged();
//                        }
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
//                mRefreshLayout.endRefreshing();
//                dismissLoadingDialog();
//            }
//        });


//        mRefreshLayout.endRefreshing();
//        dismissLoadingDialog();
//        List<RefreshModel> list = new ArrayList<>();
//        list.add(new RefreshModel("nihao","mAdapter.addNewDatas(list);"));
//        list.add(new RefreshModel("nihao","mAdapter.addNewDatas(list);"));
//
//        mAdapter.addNewDatas(list);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            mDataLV.deferNotifyDataSetChanged();
//        }

        //上拉获取10条信息
        //比较10条信息的ID与列表中的是否有一致的
        //若有一致的，说明新信息少于10条，把不一致的部分加到list里
        //若没有一致的，说明新信息多于10条，把列表清空，并把最新的10条加到list里

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        token = sp.getString("TOKEN", "");


        if (!loginToken.equals("")) {
                    romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                        @Override
                public void onResponse(Object response) {

                    final Object finalResponse = response;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            mRefreshLayout.endRefreshing();
//                            dismissLoadingDialog();
                            final PublicStoryListResponse publicStoryListResponse = (PublicStoryListResponse) finalResponse;
                            listNewData = new ArrayList<>();

                            boolean hasSame = false;
                            int count = publicStoryListResponse.msg.size();
                            for (int i = 0; i < publicStoryListResponse.msg.size(); i++) {

                                if (!mAdapter.isEmpty() && publicStoryListResponse.msg.get(i).id.equals(mAdapter.getItem(0).id)) {
                                    hasSame = true;
                                    count = i;
                                }

                            }

                            if (!hasSame) {
                                mAdapter.clear();
                                userInfo.nowPage=2;
                            }

                            for (int i = 0; i < count; i++) {
                                listNewData.add(new RefreshModel(publicStoryListResponse.msg.get(i).title,
                                        publicStoryListResponse.msg.get(i).content, "", "", publicStoryListResponse.msg.get(i).AuthorID, publicStoryListResponse.msg.get(i).id,"",datetotime(publicStoryListResponse.msg.get(i).createdAt),publicStoryListResponse.msg.get(i).flags));
                            }


                            for (int i = 0; i < count; i++) {

                                RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                                romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                    @Override
                                    public void onResponse(Object response) {
                                        UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                                        Log.e("sign", userInfoResponse.msg.user.sign);
                                        Log.e("", "username:" + userInfoResponse.msg.user.userName);

                                        for (int j = 0; j < listNewData.size(); j++) {

                                            if (listNewData.get(j).userID == userInfoResponse.msg.user.id) {
                                                listNewData.get(j).authorname = userInfoResponse.msg.user.userName;
                                                listNewData.get(j).sign = userInfoResponse.msg.user.sign;

                                                listNewData.get(j).avater = userInfoResponse.msg.user.avatar;
                                                if(!userInfoResponse.msg.user.avatar.equals("")) {
                                                    File  imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.user.id + ".png");
                                                    if(imgfile.exists()) {
                                                        Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.
                                                                user.id + ".png");
                                                        listNewData.get(j).bitmap = bmp;
                                                    }
                                                    else{
                                                        try{
                                                            url = new URL(userInfoResponse.msg.user.avatar);
                                                        }
                                                        catch (MalformedURLException e){
                                                            e.printStackTrace();
                                                        }
                                                        /**
                                                         *遗留问题: 当url为http://139.129.131.240:3000/upload/file/985139_dog.jpg这样的url时 无法加载出图片
                                                         * 但是基本一样的写法在storydegital中是对的*/
                                                        Utils.onLoadImage(url, new Utils.OnLoadImageListener(){

                                                            @Override
                                                            public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                                                                if(bitmap!=null){
                                                                    for(int i =0; i<listNewData.size();i++){
                                                                        if(listNewData.get(i).userID==userid){
                                                                                listNewData.get(i).bitmap=bitmap;
                                                                        }
                                                                    }
                                                                }
                                                                else{
                                                                    for(int i = 0;i<listNewData.size(); i++) {
                                                                        if(listNewData.get(i).userID==userid) {
                                                                            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_defaultavatar);
                                                                            listNewData.get(i).bitmap = bitmap1;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        },listNewData.get(j).userID);
                                                    }
                                                }

                                            }
                                        }
                                        mAdapter.notifyDataSetChanged();


                                    }

                                    @Override
                                    public void onError(Object error) {
                                        mRefreshLayout.endRefreshing();
                                    }
                                });


                                romauntNetWork1.getUserInfo(loginToken, Integer.toString(publicStoryListResponse.msg.get(i).AuthorID));

                            }

                            mAdapter.addNewDatas(listNewData);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                                mDataLV.deferNotifyDataSetChanged();
                            }


                        }
                    });

                }

                @Override
                public void onError(Object error) {
                    mRefreshLayout.endRefreshing();
                    Toast.makeText(Activity_two.this, "网络连接失败，请检查您的网络", Toast.LENGTH_SHORT).show();
                }
            });
            romauntNetWork.getPublicStoryList(loginToken, Long.toString(new Date().getTime()), "1", "10");
        }
        else{
            mRefreshLayout.endRefreshing();
            Toast.makeText(Activity_two.this, "您还没有登录，请先登录", Toast.LENGTH_SHORT).show();
        }

    }

    private List<RefreshModel> listMoreData;

    /*手向上拉*/
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mMorePageNumber++;

//        showLoadingDialog();


//        mEngine.loadMoreData(mMorePageNumber).enqueue(new Callback<List<RefreshModel>>() {
//            @Override
//            public void onResponse(Call<List<RefreshModel>> call, final Response<List<RefreshModel>> response) {
//                ThreadUtil.runInUIThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRefreshLayout.endLoadingMore();
//                        dismissLoadingDialog();
//                        mAdapter.addMoreDatas(response.body());
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
//                mRefreshLayout.endLoadingMore();
//            }
//        });


        RomauntNetWork romauntNetWork = new RomauntNetWork();
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        loginToken = sp.getString("LOGINTOKEN", "");
        token = sp.getString("TOKEN", "");
        if (!loginToken.equals("")) {

            romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                @Override
                public void onResponse(Object response) {
                    final PublicStoryListResponse publicStoryListResponse = (PublicStoryListResponse) response;
                    listMoreData = new ArrayList<>();

                    for (int i = 0; i < publicStoryListResponse.msg.size(); i++) {
                        listMoreData.add(new RefreshModel(publicStoryListResponse.msg.get(i).title,
                                publicStoryListResponse.msg.get(i).content, "", "", publicStoryListResponse.msg.get(i).AuthorID, publicStoryListResponse.msg.get(i).id,"",datetotime(publicStoryListResponse.msg.get(i).createdAt),publicStoryListResponse.msg.get(i).flags));
                    }

                    for (int i = 0; i < publicStoryListResponse.msg.size(); i++) {
                        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                UserInfoResponse userInfoResponse = (UserInfoResponse) response;

                                for (int j = 0; j < listMoreData.size(); j++) {
                                    if (listMoreData.get(j).userID == userInfoResponse.msg.user.id) {
                                        listMoreData.get(j).authorname = userInfoResponse.msg.user.userName;
                                        listMoreData.get(j).sign = userInfoResponse.msg.user.sign;
                                        listMoreData.get(j).avater = userInfoResponse.msg.user.avatar;
                                        if(!userInfoResponse.msg.user.avatar.equals("")) {
                                            File  imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.user.id + ".png");
                                            if(imgfile.exists()) {
                                                Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userInfoResponse.msg.
                                                        user.id + ".png");
                                                listMoreData.get(j).bitmap = bmp;
                                            }
                                            else{
                                                try{
                                                    url = new URL(userInfoResponse.msg.user.avatar);
                                                }
                                                catch (MalformedURLException e){
                                                    e.printStackTrace();
                                                }
                                                Utils.onLoadImage(url, new Utils.OnLoadImageListener(){

                                                    @Override
                                                    public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                                                        if(bitmap!=null){
                                                            for(int i =0; i<listMoreData.size();i++){
                                                                if(listMoreData.get(i).userID==userid){
                                                                    listMoreData.get(i).bitmap=bitmap;
                                                                }
                                                            }
                                                        }
                                                        else{
                                                            for(int i= 0;i<listMoreData.size();i++){
                                                                if(listMoreData.get(i).userID==userid){
                                                                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_defaultavatar);
                                                                    listMoreData.get(i).bitmap = bitmap1;
                                                                }
                                                            }
                                                        }
                                                    }
                                                },listMoreData.get(j).userID);
                                            }
                                        }
                                    }

                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Object error) {
                                Log.e("Romaunt", "onError 上拉请求用户信息");
                                mRefreshLayout.endLoadingMore();
                            }
                        });


                        romauntNetWork1.getUserInfo(loginToken, Integer.toString(publicStoryListResponse.msg.get(i).AuthorID));

                    }


                    mRefreshLayout.endLoadingMore();
//                    dismissLoadingDialog();
                    mAdapter.addMoreDatas(listMoreData);
                }

                @Override
                public void onError(Object error) {
                    Log.e("Romaunt", "onError 上拉请求广场故事列表");
                    mRefreshLayout.endLoadingMore();
                }
            });
            romauntNetWork.getPublicStoryList(loginToken, Long.toString(new Date().getTime()), Integer.toString(userInfo.nowPage++), "10");
        }


        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                long currentTime = System.currentTimeMillis();
            if (currentTime - firstbacktime <= 2000) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstbacktime = currentTime;
            }
        }
        return true;
    }
    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

}
