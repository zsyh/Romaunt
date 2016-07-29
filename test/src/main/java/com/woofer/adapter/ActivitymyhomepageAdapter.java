package com.woofer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import woofer.com.test.R;

/**隔壁老王适配器*/
public class ActivitymyhomepageAdapter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public ActivitymyhomepageAdapter(Context context, List<Map<String, Object>> data){
		this.context=context;
		this.data=data;
		this.layoutInflater=LayoutInflater.from(context);
	}
	/**
	 * 组件集合，对应list.xml中的控件
	 * @author Administrator
	 */
	public final class Zujian{
		public ImageView image;
		public ImageView image1;
		public ImageView image2;
		public ImageView image3;
		public ImageView image4;
		public TextView textView;
		public TextView textView1;
		public TextView textView2;
		public TextView textView3;
		public TextView textView4;

	}
	@Override
	public int getCount() {
		return data.size();
	}
	/**
	 * 获得某一位置的数据
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	/**
	 * 获得唯一标识
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Zujian zujian=null;
		if(convertView==null){
			zujian=new Zujian();
			//获得组件，实例化组件
			convertView=layoutInflater.inflate(R.layout.list, null);
			zujian.image=(ImageView)convertView.findViewById(R.id.itemIV1);
			zujian.image1=(ImageView)convertView.findViewById(R.id.itemIV2);
			zujian.image2=(ImageView)convertView.findViewById(R.id.itemIV3);
			zujian.image3=(ImageView)convertView.findViewById(R.id.activity_one_itemIV4);
			zujian.image4=(ImageView)convertView.findViewById(R.id.itemIV5);
			zujian.textView=(TextView)convertView.findViewById(R.id.itemTV1);
			zujian.textView1=(TextView)convertView.findViewById(R.id.itemTV2);
			zujian.textView2=(TextView)convertView.findViewById(R.id.itemTV3);
			zujian.textView3=(TextView)convertView.findViewById(R.id.itemTV4);
			zujian.textView4=(TextView)convertView.findViewById(R.id.itemTV5);
			convertView.setTag(zujian);
		}else{
			zujian=(Zujian)convertView.getTag();
		}
		//绑定数据
		zujian.image.setBackgroundResource((Integer) data.get(position).get("image"));
		zujian.image1.setBackgroundResource((Integer) data.get(position).get("image1"));
		zujian.image2.setBackgroundResource((Integer) data.get(position).get("image2"));
		zujian.image3.setBackgroundResource((Integer) data.get(position).get("image3"));
		zujian.image4.setBackgroundResource((Integer) data.get(position).get("image4"));

		zujian.textView.setText((String) data.get(position).get("textView"));
		zujian.textView1.setText((String)data.get(position).get("textView1"));
		zujian.textView2.setText((String)data.get(position).get("textView2"));
		zujian.textView3.setText((String)data.get(position).get("textView3"));
		zujian.textView4.setText((String)data.get(position).get("textView4"));

		return convertView;
	}

}
