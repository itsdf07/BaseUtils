package com.itsdf07;

import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itsdf07.alog.ALog;
import com.itsdf07.entity.FAppInfo;
import com.itsdf07.okhttp3.OkHttp3Request;
import com.itsdf07.okhttp3.OkHttp3Utils;
import com.itsdf07.okhttp3.bean.OkBaseBean;
import com.itsdf07.okhttp3.impl.OkHttp3CallbackImpl;
import com.itsdf07.utils.FAppInfoUtils;
import com.itsdf07.utils.FSimUtils;

import java.util.ArrayList;


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
                String vc = FAppInfoUtils.getVersionName(MainActivity.this);
                String vn = FAppInfoUtils.getVersionCode(MainActivity.this) + "";
                String metaData = FAppInfoUtils.getAppMetaData(MainActivity.this, "TEST_CHANNEL");
                ALog.dTag(TAG, "当前vn:%s,vc:%s,metaData:%s", vn, vc, metaData);
                ArrayList<FAppInfo> appInfos = FAppInfoUtils.getInstallApps(MainActivity.this);
                FAppInfo appInfo = FAppInfoUtils.getInstallApp(MainActivity.this, "com.rchat.remote");
                ALog.dTag(TAG, "appInfos:%s,appInfo:%s", (null == appInfos ? "appInfos为null" : appInfos.size()), (null == appInfo ? "appInfo为null" : appInfo.toString()));

                int simState = FSimUtils.getSimState(MainActivity.this);
                ALog.dTag(TAG, "SimState:%s - %s", simState, FSimUtils.translateSimState(simState));

                ALog.dTag(TAG, "Sim卡号:%s", FSimUtils.getSimSerialNumber(MainActivity.this));
                ALog.dTag(TAG, "Sim供货商:%s(代号:%s)", FSimUtils.getSimOperatorName(MainActivity.this), FSimUtils.getSimOperator(MainActivity.this));
                ALog.dTag(TAG, "Sim运营商:%s(代号:%s)", FSimUtils.getNetworkOperatorName(MainActivity.this), FSimUtils.getNetworkOperator(MainActivity.this));
                onHttpDebug();
            }
        });

    }

    /**
     * http接口调试
     */
    private void onHttpDebug() {
        String url = "http://poc.rchat.com.cn:9181/RchatMan/connectionDebug.do";
        OkHttp3Utils.postAsyn(url, "onHttpDebug", new OkHttp3CallbackImpl<OkBaseBean>() {
            @Override
            public void onStart() {
                ALog.dTag(OkHttp3Request.TAG_HTTP, "开始访问");
            }

            @Override
            public void onSuccess(OkBaseBean baseBean) {
                ALog.dTag(OkHttp3Request.TAG_HTTP, "访问成功,code:%s,desc:%s", baseBean.getCode(), baseBean.getDesc());
            }

            @Override
            public void onFailed(OkBaseBean bean) {
                ALog.dTag(OkHttp3Request.TAG_HTTP, "访问成功,code:%s,desc:%s", bean.getCode(), bean.getDesc());

            }

            @Override
            public void onFinish() {
                ALog.dTag(OkHttp3Request.TAG_HTTP, "访问结束");
            }
        });
//
    }
}
