package com.woofer.adapter;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.os.Environment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.woofer.database.Note;

import com.woofer.activity.Activity_one;
import woofer.com.test.R;

/**为activity_one提供适配器*/
public class ActivityoneAdapter extends BaseAdapter{
    private List<Note> data = null;
    private Context context =null;
    private Activity_one activity_one;
    private LayoutInflater layoutInflater;

    public ActivityoneAdapter(Context context, List<Note> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.textitem, null);
            viewHolder = new ViewHolder();
            viewHolder.tvDate
                    = (TextView)convertView.findViewById(R.id.textitemTV3);
            viewHolder.tvContent
                    = (TextView)convertView.findViewById(R.id.textltemTV2);
            viewHolder.tvNoteID
                    = (TextView)convertView.findViewById(R.id.temp);
            viewHolder.theme
                    =(TextView)convertView.findViewById(R.id.textitemTV1);
            viewHolder.publicstatus
                    =(ImageView)convertView.findViewById(R.id.textitemIV2);
            viewHolder.lable
                    =(TextView)convertView.findViewById(R.id.textitemIV1);
            viewHolder.uploadflag
                    =(TextView)convertView.findViewById(R.id.textitem_uploadflag);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Time time = new Time();
        time.setToNow();
        Time timeItem = data.get(position).getTime();
        if((time.year == timeItem.year)
                &&(time.month+1 == timeItem.month)
                &&(time.monthDay == timeItem.monthDay)){
            viewHolder.tvDate.setText(data.get(position).getTimeStr());
        }else{
            viewHolder.tvDate.setText(data.get(position).getDateStr());
        }
        viewHolder.tvNoteID.setText(data.get(position).getID()+"");
        viewHolder.tvContent.setText(delPathForContent(data.get(position).getNoteData())+"");
        viewHolder.theme.setText(data.get(position).getTheme()+"");
        if(!data.get(position).getLable().equals(""))
            viewHolder.lable.setText(data.get(position).getLable()+"");
        else
            viewHolder.lable.setVisibility(convertView.INVISIBLE);
        if(data.get(position).getPublicstatus()==0){
            viewHolder.publicstatus.setImageResource(R.drawable.img_privacy);

        }else {
            viewHolder.publicstatus.setImageResource(R.drawable.img_public);
            if(data.get(position).getUploadflag()==0){
                viewHolder.uploadflag.setText("未上传");
            }
            else{
                viewHolder.uploadflag.setText("");
            }
        }

        return convertView;
    }

    private String delPathForContent(String content){
        String patternStr = Environment.getExternalStorageDirectory()+"(.*jpg)";;
        Pattern pattern = Pattern.compile(patternStr);
        Matcher m = pattern.matcher(content);
        content = m.replaceAll("");
        patternStr = "\\n";
        pattern = Pattern.compile(patternStr);
        m = pattern.matcher(content);
        content = m.replaceAll("");
        return content;
    }

    public static class ViewHolder{
        public TextView tvNoteID;
        public TextView tvDate;
        public TextView tvContent;
        public TextView theme;
        public ImageView publicstatus;
        public TextView lable;
        public TextView uploadflag;

    }

}
