package com.woofer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woofer.refreshlayout.model.ParhsModel;

import java.util.List;

import woofer.com.test.R;

/**activity_two适配器*/
public class ParasAdapter extends BaseAdapter {

	private List<ParhsModel> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public ParasAdapter(Context context, List<ParhsModel> data){
		this.context=context;
		this.data=data;
		this.layoutInflater=LayoutInflater.from(context);
	}
	/**
	 * 组件集合，对应list.xml中的控件
	 * @author Administrator
	 */
	public final class Zujian{
		public TextView title;
		public TextView lable;
		public TextView content;
		public TextView thumbNUM;
		public TextView time;

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
			convertView=layoutInflater.inflate(R.layout.parhsitem, null);

			zujian.time=(TextView)convertView.findViewById(R.id.OT_parhs_time);
			zujian.title=(TextView)convertView.findViewById(R.id.OT_parhs_title);
			zujian.content=(TextView)convertView.findViewById(R.id.OT_parhs_content);
			zujian.thumbNUM=(TextView)convertView.findViewById(R.id.OT_parhs_thumbNUM);
			zujian.lable=(TextView)convertView.findViewById(R.id.OT_parhs_lable);


			convertView.setTag(zujian);
		}else{
			zujian=(Zujian)convertView.getTag();
		}
		//绑定数据
		zujian.time.setText(data.get(position).time);
		zujian.title.setText(data.get(position).title);
		zujian.content.setText(data.get(position).content);
		zujian.thumbNUM.setText(Integer.toString(data.get(position).thumbNUM));
		zujian.lable.setText( data.get(position).flags);


		return convertView;
	}

}
