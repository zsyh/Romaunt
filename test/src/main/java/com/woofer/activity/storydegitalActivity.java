package com.woofer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import woofer.com.test.R;
import com.woofer.titlebar.TitleBar;

public class storydegitalActivity extends AppCompatActivity {
    private TitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storydegital);

        Initcompement();



    }
    private void  Initcompement(){
        titleBar =(TitleBar)findViewById(R.id.degital_act_titlebar);
        titleBar.setTitle("文章");
        titleBar.leftButton.setImageResource(R.drawable.icon_return_white);
        titleBar.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storydegitalActivity.this.finish();
            }
        });
    }
}
