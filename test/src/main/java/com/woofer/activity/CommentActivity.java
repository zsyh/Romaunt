package com.woofer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.commentandreply.adapter.CommentAdapter;
import com.woofer.commentandreply.date.Commentdate;
import com.woofer.commentandreply.date.Replydate;
import com.woofer.commentandreply.view.NoTouchLinearLayout;
import com.woofer.net.CommentResponse;
import com.woofer.net.GetCommentlistResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UserInfoResponse;
import com.woofer.titlebar.TitleBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import woofer.com.test.R;

public class CommentActivity extends AppCompatActivity {
    private ListView mListData;
    private LinearLayout mLytCommentVG;
    private NoTouchLinearLayout mLytEdittextVG;
    public static EditText mCommentEdittext;
    private Button mSendBut;
    private ImageView avatar;

    private CommentAdapter adapter;
    private int count;                    //记录评论ID
    private String comment = "";        //记录对话框中的内容
    private int position;                //记录回复评论的索引
    private boolean isReply;            //是否是回复，true代表回复

    private TitleBar titleBar;
    private String Logintoken;
    private int Userid;
    private String username;
    private String time;
    private String content;
    private String storyId;

    private TextView nametv;
    private TextView timetv;
    private TextView storydetals;

    private List<Commentdate> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        list = new ArrayList<>();

        initView();
        Intent intent = getIntent();
        Logintoken = intent.getStringExtra("LOGINTOKEN");
        //这个id是发表该故事的用户的id
        Userid = intent.getIntExtra("USERID", 0);
        username = intent.getStringExtra("Username");
        time = intent.getStringExtra("Time");
        content = intent.getStringExtra("Content");
        storyId = intent.getStringExtra("ID");
        InitCompement();

