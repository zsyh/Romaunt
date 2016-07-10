package com.woofer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.woofer.privateLetter.ChatListAdapter;
import com.woofer.privateLetter.ChatListInfo;

import java.util.ArrayList;

import woofer.com.test.R;

public class PrivateLetterActivity extends AppCompatActivity {
    private ListView listViewChatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist);

        listViewChatList=(ListView)findViewById(R.id.listViewChatList);
        listViewChatList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(PrivateLetterActivity.this, ConversationActivity.class);
                startActivity(intent);
            }
        });



        ArrayList<ChatListInfo> dataList = new ArrayList<ChatListInfo>();

        //坑：add方法传入的是引用，只能采用new匿名对象的方法
        dataList.add(new ChatListInfo(R.drawable.ic_launcher,"OurEDA聊天","15-张单旸晖：掌门要放大招了", "昨天"));
        dataList.add(new ChatListInfo(R.drawable.ic_launcher, "OurEDA_Maker_2015", "24小时全年无休看人搬砖:[图片]", "昨天"));
        listViewChatList.setAdapter(new ChatListAdapter(this, dataList));




    }
}
