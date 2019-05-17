package com.itsdf07.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.itsdf07.MainActivity;
import com.itsdf07.R;
import com.itsdf07.fcommon.BaseActivity;
import com.itsdf07.views.FSlide2UnlockView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 横向滑动解锁示例
 * @Author itsdf07
 * @Time 2019/5/17/017
 */

public class Slide2UnlockActivity extends BaseActivity {

    @BindView(R.id.id_slide2unlock)
    FSlide2UnlockView vSlide2unlock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide2unlock);
        ButterKnife.bind(this);
        vSlide2unlock.setUnlockListener(new FSlide2UnlockView.UnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(Slide2UnlockActivity.this, "可以往下做解锁后要执行的事情，如界面跳转", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.id_slide2unlock)
    public void onViewClicked() {
    }
}
