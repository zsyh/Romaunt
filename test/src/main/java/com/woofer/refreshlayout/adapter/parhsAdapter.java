package com.woofer.refreshlayout.adapter;


import android.content.Context;

import com.woofer.refreshlayout.model.ParhsModel;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import woofer.com.test.R;


public class parhsAdapter extends BGAAdapterViewAdapter<ParhsModel> {

    public parhsAdapter(Context context) {
        super(context, R.layout.parhsitem);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.OT_parhs_title);
        viewHolderHelper.setItemChildLongClickListener(R.id.OT_parhs_title);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ParhsModel model) {
        viewHolderHelper.setText(R.id.OT_parhs_title, model.title).setText(R.id.OT_parhs_lable, model.flags)
                .setText(R.id.OT_parhs_thumbNUM, Integer.toString(model.thumbNUM)).setText(R.id.OT_parhs_time, model.time).setText(R.id.OT_parhs_content,model.content);
    }
}