package com.woofer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.woofer.commentandreply.view.NoTouchLinearLayout;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.SendChatResponse;
import com.woofer.privateLetter.ConversationListAdapter;
import com.woofer.privateLetter.ConversationListInfo;
import com.woofer.titlebar.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import woofer.com.test.R;

/**
 * Created by YOURFATHERME on 2016/7/10.
 */
public class ConversationActivity extends AppCompatActivity{
    private ListView listViewConversationList;
    private LinearLayout mLytCommentVG;
    private NoTouchLinearLayout mLytEdittextVG;
    public static EditText mCommentEdittext;
    private Button mSendBut;
    private ImageView avatar;

    private TitleBar titleBar;

    private String comment = "";
    ArrayList<ConversationListInfo> dataList = new ArrayList<ConversationListInfo>();
    private int userid;
    private String LoginToken;
    private ConversationListAdapter conversationListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        listViewConversationList=(ListView)findViewById(R.id.listViewConversationList);


        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        userid = intent.getIntExtra("USERID",0);
        LoginToken = intent.getStringExtra("LOGINTOKEN");
        titleBar  = (TitleBar)findViewById(R.id.Conversation_titlebar);
        titleBar.setTitle(username);
        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConversationActivity.this.finish();
            }
        });

        mLytCommentVG = (LinearLayout) findViewById(R.id.Conversation_vg_lyt);
        mLytEdittextVG = (NoTouchLinearLayout) findViewById(R.id.Conversation_edit_vg_lyt);
        mCommentEdittext = (EditText) findViewById(R.id.Conversation_edit_comment);
        mSendBut = (Button) findViewById(R.id.Conversation_but_comment_send);

        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);
        mLytCommentVG.setOnClickListener(cl);


        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "Hello World!!!!!这是第一条信息",false));


        listViewConversationList.setAdapter(conversationListAdapter);

    }

    private final class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.Conversation_but_comment_send:
                    if(isEditEmply()){
                        //第一条还是会加载到左边
                        RomauntNetWork romauntNetWork = new RomauntNetWork();
                        romauntNetWork.setRomauntNetworkCallback(new RomauntNetworkCallback() {
                            @Override
                            public void onResponse(Object response) {
                                if(response instanceof SendChatResponse){
                                    final SendChatResponse sendChatResponse = (SendChatResponse)response;
                                    if(sendChatResponse.status.equals("true")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dataList.add(new ConversationListInfo(R.drawable.ic_launcher,comment,true));
                               conversationListAdapter.notifyDataSetChanged();


                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onError(Object error) {
                                Toast.makeText(ConversationActivity.this, "网络连接错误，请检查您的网络", Toast.LENGTH_SHORT).show();
                            }
                        });
                        romauntNetWork.sendChat(LoginToken,Integer.toString(userid),comment);
                        /*dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "5456",true));
                        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "5456",true));
                        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "5456",true));
                        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "5456",true));
                    dataList.add(new ConversationListInfo(R.drawable.ic_launcher,comment,true));*/
                }
                    mLytCommentVG.setVisibility(View.VISIBLE);
                    mLytEdittextVG.setVisibility(View.GONE);
                    onFocusChange(false);
                    break;
                case R.id.Conversation_vg_lyt:
                    mLytCommentVG.setVisibility(View.GONE);
                    mLytEdittextVG.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    break;
            }
        }
    }

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

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = mCommentEdittext.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "聊天内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        mCommentEdittext.setText("");
        return true;
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
