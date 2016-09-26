package com.woofer.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import woofer.com.test.R;


public class SelectPicPopupWindow extends PopupWindow {


                private View mMenuView;
                private LinearLayout wxcycle;
                private LinearLayout wechat;
                private LinearLayout qq;
                private LinearLayout sinaweibo;
                private LinearLayout url;
                private LinearLayout sms;
                private LinearLayout douban;
                private LinearLayout email;

                public SelectPicPopupWindow(Activity context,OnClickListener itemsOnClick) {
                super(context);
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mMenuView = inflater.inflate(R.layout.popupwindow, null);
                    wxcycle = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_wxcycle);
                    wechat = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_wechat);
                    qq = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_qq);
                    sinaweibo = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_sinaweibo);
                    url = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_url);
                    sms = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_sms);
                    douban = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_douban);
                    email = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_email);

                /*wxcycle = (LinearLayout) mMenuView.findViewById(R.id.popupwindow_wxcycle);
               //取消按钮
                wxcycle.setOnClickListener(new OnClickListener() {

                                public void onClick(View v) {
                                //销毁弹出框
                                dismiss();
                            }
                    });*/
                //设置按钮监听
                    wxcycle.setOnClickListener(itemsOnClick);
                    wechat.setOnClickListener(itemsOnClick);
                    qq.setOnClickListener(itemsOnClick);
                    sinaweibo.setOnClickListener(itemsOnClick);
                    url.setOnClickListener(itemsOnClick);
                    sms.setOnClickListener(itemsOnClick);
                    douban.setOnClickListener(itemsOnClick);
                    email.setOnClickListener(itemsOnClick);
                //设置SelectPicPopupWindow的View
                this.setContentView(mMenuView);
                //设置SelectPicPopupWindow弹出窗体的宽
                this.setWidth(LayoutParams.FILL_PARENT);
                //设置SelectPicPopupWindow弹出窗体的高
                this.setHeight(LayoutParams.WRAP_CONTENT);
                //设置SelectPicPopupWindow弹出窗体可点击
                this.setFocusable(true);
                //设置SelectPicPopupWindow弹出窗体动画效果

                this.setAnimationStyle(R.style.AnimBottom);
                //实例化一个ColorDrawable颜色为半透明
                ColorDrawable dw = new ColorDrawable(0xb0000000);
                //设置SelectPicPopupWindow弹出窗体的背景
                this.setBackgroundDrawable(dw);
                //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
                mMenuView.setOnTouchListener(new View.OnTouchListener() {

                                public boolean onTouch(View v, MotionEvent event) {

                                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                                int y=(int) event.getY();
                                if(event.getAction()== MotionEvent.ACTION_UP){
                                        if(y<height){
                                                dismiss();
                                            }
                                    }
                                return true;
                            }
                    });

            }

            }
