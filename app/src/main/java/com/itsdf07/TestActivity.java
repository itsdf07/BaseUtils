package com.itsdf07;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itsdf07.alog.ALog;
import com.itsdf07.fcommon.BaseActivity;
import com.itsdf07.fcommon.mvp.BaseMvpActivity;

/**
 * @Description:
 * @Author itsdf07
 * @Time 2018/7/11 14:49
 */

public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALog.dTag("dfsu","111111111");
    }
}
