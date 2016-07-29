package com.woofer.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.util.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import woofer.com.test.R;

/**粉丝 关注 actiyity的适配器*/
public class FansAdapter extends BaseAdapter {

	private URL url;
	private int userid;
	private String avatar;
	private Zujian zujian;
	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public FansAdapter(Context context, List<Map<String, Object>> data){
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
		zujian=null;
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
		//zujian.image.setBackgroundResource((Integer) data.get(position).get("AVATAR"));
		avatar = String.valueOf(data.get(position).get("AVATAR"));
		userid = Integer.parseInt(String.valueOf(data.get(position).get("USERID")));

		if(!avatar.equals("")){
			File imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
			if(imgfile.exists()){
				Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
				zujian.image.setImageBitmap(bitmap);
			}else{
				try{
					url = new URL(avatar);
				}catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Utils.onLoadImage(url, new Utils.OnLoadImageListener() {


					public void setResources(Resources resources) {
						this.resources = resources;
					}

					private Resources resources;

					public Resources getResources() {
						return resources;
					}

					@Override
					public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
						if (bitmap != null) {
							zujian.image.setImageBitmap(bitmap);
						} else {
							Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_defaultavatar);
							zujian.image.setImageBitmap(bitmap1);
						}

					}
				},userid);
			}
		}
		//zujian.image1.setBackgroundResource((Integer) data.get(position).get("SEX"));

		if(Integer.parseInt(String.valueOf(data.get(position).get("SEX")))==2){
			zujian.image1.setImageResource(R.drawable.img_small_female);
		}else if(Integer.parseInt(String.valueOf(data.get(position).get("SEX")))==1){
			zujian.image1.setImageResource(R.drawable.img_small_male);
		}else{
			zujian.image1.setVisibility(View.INVISIBLE);
		}
		zujian.textView.setText((String) data.get(position).get("USERNAME"));
		zujian.textView1.setText((String)data.get(position).get("SIGN"));


		return convertView;
	}

}
