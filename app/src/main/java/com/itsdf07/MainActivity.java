package com.itsdf07;

import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itsdf07.alog.ALog;
import com.itsdf07.debug.TcpDebugActivity;
import com.itsdf07.entity.AppInfo;
import com.itsdf07.receiver.RestartAppReceiver;
import com.itsdf07.utils.AppInfoUtils;
import com.itsdf07.utils.SimUtils;

import junit.framework.Test;

import java.util.ArrayList;

import com.itsdf07.alog.ALog;
import com.itsdf07.http.bean.BaseBean;
import com.itsdf07.http.delegate.HttpCallbackImpl;
import com.itsdf07.http.utils.HttpUtils;
import com.itsdf07.http.utils.OkHttpRequest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ALog.dTag(TAG, "myPid:::%s", Process.myPid());
        findViewById(R.id.btnTestClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testStr = ExampleTest.testString("这是测试是否成功依赖utils库");
                ALog.dTag(TAG, "testStr:%s", testStr);
                String vc = AppInfoUtils.getVersionName(MainActivity.this);
                String vn = AppInfoUtils.getVersionCode(MainActivity.this) + "";
                String metaData = AppInfoUtils.getAppMetaData(MainActivity.this, "TEST_CHANNEL");
                ALog.dTag(TAG, "当前vn:%s,vc:%s,metaData:%s", vn, vc, metaData);
                ArrayList<AppInfo> appInfos = AppInfoUtils.getInstallApps(MainActivity.this);
                AppInfo appInfo = AppInfoUtils.getInstallApp(MainActivity.this, "com.rchat.remote");
                ALog.dTag(TAG, "appInfos:%s,appInfo:%s", (null == appInfos ? "appInfos为null" : appInfos.size()), (null == appInfo ? "appInfo为null" : appInfo.toString()));

                int simState = SimUtils.getSimState(MainActivity.this);
                ALog.dTag(TAG, "SimState:%s - %s", simState, SimUtils.translateSimState(simState));

                ALog.dTag(TAG, "Sim卡号:%s", SimUtils.getSimSerialNumber(MainActivity.this));
                ALog.dTag(TAG, "Sim供货商:%s(代号:%s)", SimUtils.getSimOperatorName(MainActivity.this), SimUtils.getSimOperator(MainActivity.this));
                ALog.dTag(TAG, "Sim运营商:%s(代号:%s)", SimUtils.getNetworkOperatorName(MainActivity.this), SimUtils.getNetworkOperator(MainActivity.this));
                onHttpDebug();
            }
        });
        findViewById(R.id.btnTcpDebug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TcpDebugActivity.class));
            }
        });

    }

    /**
     * http接口调试
     */
    private void onHttpDebug() {
        String url = "http://121.199.44.234:9181/RchatMan/connectionDebug.do";
        HttpUtils.postAsyn(url, "onHttpDebug", new HttpCallbackImpl<BaseBean>() {
            @Override
            public void onStart() {
                ALog.dTag(OkHttpRequest.TAG_HTTP, "开始访问");
            }

            @Override
            public void onSuccess(BaseBean baseBean) {
                if (null == baseBean) {
                    ALog.dTag(OkHttpRequest.TAG_HTTP, "访问成功，但是解析后的数据体为空");
                    return;
                }
                ALog.dTag(OkHttpRequest.TAG_HTTP, "访问成功,code:%s,desc:%s", baseBean.getCode(), baseBean.getDesc());
            }

            @Override
            public void onFailureResult(BaseBean bean) {
                if (null == bean) {
                    ALog.dTag(OkHttpRequest.TAG_HTTP, "访问失败，并且解析后的数据体为空");
                    return;
                }
                ALog.dTag(OkHttpRequest.TAG_HTTP, "访问成功,code:%s,desc:%s", bean.getCode(), bean.getDesc());

            }

            @Override
            public void onFinish() {
                ALog.dTag(OkHttpRequest.TAG_HTTP, "访问结束");
            }
        });
//
    }

    private void restartApp() {
        //关闭App并且重启
        ALog.dTag(TAG, "myPid:%s", Process.myPid());
        sendBroadcast(new Intent(RestartAppReceiver.BROADCASTRECEIVER_ACTION));
    }
}
