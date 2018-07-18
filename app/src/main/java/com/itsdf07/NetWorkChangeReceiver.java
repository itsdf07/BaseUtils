package com.itsdf07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.itsdf07.alog.ALog;

/**
 * @Description:
 * @Author itsdf07
 * @Time 2018/7/11 11:10
 */

public class NetWorkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetWorkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ALog.dTag(TAG, "action:%s", intent.getAction());
        context.sendBroadcast(new Intent("restart.app"));
    }
}
