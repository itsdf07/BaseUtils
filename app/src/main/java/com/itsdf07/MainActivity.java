package com.itsdf07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itsdf07.alog.ALog;
import com.itsdf07.entity.AppInfo;
import com.itsdf07.utils.AppInfoUtils;
import com.itsdf07.utils.SimUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            }
        });

    }
}
