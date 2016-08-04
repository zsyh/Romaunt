package com.woofer.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.sax.RootElement;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woofer.ui.OpenGl.GLView;

import org.w3c.dom.Text;

public class GithubAboutActivity extends Activity {

    private Button copyurl;
    private TextView url;
    GLView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GLView(this);


        url = new TextView(this);
        url.setText("https://github.com/zsyh9612/Romaunt");
        url.setId(1);
        copyurl = new Button(this);
        copyurl.setText("拷贝链接到剪贴板");
        copyurl.setId(2);

        RelativeLayout mLayout = new RelativeLayout(this);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mLayout.setBackground(Color.rgb(Color.parseColor()));
        }
*/
        RelativeLayout.LayoutParams  urlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        urlparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        urlparams.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams  copyurlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        copyurlparams.addRule(RelativeLayout.BELOW,url.getId());
        copyurlparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        copyurlparams.setMargins(0,0,0,80);

        RelativeLayout.LayoutParams OpenGlparams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        OpenGlparams.addRule(RelativeLayout.BELOW,copyurl.getId());

        mLayout.addView(url,urlparams);
        mLayout.addView(copyurl,copyurlparams);
        mLayout.addView(view,OpenGlparams);

        copyurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("https://github.com/zsyh9612/Romaunt");
                Toast.makeText(GithubAboutActivity.this, "链接已复制到剪贴板", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
