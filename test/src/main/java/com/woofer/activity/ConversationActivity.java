package com.woofer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
    private ImageView avatar;

    private TitleBar titleBar;

    private String comment = "";
    ArrayList<ConversationListInfo> dataList = new ArrayList<ConversationListInfo>();
    private int userid;
    private String LoginToken;
    private ConversationListAdapter conversationListAdapter;

    private ImageView imageViewSend;
    private EditText editTextSend;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        listViewConversationList=(ListView)findViewById(R.id.listViewConversationList);
        imageViewSend=(ImageView)findViewById(R.id.imageViewSend);
        editTextSend=(EditText)findViewById(R.id.editTextSend);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        userid = intent.getIntExtra("USERID", 0);
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


        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



            }
        });





        conversationListAdapter= new ConversationListAdapter(this,dataList);
        listViewConversationList.setAdapter(conversationListAdapter);

    }



    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        comment = editTextSend.getText().toString().trim();
        if (comment.equals("")) {
            Toast.makeText(getApplicationContext(), "聊天内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        editTextSend.setText("");
        return true;
    }


    private String datetotime(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyyMMdd HH:mm");
        long lcc = Long.valueOf(time);
        int t = Integer.parseInt(time);
        String times = sdr.format(new Date(t * 1000L));
        return times;
    }

    private boolean inRangeOfView(View view, MotionEvent ev){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if(ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())){
            return false;
        }
        return true;
    }

    /**
     * 点击屏幕其他地方收起输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!inRangeOfView(editTextSend, ev)) {

                //关闭输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();
                if (isOpen) {
                    imm.hideSoftInputFromWindow(editTextSend.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }


            }
            return super.dispatchTouchEvent(ev);
        }

        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

}
