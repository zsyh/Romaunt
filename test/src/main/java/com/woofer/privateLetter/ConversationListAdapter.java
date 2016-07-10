package com.woofer.privateLetter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import woofer.com.test.R;


/**
 * Created by YOURFATHERME on 2016/7/10.
 */
public class ConversationListAdapter extends BaseAdapter{
    private ArrayList<ConversationListInfo> dataList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ConversationListAdapter(Context context, ArrayList<ConversationListInfo> dataList) {//构造函数
        this.context=context;
        this.dataList = dataList;
        this.layoutInflater= LayoutInflater.from(context);//从xml中找出对应的控件并实例化
    }

    public final class ViewHolder{//内部类，用于控件缓存
        public ImageView imageView;
        public TextView textViewText;

    }


    @Override
    public int getCount() {

        return dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        ConversationListInfo conversationListInfo = dataList.get(position);
        if(convertView==null){
            viewHolder=new ViewHolder();
            //获得组件，实例化组件


            //根据是否是我，选择性地加载两套布局
            if(conversationListInfo.getList_isme())
            {

                convertView=layoutInflater.inflate(R.layout.conversation_listitem_right, null);
            }
            else
            {

                convertView=layoutInflater.inflate(R.layout.conversation_listitem_left, null);
            }


            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.textViewText=(TextView)convertView.findViewById(R.id.textViewText);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //绑定数据

        viewHolder.imageView.setImageResource(conversationListInfo.getList_img());
        viewHolder.textViewText.setText(conversationListInfo.getList_text());

        return convertView;
    }



}


