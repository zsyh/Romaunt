package com.woofer.viewpager;

/**
 * Created by YOURFATHERME on 2016/5/17.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class ChildViewPager extends ViewPager {

    public static ViewPager mPager;//此处我直接在调用的时候静态赋值过来 的
    private int abc = 1;
    private float mLastMotionX;
    String TAG="@";

    private float firstDownX;
    private float firstDownY;
    private boolean flag=false;
    private boolean isCanScroll = false;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }


    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        final float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPager.requestDisallowInterceptTouchEvent(true);
                abc=1;
                mLastMotionX=x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (abc == 1) {
                    if (x - mLastMotionX > 5 && getCurrentItem() == 0) {
                        abc = 0;
                        mPager.requestDisallowInterceptTouchEvent(false);
                    }


                    if (x - mLastMotionX < -5 && getCurrentItem()  == getAdapter().getCount()-1) {
                        abc = 0;
                        mPager.requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mPager.requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item);
    }

}