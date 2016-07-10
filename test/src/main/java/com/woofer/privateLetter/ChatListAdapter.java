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
public class ChatListAdapter extends BaseAdapter{

    private ArrayList<ChatListInfo> dataList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ChatListAdapter(Context context,ArrayList<ChatListInfo> dataList) {//构造函数
        this.context=context;
        this.dataList = dataList;
        this.layoutInflater=LayoutInflater.from(context);//从xml中找出对应的控件并实例化
    }

    public final class ViewHolder{//内部类，用于控件缓存
        public ImageView imageView;
        public TextView textViewTitle;
        public TextView textViewMessage;
        public TextView textViewTime;
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
        if(convertView==null){
            viewHolder=new ViewHolder();
            //获得组件，实例化组件
            convertView=layoutInflater.inflate(R.layout.chatlist_item, null);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.textViewTitle=(TextView)convertView.findViewById(R.id.textViewTitle);
            viewHolder.textViewMessage=(TextView)convertView.findViewById(R.id.textViewMessage);
            viewHolder.textViewTime=(TextView)convertView.findViewById(R.id.textViewTime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //绑定数据
        ChatListInfo chatListInfo = dataList.get(position);
        viewHolder.imageView.setImageResource(chatListInfo.getList_img());
        viewHolder.textViewTitle.setText(chatListInfo.getList_title());
        viewHolder.textViewMessage.setText(chatListInfo.getList_message());
        viewHolder.textViewTime.setText(chatListInfo.getList_time());

        return convertView;
    }



}
