package com.woofer.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.titlebar.TitleBar;
import com.woofer.ui.OpenGl.GLView;

import woofer.com.test.R;

public class GithubAboutActivity extends Activity {

    private Button copyurl;
    private TextView url;
    private TitleBar titleBar;
    private TextView From;
    private TextView By;

    GLView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GLView(this);
        view.setId(3);


        url = new TextView(this);
        url.setText("https://github.com/zsyh9612/Romaunt");
        url.setTextColor(Color.rgb(255,255,255));
        url.setId(1);
        url.setTextSize(20);
        copyurl = new Button(this);
        copyurl.setText(" 拷贝链接到剪贴板 ");
        copyurl.setId(2);
        copyurl.setBackgroundResource(R.drawable.btnstylegithub);
        copyurl.setTextColor(Color.rgb(255,255,255));
        titleBar = new TitleBar(this);
        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubAboutActivity.this.finish();
            }
        });
        From = new TextView(this);
        From.setText("From OUREDA Embeded in Android");
        From.setTextColor(Color.rgb(255,255,255));
        From.setId(5);
        From.setTextSize(14);

        By = new TextView(this);
        By.setText("By Zaizai && woofer");
        By.setTextColor(Color.rgb(255,255,255));
        By.setId(6);
        By.setTextSize(18);


        RelativeLayout mLayout = new RelativeLayout(this);
        //mLayout.setBackgroundColor(Color.rgb(255,147,59));
        mLayout.setBackgroundResource(R.drawable.setbackground);
        mLayout.setId(4);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mLayout.setBackground(Color.rgb(Color.parseColor()));
        }
*/

        RelativeLayout.LayoutParams  urlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        urlparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        urlparams.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams  Fromparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Fromparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Fromparams.addRule(RelativeLayout.BELOW,url.getId());
        RelativeLayout.LayoutParams  Byparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        Byparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Byparams.addRule(RelativeLayout.BELOW,From.getId());


        RelativeLayout.LayoutParams  copyurlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        copyurlparams.addRule(RelativeLayout.BELOW,By.getId());
        copyurlparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        copyurlparams.setMargins(0,0,0,80);

        RelativeLayout.LayoutParams OpenGlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 560);
        OpenGlparams.addRule(RelativeLayout.ABOVE,url.getId());
        OpenGlparams.setMargins(0,100,0,100);
        RelativeLayout.LayoutParams titleparams=
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        titleparams.addRule(RelativeLayout.ALIGN_LEFT,view.getId());
        titleparams.addRule(RelativeLayout.ABOVE,view.getId());
        mLayout.addView(view,OpenGlparams);
        mLayout.addView(titleBar,titleparams);
        mLayout.addView(url,urlparams);
        mLayout.addView(copyurl,copyurlparams);
        mLayout.addView(From,Fromparams);
        mLayout.addView(By,Byparams);


        copyurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("https://github.com/zsyh9612/Romaunt");
                Toast.makeText(GithubAboutActivity.this, "链接已复制到剪贴板", Toast.LENGTH_SHORT).show();
            }
        });


        setContentView(mLayout);

    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }
}
