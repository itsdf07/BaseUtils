package com.itsdf07.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.itsdf07.alog.ALog;

/**
 * @Description: 通过广播的方式重启当前进程，其中Pid是会变的
 * @Author itsdf07
 * @Time 2018/7/11 11:21
 */

public class RestartAppReceiver extends BroadcastReceiver {
    private static final String TAG = "RestartAppReceiver";
    public static final String BROADCASTRECEIVER_ACTION = "com.itsdf07.action.restart.app";

    @Override
    public void onReceive(Context context, Intent intent) {
        ALog.dTag(TAG, "action:%s", intent.getAction());
        if (intent.getAction().equals(BROADCASTRECEIVER_ACTION)) {
            ALog.dTag(TAG, "myPid:%s", Process.myPid());
            Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            Process.killProcess(Process.myPid());
        }
    }
}

//使用方式
//sendBroadcast(new Intent(RestartAppReceiver.BROADCASTRECEIVER_ACTION));