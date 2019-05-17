package com.itsdf07.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itsdf07.utils.R;

/**
 * @Description 自定义title标题
 * @Author itsdf07
 * @Time 2018/11/23
 */
public class FTitlebarView extends RelativeLayout {
    private View layoutLeft, layoutRight;
    private TextView tvLeft, tvTitle, tvRight;
    private ImageView ivLeft, ivRight;
    private onViewClick mClick;

    public FTitlebarView(Context context) {
        this(context, null);
    }

    public FTitlebarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FTitlebarView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.flayout_title, this);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FTitlebarView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (R.styleable.FTitlebarView_leftTextColor == attr) {
                tvLeft.setTextColor(array.getColor(attr, Color.BLACK));
            } else if (R.styleable.FTitlebarView_leftDrawble == attr) {
                ivLeft.setImageResource(array.getResourceId(attr, 0));
            } else if (R.styleable.FTitlebarView_leftText == attr) {
                tvLeft.setText(array.getString(attr));
            } else if (R.styleable.FTitlebarView_centerTextColor == attr) {
                tvTitle.setTextColor(array.getColor(attr, Color.BLACK));
            } else if (R.styleable.FTitlebarView_centerTitle == attr) {
                tvTitle.setText(array.getString(attr));
            } else if (R.styleable.FTitlebarView_rightDrawable == attr) {
                ivRight.setImageResource(array.getResourceId(attr, 0));
            } else if (R.styleable.FTitlebarView_rightText == attr) {
                tvRight.setText(array.getString(attr));
            } else if (R.styleable.FTitlebarView_rightTextColor == attr) {
                tvRight.setTextColor(array.getColor(attr, Color.BLACK));
            } else {

            }
        }
        array.recycle();
        layoutLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mClick) {
                    mClick.leftClick(view);
                }
            }
        });
        layoutRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mClick) {
                    mClick.rightClick(view);
                }
            }
        });

    }

    private void initView() {
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRight = (TextView) findViewById(R.id.tvRight);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        layoutLeft = findViewById(R.id.layoutLeft);
        layoutRight = findViewById(R.id.layoutRight);
    }

    public void setOnViewClick(onViewClick click) {
        this.mClick = click;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置左标题
     *
     * @param title
     */
    public void setLeftText(String title) {
        tvLeft.setText(title);
    }


    /**
     * 设置右标题
     *
     * @param title
     */
    public void setRightText(String title) {
        tvRight.setText(title);
    }


    /**
     * 设置标题大小
     *
     * @param size
     */
    public void setTitleSize(int size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置左标题大小
     *
     * @param size
     */
    public void setLeftTextSize(int size) {
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }


    /**
     * 设置右标题大小
     *
     * @param size
     */
    public void setRightTextSize(int size) {
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * 设置左图标
     *
     * @param res
     */
    public void setLeftDrawable(int res) {
        ivLeft.setImageResource(res);
    }

    /**
     * 设置右图标
     *
     * @param res
     */
    public void setRightDrawable(int res) {
        ivRight.setImageResource(res);
    }

    public interface onViewClick {
        void leftClick(View view);

        void rightClick(View view);
    }

}

//xml引用
//<com.itsdf07.widget.FTitlebarView
//        android:id="@+id/title"
//        android:layout_width="match_parent"
//        android:layout_height="48dp"
//        android:background="@color/navigationBarColor"
//        app:centerTextColor="#FFF"
//        app:centerTitle="自定义标题"
//        app:leftDrawble="@mipmap/ic_write_back"
//        app:leftText="返回"
//        app:leftTextColor="#FFF"
//        app:rightDrawable="@android:drawable/ic_btn_speak_now"
//        app:rightText="语音"
//        app:rightTextColor="#FFF" />

//代码设置
//    private void init() {
//        FTitlebarView titlebarView = (FTitlebarView) findViewById(R.id.title);
//        titlebarView.setTitleSize(20);
//        titlebarView.setTitle("标题栏");
//        titlebarView.setRightDrawable(0);//不需要icon时
//        titlebarView.setRightText("");//不需要文案时
//        titlebarView.setOnViewClick(new FTitlebarView.onViewClick() {
//            @Override
//            public void leftClick(View view) {
//                Toast.makeText(MainActivity.this, "左边", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void rightClick(View view) {
//                Toast.makeText(MainActivity.this, "右边", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }