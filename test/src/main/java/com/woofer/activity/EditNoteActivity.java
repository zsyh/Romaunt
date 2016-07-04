package com.woofer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Time;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.Bitmaptools.BitmapTools;
import com.woofer.database.DatabaseManager;
import com.woofer.database.Note;
import com.woofer.ui.CustomDialog;
import com.woofer.ui.StringSpanEdit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Random;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class EditNoteActivity extends Activity {

    /*所保存图片的文件夹*/
    public final static String IMG_DIR = "ImgRESORCES";

    private EditText themeeditTX = null;
    private ImageButton imgbtn1;
    private ImageButton btncreame = null;
    private Button save = null;
    private Button cancle = null;
    private TextView tVtime;
    private TitleBar edittitleBar;
    private StringSpanEdit stringSpanEdit=null;
    //当前图片路径
    private String ImgPath="";
    private Uri originalUri = null;
    /*不要用成java.spl.time*/
    private Time time =new Time();
    private DatabaseManager dataBase=null;
    /**用来区分新旧note的标志*/
    private int noteID   = -1;
    private int spanEnd;
    private EditText editText;
    private ImageButton imageButton;
    private EditText editText_lable;
    /*设置公开私密状态*/
    private int flag;
    private ImageButton imabtn1;
    private int uploadflag = 0;




    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnote);
        /**获取时间的操作**/
        time.setToNow();
        /*
        *这句话是用来做什么?
        *将该项目中包含的原始intent检索出来????
        */
        Intent intent =getIntent();
        noteID =intent.getIntExtra("id",-1);
        dataBase = new DatabaseManager(this);
        initComperment();
        if(noteID !=-1){
            freshnotedata(noteID);
        }
        imabtn1 = (ImageButton)findViewById(R.id.editnote_imgbtn);
        if(flag==0){
            imabtn1.setImageResource(R.drawable.img_privacy);
        }else{
            imabtn1.setImageResource(R.drawable.img_public);
        }
            imabtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = 1-flag;
                    if(flag==0){
                        imabtn1.setImageResource(R.drawable.img_privacy);
                    }else{
                        imabtn1.setImageResource(R.drawable.img_public);
                    }
                }
            });

    }





    /**http://blog.csdn.net/jiangwei0910410003/article/details/16983049**/
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //对Activity中传回的requestCode进行判断 处理usecream 和 selectimg
        //resultCode 调用什么方法传回的!!!

        if(resultCode==RESULT_OK) {
            //BitmapFactory加载手机磁盘上的资源
            Bitmap bitmap = null;
            switch (requestCode) {
                /**使用相机*/
                case 1:
                    /*对图片进行按比例缩放*/
                    bitmap = BitmapFactory.decodeFile(ImgPath);
                    Bitmap resizeBitMap = BitmapTools.getScaleBitmap(bitmap, 0.5f, 0.5f);
                    /*对bitmap进行销毁*/
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    /*插入edittext*/
                    stringSpanEdit.insertImgInText(resizeBitMap, ImgPath);
                    /*对插入的图片进行判断 调用不同函数 正则表达式不一样!!!*/
                    break;
                /**使用相册*/
                case 0:
                    /**外界的程序访问ContentProvider所提供的数据*/
                    ContentResolver resolver = getContentResolver();
                    originalUri = data.getData();
                    Bitmap originalBitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        /**获得用户选择的图片的索引值*/
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        /**根据索引值获取图片路径*/
                        ImgPath = cursor.getString(column_index);
                        originalBitmap = BitmapTools.getScaleBitmap(bitmap, 0.5f, 0.5f);
                        stringSpanEdit.insertImgInText(originalBitmap, ImgPath);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /* try {

                        bitmap =BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                        originalBitmap = BitmapFactory.decodeStream(resolver
                                .openInputStream(originalUri));
                        bitmap = BitmapTools.getScaleBitmap(originalBitmap, 0.6f, 0.6f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (!(bitmap == null)){
                        editText = (EditText) findViewById(R.id.SpanediteT1_2);
                        insertIntoEditText(getBitmapMime(bitmap, originalUri));
                    }*/
            }
        }
            else{
            /**放弃拍照*/
            Toast.makeText(getApplicationContext(),"获取图片失败",Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        /*
        *http://www.androidchina.net/4197.html
        *考虑到图片需要上传到服务器
        *0.上传
        *1.用 ContentResolver获取封装的uri数据
        *2.BitmapFactory解析成流
        * */

        /*
        * 图片的上传
        * 每插入一张图片就进行一次上传或点击保存才开始上传*/
    }


    private SpannableString getBitmapMime(Bitmap pic, Uri uri){
        String path =uri.getPath();
        SpannableString ss = new SpannableString(path);
        ImageSpan span =new ImageSpan(this, pic);
        ss.setSpan(span, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    private void insertIntoEditText(SpannableString ss){
        Editable et =editText.getText();//获取edittext中的内容
        int start = editText.getSelectionStart();
        et.insert(start, ss);//设置ss要添加的位置
        editText.setText(et);//把er添加到Edittext中
        editText.setSelection(start + ss.length());//设置Edittext中光标在最后显示
    }
    private void insertPic(){

    }
    //上传成功 显示图片
    public void issuccess(Object t){
        // 根据Bitmap对象创建ImageSpan对象
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        // 用ImageSpan对象替换你指定的字符串
        // 将选择的图片追加到EditText中光标所在位置
        // 获取光标所在位置
    }
    public void fail(){}
    private void initComperment() {

        editText_lable = (EditText)findViewById(R.id.editnote_lable);

        edittitleBar = (TitleBar) findViewById(R.id.editactionbar1);
        edittitleBar.setLeftImageResource(R.drawable.img_x_white);
        edittitleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        edittitleBar.setRightImageResource(R.drawable.imgsrc);
        edittitleBar.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( themeeditTX.getText().toString().equals("") ){
                    Toast.makeText(EditNoteActivity.this,"题目不能为空!",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(stringSpanEdit.getText().toString().equals("")){
                    Toast.makeText(EditNoteActivity.this,"内容不能为空!",Toast.LENGTH_SHORT).show();

                    return ;
                }

                if( editText_lable.getText().toString().equals("")){
                    Toast.makeText(EditNoteActivity.this,"标签不能为空!",Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (noteID == -1) {
                    saveNote(stringSpanEdit.getText().toString(), themeeditTX.getText().toString(),editText_lable.getText().toString(),flag,uploadflag);
                } else {
                    updateNote(noteID, stringSpanEdit.getText().toString(),
                            themeeditTX.getText().toString(),editText_lable.getText().toString(),flag);


                }
                toMainActivity();
            }
        });

        imageButton = (ImageButton)findViewById(R.id.act_editnote_label);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LayoutInflater inflater = (LayoutInflater) EditNoteActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dialog_layoutforlabel, null);
                new AlertDialog.Builder(EditNoteActivity.this)
                        .setTitle("编辑标签").setMessage("编辑").setView(view)
                        .setPositiveButton((CharSequence) btn1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editText = (EditText) findViewById(R.id.dialog_ET);
                                String str = editText.getText().toString();
                                Toast.makeText(EditNoteActivity.this, str, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton((CharSequence) btn2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }*/


                LayoutInflater layoutInflater = LayoutInflater.from(EditNoteActivity.this);
//                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_layoutforlabel, null);
                View conView = layoutInflater.inflate(R.layout.dialog_layoutforlabel,null);
                editText = (EditText)conView.findViewById(R.id.dialog_ET);

                new AlertDialog.Builder(EditNoteActivity.this)
                        .setIcon(android.R.drawable.ic_menu_gallery)

                        .setView(conView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            editText_lable.setText(editText.getText().toString());
                            dialog.dismiss();

                        //toMainActivity();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        // TODO Auto-generated method stub

                        // do something

                        dialog.dismiss();

                    }

                }).show();



                /*editText = (EditText)conView.findViewById(R.id.dialog_ET);
                CustomDialoglabel.Builder builder = new CustomDialoglabel.Builder(EditNoteActivity.this);
                builder.setMessage("编辑？")
                        .setTitle("编辑标签").setContentView(conView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().equals("")) {
                                    String str = editText.getText().toString();
                                    Toast.makeText(EditNoteActivity.this, str, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                //toMainActivity();

                            }
                        }).setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();*/
        /*
        *重写dialog
        */
               /* AlertDialog.Builder dialog = new AlertDialog.Builder(EditNoteActivity.this);
                LayoutInflater inflater = (LayoutInflater) EditNoteActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_layoutforlabel, null);
                dialog.setView(layout);

                ((Button)layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                dialog.setPositiveButton("查找", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("search", searchC);
                        intent.putExtras(bundle);
                        intent.setClass(EditNoteActivity.this, MainActivity.class);
                        EditNoteActivity.this.startActivity(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                dialog.show();*/

            }
        });
        btncreame = (ImageButton) findViewById(R.id.editimgbtn1_2);
        btncreame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usecamera();
            }
        });
        imgbtn1 =(ImageButton)findViewById(R.id.editimgbtn1_1);
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useGallery();
            }
        });
        stringSpanEdit=(StringSpanEdit)findViewById(R.id.SpanediteT1_2);
        themeeditTX = (EditText)findViewById(R.id.editeT1_1);
        tVtime=(TextView)findViewById(R.id.edittV1_4);
        if(noteID ==-1){
            String dateNtime=time.year+"-"+(time.month+1)+"-"+
                    time.monthDay+" "+time.hour+":"+time.minute;
            tVtime.setText(dateNtime);
        }
    }


    /**
     * 使用相机操作
     * (前先判断sd卡是否有空间)
     **/
    private void usecamera() {
        if (!isexistsandmkdir(IMG_DIR)) {
                return;
        }
        //拿到当前的时间 对相片进行命名
        Time time =new Time();
        time.setToNow();
        Random andom=new Random();

        /*月份返回的是0~11*/
        String imgName=time.year+""+(time.month+1) +""+time.monthDay+
                ""+time.hour+""+time.minute+""+time.second+""
                +andom.nextInt(1000)+".jpg";

        ImgPath=Environment.getExternalStorageDirectory()+"/"+IMG_DIR+"/"+imgName;
        File f =new File(ImgPath);

        Uri uri =Uri.fromFile(f);
        //创建一个获取图像的INTENT
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //设置图像文件名
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        //视频获取intent设置最高质量最大文件尺寸
        //http://blog.csdn.net/niu_gao/article/details/7658008
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(intent, 1);
    }

    /**
    *使用相册 没想好怎么写
    */
    public void useGallery(){
        Intent getImage =new Intent(Intent.ACTION_GET_CONTENT);
        //增加一个可以打开的分类---即获得的uri可以被ContentResolver解析
        getImage.addCategory(Intent.CATEGORY_OPENABLE);
        getImage.setType("image/*");
        startActivityForResult(getImage,0);
    }

    /**
     * 判断文件夹是否创建成功
    *exists() mkdir()
    *return是否存在
    */
    private boolean isexistsandmkdir(String dirpath) {
        //获取存储卡状态
        String sdcard = Environment.getExternalStorageState();
        //sd卡未正常挂载。// /mnt/sdCard目录存在  canRead canWrite
        if (!sdcard.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "未检测到存储卡", Toast.LENGTH_SHORT).show();
            return false;
        }
        //获取外部存储(包括手机自带的内部存储)路径
        File f=new File(Environment.getExternalStorageDirectory()+"/"+dirpath);
        if(f.exists()){
            return true;
        }
        //是否创建成功文件夹
        boolean isSuccess=f.mkdir();
        if(isSuccess){
            return true;
        }
        return false;
    }
    /**
     * 按下返回键跳转回主界面
     */
    @Override
    public void onBackPressed(){
        CustomDialog.Builder builder = new CustomDialog.Builder(EditNoteActivity.this);
        builder.setMessage("确定放弃编辑？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                        toMainActivity();
            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
        /*
        *重写dialog
        */
    }
    public void toMainActivity(){

        //从栈中销毁其上的Activity
        //MainActivity嵌套viewpager这时候应该怎么选择参数合理出栈？
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }


    /**
     *将日记保存起来
     *
     * @param  content
     */
    private  void saveNote(String content,String theme, String lable, int status, int uploadflag){
        Time time = new Time();
        time.setToNow();
        String dateNtime = time.year+
                    "-"+(time.month+1)+"-"+time.monthDay+" "
                    +time.hour+":"+time.minute;
        dataBase.executeWriteMsg(dateNtime, content, theme, lable, status, uploadflag);


    }
    /**
     *将日记进行更新操作
     */
    private void updateNote(int id, String content, String theme ,String lable, int status){
        Time time =new Time();
        time.setToNow();
        String dateNtime =  time.year+
                "-"+(time.month+1)+"-"+time.monthDay+" "
                +time.hour+":"+time.minute;
        dataBase.updateNote(id, dateNtime, content, theme, lable,status);

    }
    /**
    *将要更新的数据从数据库读取出，放到界面上
    */
    private void freshnotedata(int id){
        Note note=dataBase.readData(id);
        this.themeeditTX.setText(note.getTheme());
        this.stringSpanEdit.setSpanContext(note.getNoteData());
        this.tVtime.setText(note.getDateTimeStr());
        this.editText_lable.setText(note.getLable());
        this.flag = note.getPublicstatus();
    }

}



