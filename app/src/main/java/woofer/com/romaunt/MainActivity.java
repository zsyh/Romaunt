package woofer.com.romaunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;




//implements 自View.OnClickListener 实现对单击事件的统一响应
public class MainActivity extends Activity implements View.OnClickListener{

    private CheckBox checkBox1;
    private EditText editText2;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox1=(CheckBox)findViewById(R.id.checkbox1);
        editText2=(EditText)findViewById(R.id.eT2);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editText2.postInvalidate();
                //切换后将光标置于末尾

                CharSequence charSequence = editText2.getText();
                if(charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
                findViewById(R.id.button).setOnClickListener(this);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                Intent intent=new Intent(this, LoginactivityActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
