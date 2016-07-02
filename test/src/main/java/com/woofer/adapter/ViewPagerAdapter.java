package com.woofer.adapter;

/**
 * Created by YOURFATHERME on 2016/5/17.
 */
import java.io.Serializable;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter implements Serializable {
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    // 返回当前分页数。
    public int getCount() {
        return views.size();
    }

    @Override
    //该方法判断是否由该对象生成界面。
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    //这个方法从viewPager中移动当前的view
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    // 这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }
}