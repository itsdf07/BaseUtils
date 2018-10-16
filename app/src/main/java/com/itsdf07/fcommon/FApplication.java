package com.itsdf07.fcommon;

import android.app.Application;
import android.os.Process;

import com.itsdf07.alog.ALog;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public class FApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ALog.d("%s:执行了onCreate,pid:%s", this, Process.myPid());
    }
}
