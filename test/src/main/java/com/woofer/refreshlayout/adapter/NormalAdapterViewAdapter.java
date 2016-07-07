package com.woofer.refreshlayout.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import retrofit2.http.Url;
import woofer.com.test.R;
import com.woofer.refreshlayout.model.RefreshModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NormalAdapterViewAdapter extends BGAAdapterViewAdapter<RefreshModel> {

    public NormalAdapterViewAdapter(Context context) {
        super(context, R.layout.item);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.activity_one_itemTV4);
        viewHolderHelper.setItemChildLongClickListener(R.id.activity_one_itemTV4);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, RefreshModel model) {

        viewHolderHelper.setText(R.id.activity_one_itemTV1, model.title).setText(R.id.itemTV5, model.detail)
                .setText(R.id.activity_one_AuthorID, model.authorname).setText(R.id.activity_one_itemTV4, model.sign)
        .setText(R.id.itemIV1,model.lable).setText(R.id.activity_one_itemTV2,model.time);
    }

}
