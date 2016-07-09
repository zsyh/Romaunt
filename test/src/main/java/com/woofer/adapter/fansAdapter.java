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

/**粉丝 关注 actiyity的适配器*/
public class fansAdapter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public fansAdapter(Context context, List<Map<String, Object>> data){
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

		public TextView textView;
		public TextView textView1;
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
			convertView=layoutInflater.inflate(R.layout.fansitem, null);
			zujian.image=(ImageView)convertView.findViewById(R.id.OT_fans_avater);
			zujian.image1=(ImageView)convertView.findViewById(R.id.OT_fans_seximg);

			zujian.textView=(TextView)convertView.findViewById(R.id.OT_fans_UserName);
			zujian.textView1=(TextView)convertView.findViewById(R.id.OT_fans_sign);

			convertView.setTag(zujian);
		}else{
			zujian=(Zujian)convertView.getTag();
		}
		//绑定数据
		zujian.image.setBackgroundResource((Integer) data.get(position).get("AVATAR"));
		zujian.image1.setBackgroundResource((Integer) data.get(position).get("SEX"));


		zujian.textView.setText((String) data.get(position).get("USERNAME"));
		zujian.textView1.setText((String)data.get(position).get("SIGN"));


		return convertView;
	}

}
