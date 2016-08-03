package com.woofer.commentandreply.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.woofer.commentandreply.date.Commentdate;
import com.woofer.commentandreply.date.Replydate;
import com.woofer.commentandreply.view.NoScrollListView;
import com.woofer.util.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import woofer.com.test.R;

/**
 * Created by Admin on 2016/5/27.
 */
public class CommentAdapter extends BaseAdapter {
    /**传入布局文件*/
    private int resourceId;
    private Context context;
    /**handl？？？？？？*/
    private Handler handler;
    private List<Commentdate> list;
    private LayoutInflater inflater;
    private ViewHolder mholder = null;
    private URL url;
    /**是否可以删除*/
//    private Map<Integer, Boolean> isVisible;

    private android.view.animation.Animation animation;//动画效果的

    public CommentAdapter(Context context, List<Commentdate> list
            , int resourceId, Handler handler) {
        this.list = list;
        this.context = context;
        this.handler = handler;
        this.resourceId = resourceId;
        inflater = LayoutInflater.from(context);
    }
//
//    public void addMap(int id) {
//        isVisible.put(id, false);
//    }
//
//    private void setPaise() {
//        isVisible = new HashMap<Integer, Boolean>();
//        for (int i = 0; i < list.size(); i++) {
//            //规避ID重复的风险
//            addMap(list.get(i).getPositon());
//        }
//    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Commentdate bean = list.get(position);
//		ViewHolder mholder = null;

        if (convertView == null) {
            mholder = new ViewHolder();
            convertView = inflater.inflate(resourceId, null);
            mholder.commentNickname = (TextView)
                        convertView.findViewById(R.id.comment_name);
            mholder.commentItemTime = (TextView)
                    convertView.findViewById(R.id.comment_time);
            mholder.commentItemContent = (TextView)
                    convertView.findViewById(R.id.comment_content);
            mholder.replyList = (NoScrollListView)
                    convertView.findViewById(R.id.no_scroll_list_reply);

            mholder.replyBut = (Button)
                    convertView.findViewById(R.id.but_comment_reply);
            mholder.avatarpic = (ImageView)convertView.findViewById(R.id.comment_area_avatar);

            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }

        int userid = bean.getCommnetAccount();

        //改写绑定数据 加载用户头像
        if(!bean.avatar.equals("")){
            File imgfile = new File(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
            if(imgfile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/cacheFile/cache" + userid + ".png");
                mholder.avatarpic.setImageBitmap(bitmap);
            }else {
                try {
                    url = new URL(bean.avatar);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Utils.onLoadImage(url,new Utils.OnLoadImageListener() {
                    @Override
                    public void OnLoadImage(Bitmap bitmap, String bitmapPath, int userid) {
                        if (bitmap != null) {
                            mholder.avatarpic.setImageBitmap(bitmap);
                        } else {

                        }
                    }
                },userid);
            }
        }
        mholder.commentNickname.setText(bean.getCommentNickname());
        mholder.commentItemTime.setText(bean.getCommentTime());
        mholder.commentItemContent.setText(bean.getCommentContent());



        final ReplyAdapter adapter = new ReplyAdapter(context, bean.getReplyList(), R.layout.reply_item);
        mholder.replyList.setAdapter(adapter);
        TextviewClickListener tcl = new TextviewClickListener(position);
        mholder.replyBut.setOnClickListener(tcl);

        return convertView;
    }

    private final class ViewHolder {
        public TextView commentNickname;            //评论人昵称
        public TextView commentItemTime;            //评论时间
        public TextView commentItemContent;         //评论内容
        public NoScrollListView replyList;          //评论回复列表
        public Button replyBut;                     //回复
        public ImageView avatarpic;                 //
        public ImageView sexpic;                    //

    }

    /**
     * 获取回复评论
     */
    public void getReplyComment(Replydate bean, int position) {
        List<Replydate> rList = list.get(position).getReplyList();
        rList.add(rList.size(), bean);
    }

    /**
     * 事件点击监听器
     */
    private final class TextviewClickListener implements View.OnClickListener {
        private int position;

        public TextviewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_reply:
                    handler.sendMessage(handler.obtainMessage(1, position));
                    break;

            }
        }
    }

}
