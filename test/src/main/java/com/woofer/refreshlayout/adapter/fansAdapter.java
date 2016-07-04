package com.woofer.refreshlayout.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.woofer.refreshlayout.fansinfoModel;

import java.net.URL;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import woofer.com.test.R;


public class fansAdapter extends BGAAdapterViewAdapter<fansinfoModel> {

    public fansAdapter(Context context) {
        super(context, R.layout.fansitem);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.OT_fans_UserName);
        viewHolderHelper.setItemChildLongClickListener(R.id.OT_fans_UserName);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, fansinfoModel model) {
        viewHolderHelper.setText(R.id.OT_fans_UserName, model.username).setText(R.id.OT_fans_sign, model.sign);
        ;
    }

}