        getCommentData();

    }

    private void initView() {
        mListData = (ListView) findViewById(R.id.list_data);
        mLytCommentVG = (LinearLayout) findViewById(R.id.comment_vg_lyt);
        mLytEdittextVG = (NoTouchLinearLayout) findViewById(R.id.edit_vg_lyt);
        mCommentEdittext = (EditText) findViewById(R.id.edit_comment);
        mSendBut = (Button) findViewById(R.id.but_comment_send);

        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);
        mLytCommentVG.setOnClickListener(cl);

        mLytEdittextVG.setOnResizeListener(new NoTouchLinearLayout.OnResizeListener() {
            @Override
            public void OnResize() {

                onFocusChange(false);

            }
        });


    }
    /**
     * 事件点击监听器
     */
    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_send:        //发表评论按钮
                    if (isEditEmply()) {        //判断用户是否输入内容
                        if (isReply) {
                            replyComment();
                        } else {
                            publishComment();
                        }
                        mLytCommentVG.setVisibility(View.VISIBLE);
                        mLytEdittextVG.setVisibility(View.GONE);
                        onFocusChange(false);
                    }
                    break;
                case R.id.comment_vg_lyt:        //底部评论按钮
                    isReply = false;
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    mLytCommentVG.setVisibility(View.GONE);
                    onFocusChange(true);
                    break;
            }
        }
    }

    private void InitCompement() {
        nametv = (TextView) findViewById(R.id.comment_username);
        nametv.setText(username);
        timetv = (TextView) findViewById(R.id.comment_time);
        timetv.setText(time);
        storydetals = (TextView) findViewById(R.id.comment_sign);
        storydetals.setText(content);

        titleBar = (TitleBar) findViewById(R.id.comment_titlebar);
        titleBar.setTitle("评论");
        titleBar.setLeftImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.this.finish();
            }
        });
        avatar = (ImageView)findViewById(R.id.comment_avatar);
        File imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + Userid + ".png");
        if(imgfile.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + Userid + ".png");
            avatar.setImageBitmap(bmp);
        }
    }

    /**
     * 发表评论
     */
    private void publishComment() {

        RomauntNetWork romauntNetWork = new RomauntNetWork();
        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override

            public void onResponse(Object response) {
                Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();

                CommentResponse commentResponse = (CommentResponse) response;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);

                SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                String username = sp.getString("USERNAME","");




                Commentdate freshview = new Commentdate(count, Integer.parseInt(sp.getString("USERID", "")), username, str, comment, sp.getString("USERSIGN", ""),
                        sp.getString("AVATERURL", ""), commentResponse.msg.id);
                list.add(freshview);//加载到list的最前面

                if (count == 0) {
                    adapter = new CommentAdapter(CommentActivity.this, list, R.layout.comment_item_list, handler);
                    mListData.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
                count++;

            }

            @Override
            public void onError(Object error) {
                Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }
        });
        romauntNetWork.comment(Logintoken, storyId, comment);


    }

    /**
     * 获取评论列表数据
     */
    private void getCommentData() {

        final RomauntNetWork romauntNetWork = new RomauntNetWork();
        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                if (response instanceof GetCommentlistResponse) {
                    final GetCommentlistResponse getCommentlistResponse = (GetCommentlistResponse) response;
                    /**拿取评论*/
                    count = getCommentlistResponse.msg.comment.size();
                    for (int i = 0; i < getCommentlistResponse.msg.comment.size(); i++) {
                        list.add(new Commentdate(i, getCommentlistResponse.msg.comment.get(i).UserId, " ",
                                datetotime(getCommentlistResponse.msg.comment.get(i).createdAt), getCommentlistResponse.msg.comment.get(i).content,
                                " ", " ", getCommentlistResponse.msg.comment.get(i).id));

                        for (int j = 0; j < getCommentlistResponse.msg.comment.get(i).RevComment.size(); j++) {
                            list.get(i).replyList.add(new Replydate(j,getCommentlistResponse.msg.comment.get(i).RevComment.get(j).UserId
                                    , "", "", "", getCommentlistResponse.msg.comment.get(i).RevComment.get(j).content));
                        }
                    }

                    for (int i = 0; i < getCommentlistResponse.msg.comment.size(); i++) {
                        RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                        romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                UserInfoResponse userInfoResponse = (UserInfoResponse) response;
                                for (int j = 0; j < list.size(); j++) {
                                    if (list.get(j).commnetAccount == userInfoResponse.msg.user.id) {
                                        list.get(j).commentNickname = userInfoResponse.msg.user.userName;
                                        list.get(j).avatar = userInfoResponse.msg.user.avatar;
                                        list.get(j).sign = userInfoResponse.msg.user.sign;
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Object error) {

                            }
                        });
                        romauntNetWork1.getUserInfo(Logintoken, Integer.toString(getCommentlistResponse.msg.comment.get(i).UserId));
                    }

                    for (int i = 0; i < getCommentlistResponse.msg.comment.size(); i++) {
                        for(int j = 0 ; j < list.get(i).replyList.size();j++)
                        {
                            RomauntNetWork romauntNetWork1 = new RomauntNetWork();
                            romauntNetWork1.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                                @Override
                                public void onResponse(Object response) {
                                    if(response instanceof  UserInfoResponse){
                                        UserInfoResponse userInfoResponse = (UserInfoResponse)response;
                                        for (int i = 0; i < getCommentlistResponse.msg.comment.size(); i++) {
                                            for (int j = 0; j < list.get(i).replyList.size(); j++) {
                                                if (list.get(i).replyList.get(j).replyAccount==userInfoResponse.msg.user.id) {
                                                    list.get(i).replyList.get(j).replyNickname = userInfoResponse.msg.user.userName;
                                                }
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                    else{
                                        Log.e("Romaunt","获取回复列表用户名时response形态不符");
                                    }
                                }

                                @Override
                                public void onError(Object error) {

                                }
                            });romauntNetWork1.getUserInfo(Logintoken,Integer.toString(list.get(i).replyList.get(j).replyAccount));
                        }

                    }






                    adapter = new CommentAdapter(CommentActivity.this, list, R.layout.comment_item_list, handler);
                    mListData.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Object error){

                }
        });
        romauntNetWork.getCommentlist(Logintoken, storyId);


}
    /**
     * 获取回复列表数据
     */
    private List<Replydate> getReplyData() {
        List<Replydate> replyList = new ArrayList<>();
        return replyList;
    }

    /**
     * 回复评论
     */
    private void replyComment() {
        RomauntNetWork romauntNetWork = new RomauntNetWork();
        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
            @Override
            public void onResponse(Object response) {
                if(response instanceof  CommentResponse){
                    CommentResponse commentResponse = (CommentResponse)response;
                    Log.e("commentreply",Integer.toString(commentResponse.msg.id));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                            Replydate replydate = new Replydate();
                            replydate.setId(count + 100);
                            replydate.setCommentNickname("");
                            //list.get(position).getCommentNickname()
                            replydate.setReplyNickname(sp.getString("USERNAME", ""));
                            replydate.setReplyContent(comment);
                            adapter.getReplyComment(replydate, position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            @Override
            public void onError(Object error) {
                Toast.makeText(CommentActivity.this, "回复失败,请重试", Toast.LENGTH_SHORT).show();
            }
        });
        romauntNetWork.commentReply(Logintoken, Integer.toString(list.get(position).commentid),comment);
    }


    /**有commentadapter的点击事件 由handle传入数据 负责视图的更新*/
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    isReply = true;
                    position = (Integer) msg.obj;
                    mLytCommentVG.setVisibility(View.GONE);
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    break;

            }

        }
    };

    /**
     * 点击屏幕其他地方收起输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);
                mCommentEdittext.setText("");

            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {

            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        mCommentEdittext.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    mCommentEdittext.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
                    mLytCommentVG.setVisibility(View.VISIBLE);
                    mLytEdittextVG.setVisibility(View.GONE);
                }
            }
        }, 100);
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = mCommentEdittext.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        mCommentEdittext.setText("");
        return true;
    }

    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 50;
            int top = leftTop[1] - 50;
            int bottom = top + v.getHeight() + 300;
            int right = left + v.getWidth() + 120;
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }



    @Override
    public void onBackPressed() {

        //判断控件是否显示
        if (mLytEdittextVG.getVisibility() == View.VISIBLE) {
            mLytEdittextVG.setVisibility(View.GONE);
            mLytCommentVG.setVisibility(View.VISIBLE);
            mCommentEdittext.setText("");
        }
        else{
            super.onBackPressed();

        }
    }
    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int t = Integer.parseInt(time);
        String times = sdr.format(new Date(t * 1000L));
        return times;
    }

}
