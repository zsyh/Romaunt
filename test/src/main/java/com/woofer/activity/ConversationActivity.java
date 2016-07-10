package com.woofer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListView;

import com.woofer.privateLetter.ConversationListAdapter;
import com.woofer.privateLetter.ConversationListInfo;

import java.util.ArrayList;

import woofer.com.test.R;

/**
 * Created by YOURFATHERME on 2016/7/10.
 */
public class ConversationActivity extends Activity{
    private ListView listViewConversationList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        listViewConversationList=(ListView)findViewById(R.id.listViewConversationList);
        ArrayList<ConversationListInfo> dataList = new ArrayList<ConversationListInfo>();

        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "Hello World!!!!!这是第一条信息",false));
        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "这是第二条信息",true));
        dataList.add(new ConversationListInfo(R.drawable.ic_launcher, "这是第三条信息", false));
        listViewConversationList.setAdapter(new ConversationListAdapter(this, dataList));

    }
}
