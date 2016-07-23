package com.woofer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import woofer.com.test.R;

public class ChooseActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private  Button btn1;
    private Button btn2;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        btn1=(Button)findViewById(R.id.acticity_choose_btn1);
        btn2=(Button)findViewById(R.id.acticity_choose_btn2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ChooseActivity.this.finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}
