package woofer.com.romaunt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class LoginactivityActivity extends Activity  {

    private TitleBar LoginTitleBar1;
    private Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        LoginTitleBar1=(TitleBar)findViewById(R.id.Loginactionbar);
        LoginTitleBar1.setLeftImageResource(R.drawable.img_arrow_register);
        LoginTitleBar1.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();

            }
        });

        btn1=(Button)findViewById(R.id.button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dlg = new AlertDialog.Builder(LoginactivityActivity.this).create();
                dlg.show();
                dlg.getWindow().setContentView(R.layout.alert_style);
                dlg.getWindow().setLayout(940, 460);
                //设置使用shape布局 是dialog圆角化
                android.view.Window window = dlg.getWindow();
                window.setBackgroundDrawableResource(R.drawable.shape);
                /*new AlertDialog.Builder(LoginactivityActivity.this)
                        .setTitle("\n账户或密码错误\n")
                        .setIcon(R.drawable.img_warning)
                        .show();*/
            }
        });
    }
    public void onBack(){
        new Thread(){
            public void run() {
                try{
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                 catch (Exception e) {
                     Log.e("Exception when onBack", e.toString());
                 }
            }
        }.start();
    }
}
