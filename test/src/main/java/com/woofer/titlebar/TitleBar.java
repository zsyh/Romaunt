package com.woofer.titlebar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;


public class TitleBar extends RelativeLayout implements OnClickListener,
        OnTouchListener {

    public TitleBarButton leftButton;
    public TitleBarButton rightButton;
    private TextView titleTextView;

    private LayoutParams leftParam;
    private LayoutParams rightParam;
    private LayoutParams titleParam;

    private float leftWidth;
    private float rightWidth;
    private String titleText;
    private int titleColor;
    private float titleSize;
    private int leftBackground;
    private int rightBackground;

    private String leftText;
    protected String rightText;

    private int leftTextColor;
    private int rightTextColor;
    private int leftTextColorPressed;
    private int rightTextColorPressed;

    private int btPadding;

    private int leftBackPressed;
    private int rightBackPressed;

    private final static int DEFAULT_WIDTH = 48;
    private final static int DEFAULT_COLOR = Color.parseColor("#FFFFFF");
    private final static int DEFAULT_TITLE_SIZE = 18;

    private int btMarginBottom;
    private int btMarginLeft;
    private int btMarginRight;
    private int btMarginTop;

    private Context context;

    private OnTitleClickListener listener;

    public interface OnTitleClickListener {

        public void onLeftClick();

        public void onRightClick();

        public void onTitleClick();
    }

    public TitleBar(Context context) {
        this(context, null);

    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    @TargetApi(VERSION_CODES.HONEYCOMB)
    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;

        getAttrs(attrs);
        initView();

    }

    public void setOnTitleClickListeren(OnTitleClickListener listener) {

        this.listener = listener;
    }

    public void setTitle(CharSequence charSequence) {

        titleTextView.setText(charSequence);
    }

    public void setTitleColor(int color) {
        titleColor = color;
        titleTextView.setTextColor(titleColor);
    }

    public void setLeftVisibility(int visible) {
        if (visible == View.INVISIBLE
                && leftButton.getVisibility() == View.VISIBLE) {
            AnimationSet set = new AnimationSet(true);
            AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
            ScaleAnimation scale = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                    1, 0.5f, 1, 0.5f);
            anim.setDuration(200);
            scale.setDuration(200);
            set.setInterpolator(new DecelerateInterpolator());
            set.addAnimation(scale);
            set.addAnimation(anim);
            leftButton.setAnimation(set);
            set = null;
        }
        visible = visible == View.GONE ? View.INVISIBLE : visible;
        leftButton.setVisibility(visible);
    }

    public void setRightVisibility(int visible) {
        // 这是一个没什么用的动画
        if (visible == View.INVISIBLE
                && rightButton.getVisibility() == View.VISIBLE) {
            AnimationSet set = new AnimationSet(true);
            AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
            ScaleAnimation scale = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                    1, 0.5f, 1, 0.5f);
            anim.setDuration(200);
            scale.setDuration(200);
            set.setInterpolator(new DecelerateInterpolator());
            set.addAnimation(scale);
            set.addAnimation(anim);
            rightButton.setAnimation(set);
            set = null;
        }
        visible = visible == View.GONE ? View.INVISIBLE : visible;
        rightButton.setVisibility(visible);
    }

    /**
     *
     * <font color='#00a4e9'>设置左侧按钮按压效果</font>
     *
     * @param id
     *            参数为图片id
     */
    public void setLeftPressedImage(int id) {

        this.leftBackPressed = id;
    }

    public void setRightPressedImage(int id) {
        this.rightBackPressed = id;
    }

    public void setLeftImageResource(int resource) {
        this.leftBackground = resource;
        leftButton.setImageResource(leftBackground);
    }

    public void setRightImageResource(int resource) {
        this.rightBackground = resource;
        rightButton.setImageResource(rightBackground);
    }

    public void setLeftText(CharSequence text) {
        leftButton.setText(text);
    }

    public void setRightText(CharSequence text) {
        rightButton.setText(text);
    }

    public void setRightTextColor(int color) {
        this.rightTextColor = color;
        rightButton.setTextColor(color);
    }

    public void setLeftTextColor(int color) {
        this.leftTextColor = color;
        leftButton.setTextColor(color);
    }

    public void setTextColor(int color) {
        this.leftTextColor = color;
        this.rightTextColor = color;
        rightButton.setTextColor(color);
        leftButton.setTextColor(color);
    }

    public void setPressedTextColor(int color) {
        this.leftTextColorPressed = color;
        this.rightTextColorPressed = color;
    }

    /**
     * <font color='#444444'> <strong> 自带透明状态栏特效</strong> </font><br>
     * <br>
     *
     * @param activity
     */
    @TargetApi(VERSION_CODES.KITKAT)
    public void translucentStatus(Activity activity) {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusHeight = getStatusHeight(activity.getApplicationContext());
            if (statusHeight == -1) {
                return;
            }
            int preHeight = getLayoutParams().height;
            getLayoutParams().height = preHeight + statusHeight;
            setLayoutParams(getLayoutParams());
            setPadding(0, statusHeight, 0, 0);
            activity = null;
        } else {
            return;
        }
    }

    private int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private void getAttrs(AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TitleBar);

        leftWidth = ta.getDimension(R.styleable.TitleBar_leftwidth,
                getPx(DEFAULT_WIDTH));
        leftBackground = ta.getResourceId(R.styleable.TitleBar_leftbackground,
                0);
        leftTextColor = ta.getColor(R.styleable.TitleBar_leftTextColor,
                DEFAULT_COLOR);
        leftText = ta.getString(R.styleable.TitleBar_lefttext);

        rightWidth = ta.getDimension(R.styleable.TitleBar_rightwidth,
                getPx(DEFAULT_WIDTH));
        rightBackground = ta.getResourceId(
                R.styleable.TitleBar_rightbackground, 0);
        rightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor,
                DEFAULT_COLOR);
        rightText = ta.getString(R.styleable.TitleBar_righttext);

        titleText = ta.getString(R.styleable.TitleBar_titletext);
        titleColor = ta.getColor(R.styleable.TitleBar_titletcolor,
                DEFAULT_COLOR);
        titleSize = ta.getDimension(R.styleable.TitleBar_titlesize,
                sp2px(DEFAULT_TITLE_SIZE));
        btPadding = (int) ta.getDimension(R.styleable.TitleBar_bt_padding,
                DEFAULT_TITLE_SIZE);

        btMarginTop = (int) ta.getDimension(R.styleable.TitleBar_bt_margin_top,
                0.0F);
        btMarginBottom = (int) ta.getDimension(
                R.styleable.TitleBar_bt_margin_bottom, 0.0F);
        btMarginLeft = (int) ta.getDimension(
                R.styleable.TitleBar_bt_margin_left, 0.0F);
        btMarginRight = (int) ta.getDimension(
                R.styleable.TitleBar_bt_margin_right, 0.0F);

        // leftWidth = rightWidth = rightWidth > leftWidth ? rightWidth
        // : leftWidth;

        ta.recycle();
    }

    @SuppressLint("NewApi")
    @TargetApi(VERSION_CODES.JELLY_BEAN)
    private void initView() {
        leftButton = new TitleBarButton(context);
        rightButton = new TitleBarButton(context);
        titleTextView = new TextView(context);

        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        leftButton.setImageResource(leftBackground);
        leftButton.setOnClickListener(this);
        leftButton.setOnTouchListener(this);
        leftButton.setPadding(btPadding, btPadding, btPadding, btPadding);

        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setImageResource(rightBackground);
        rightButton.setOnClickListener(this);
        rightButton.setOnTouchListener(this);
        rightButton.setPadding(btPadding, btPadding, btPadding, btPadding);

        titleTextView.setText(titleText);
        titleTextView.setTextSize(px2sp(titleSize));
        titleTextView.setTextColor(titleColor);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setOnClickListener(this);
        titleTextView.setPadding(btPadding, 0, btPadding, 0);

        leftParam = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        if (leftText == null) {
            leftParam.width = (int) leftWidth;
        }
        leftParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        this.leftParam.leftMargin = this.btMarginLeft;
        this.leftParam.rightMargin = this.btMarginRight;
        this.leftParam.topMargin = this.btMarginTop;
        this.leftParam.bottomMargin = this.btMarginBottom;

        rightParam = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        if (rightText == null) {
            rightParam.width = (int) rightWidth;
        }
        rightParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.rightParam.leftMargin = this.btMarginLeft;
        this.rightParam.rightMargin = this.btMarginRight;
        this.rightParam.topMargin = this.btMarginTop;
        this.rightParam.bottomMargin = this.btMarginBottom;

        titleParam = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        titleParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        addView(leftButton, leftParam);
        addView(titleTextView, titleParam);
        addView(rightButton, rightParam);

    }

    private float getPx(float dp) {
        return (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    private int px2sp(float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            if (v == leftButton) {
                listener.onLeftClick();
            }
            if (v == rightButton) {
                listener.onRightClick();
            }
            if (v == titleTextView) {
                listener.onTitleClick();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v == leftButton) {
                if (leftBackPressed == 0) {
                    leftButton.setTextColor(leftTextColorPressed);
                    return false;
                } else {
                    leftButton.setImageResource(leftBackPressed);
                }
            } else if (v == rightButton) {
                if (rightBackPressed == 0) {
                    rightButton.setTextColor(rightTextColorPressed);
                    return false;
                } else {
                    rightButton.setImageResource(rightBackPressed);
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            leftButton.setTextColor(leftTextColor);
            rightButton.setTextColor(rightTextColor);
            leftButton.setImageResource(leftBackground);
            rightButton.setImageResource(rightBackground);
        }
        return false;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
