package com.woofer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;


import com.woofer.adapter.ActivityoneAdapter;
import com.woofer.database.DatabaseManager;
import com.woofer.database.Note;
import com.woofer.database.NoteData;
import com.woofer.SwipeMenu.SwipeMenu;
import com.woofer.SwipeMenu.SwipeMenuCreator;
import com.woofer.SwipeMenu.SwipeMenuItem;
import com.woofer.SwipeMenu.SwipeMenuListView;
import com.woofer.SwipeMenu.SwipeMenuListView.OnMenuItemClickListener;

import woofer.com.test.R;

import com.woofer.net.GetStoryResponse;
import com.woofer.net.RomauntNetWork;
import com.woofer.net.RomauntNetworkCallback;
import com.woofer.net.UploadStoryResponse;
import com.woofer.titlebar.TitleBar;

import java.util.List;

public class Activity_one extends Activity {
    private TitleBar act1;
    private ImageButton img1;
    private int itemsum=0;
    private NoteData notedata=null;
    private DatabaseManager databaseManager=null;
    private SwipeMenuListView listview = null;
    private ActivityoneAdapter madapeter = null;
    private long firstbacktime = 0;
    /*private  List<Map<String, Object>> list;
    private AppAdapter madapter;
    private ActivityoneAdapter adapter=null;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_one);

        /**新建适配器 绑定数据结构 塞数据没做*/
        databaseManager = new DatabaseManager(this);
        listview = (SwipeMenuListView) findViewById(R.id.mlistview);
        notedata = new NoteData(this);
        madapeter = new ActivityoneAdapter(this, notedata.getNoteDataList());
        itemsum = notedata.getNoteDataList().size();
        listview.setDivider(null);
        /**item的单击事件 跳转到编辑页面*/
        listview.setOnItemClickListener(new noteclicklistener());
        listview.setAdapter(madapeter);


        /***/

        //注意不要写成匿名内部类//
        /*list = getData();
        madapter= new AppAdapter(this,list);
        listView.setAdapter(madapter);
*/

        act1=(TitleBar)findViewById(R.id.actionbar1);
        act1.setLeftImageResource(R.drawable.img_cloud);
        act1.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(Activity_one.this, "上传所有公开笔记", Toast.LENGTH_SHORT).show();


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        List<Note> noteDataList=notedata.getNoteDataList();

                        RomauntNetWork romauntNetWork=new RomauntNetWork();
                        for(int i = 0 ;i<noteDataList.size();i++){
                            if(noteDataList.get(i).getPublicstatus()==1 && (noteDataList.get(i).getUploadflag()==0))//上传要上传但未上传的
                            {
                                final int ID=  noteDataList.get(i).getID();
                                final String title = noteDataList.get(i).getTheme();
                                final String flags= noteDataList.get(i).getLable();
                                final String content= noteDataList.get(i).getNoteData();

                                SharedPreferences sp  = getSharedPreferences("userinfo",signinActivity.MODE_PRIVATE);
                                String logintoken = sp.getString("LOGINTOKEN","");

                                Object response =romauntNetWork.uploadStorySync(logintoken,title,flags,content,"1");
                                if(response==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Activity_one.this,"网络无连接",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return ;
                            }
                                if (response instanceof UploadStoryResponse) {
                                    UploadStoryResponse uploadStoryResponse= (UploadStoryResponse)response;
                                    if(uploadStoryResponse.status.equals("true"));
                                    {
                                        databaseManager.Reuploadflag(ID,1);
                                        noteDataList.get(i).setUploadflag(1);

                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {

                                               madapeter.notifyDataSetChanged();
                                           }
                                       });


                                    }

                                }
                                else{
                                    Log.e("Romaunt","upload story status false");

                                }


                            }



                        }

                    }


                }).start();

            }
        });
        act1.setRightImageResource(R.drawable.img_add);
        //跳转到写日记界面
        //。。。———————error
        act1.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Activity_one.this,EditNoteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //step 1; create a MenuCreator;
        final SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                createMenu1(menu);
               /* switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                   */
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item2 = new SwipeMenuItem(
                      getApplicationContext());
                item2.setTitle("删除");
                item2.setTitleSize(28);
                item2.setTitleColor(R.color.colorwrite);
                item2.setWidth(dp2px(116));
                item2.setBackground(new ColorDrawable(Color.rgb(251, 102, 102)));
                menu.addMenuItem(item2);
            }
        };
        // set cteator
        listview.setMenuCreator(creator);
        //step2; listener item click event
        listview.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {

                //Map<String, Object> item= mApplist.get(position);

                /**
                 * 注意三句话的顺序 写错会导致数据库无法删除
                 */
                databaseManager.deleteNote(notedata.getNoteDataList().get(position).getID());
                notedata.getNoteDataList().remove(position);//注意匿名内部类的写法
                madapeter.notifyDataSetChanged();

                //绑定侧滑icon的事件
                /*switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        //delete(item);
                        mApplist.remove(position);
                        madapter.notifyDataSetChanged();
                        break;
                }*/
            }
        });
    }

    private class noteclicklistener  implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ActivityoneAdapter.ViewHolder viewHolder = (ActivityoneAdapter.ViewHolder)view.getTag();

            /**为什么不能访问id*/
            String itemID = Integer.toString(notedata.getNoteDataList().get(position).getID());
            //viewHolder.tvNoteID.getText().toString().trim();
            toEditNoteActivity(Integer.parseInt(itemID));
        }
        private void toEditNoteActivity(int id){
            Intent intent = new Intent(Activity_one.this,EditNoteActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /*public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image1", R.drawable.img_warning);
            map.put("image2", R.drawable.img_public);

            map.put("textView1", "此为公开内容");
            map.put("textView2", "所有人可以阅读，左滑可以删除");
            map.put("textView3", "20:20:19");
            list.add(map);
            map=new HashMap<String, Object>();
        map.put("image1", R.drawable.img_warning);
        map.put("image2", R.drawable.img_public);

        map.put("textView1", "此为公开内容");
        map.put("textView2", "所有人可以阅读，左滑可以删除");
        map.put("textView3", "20:20:19");
        list.add(map);

        return list;
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long currentTime=System.currentTimeMillis();
            if(currentTime - firstbacktime <= 2000){
                this.finish();
            }else{
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstbacktime = currentTime;
            }
        }
        return true;
    }
}


/*  holder.text_tV1.setText(item.loadLabel(getPackageManager()));
            holder.text_img2.setImageResource(R.drawable.img_public);
            holder.text_tV2.setText("只有自己可以阅读，左滑可以删除，最多显示两行");
            holder.text_tV3.setText("20:");*/
