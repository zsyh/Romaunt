package com.woofer.refreshlayout.util;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class Utils {
    public static void onLoadImage(final URL bitmapUrl,final OnLoadImageListener onLoadImageListener){
            final Handler handler = new Handler(){
                public void handleMessage(Message msg){
                onLoadImageListener.OnLoadImage((Bitmap) msg.obj, null);
            }
    };

    new Thread(new Runnable(){
        @Override
       public void run() {
            // TODO Auto-generated method stub

            if (bitmapUrl.equals("")) {

            } else {
                URL imageUrl = bitmapUrl;

            try {
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
                if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    System.out.println("存在sd卡");
                    File cacheFile = new File(Environment.getExternalStorageDirectory() + "/cacheFile");
                    System.out.println(cacheFile.getPath());
                    if (!cacheFile.exists())
                        cacheFile.mkdir();
                    System.out.println(cacheFile.exists());
                    File imageCache = new File(cacheFile.getPath() + "/netwrok.png");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(imageCache);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        }
            }).start();
        }

    public interface OnLoadImageListener{
    public void OnLoadImage(Bitmap bitmap,String bitmapPath);
    }
}
