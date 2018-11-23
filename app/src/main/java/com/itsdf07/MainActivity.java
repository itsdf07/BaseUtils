package com.itsdf07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itsdf07.alog.ALog;
import com.itsdf07.dialog.FCustomDialog;
import com.itsdf07.entity.FAppInfo;
import com.itsdf07.fcommon.BaseActivity;
import com.itsdf07.okhttp3.NetCode;
import com.itsdf07.okhttp3.OkHttp3Utils;
import com.itsdf07.okhttp3.bean.OkBaseBean;
import com.itsdf07.okhttp3.callback.HttpBaseCallback;
import com.itsdf07.okhttp3.callback.HttpProgressCallback;
import com.itsdf07.okhttp3.impl.OkHttp3CallbackImpl;
import com.itsdf07.sim.SimMvpActivity;
import com.itsdf07.utils.FAppInfoUtils;
import com.itsdf07.utils.FFileUtils;
import com.itsdf07.utils.FMD5Utils;
import com.itsdf07.utils.FSimUtils;
import com.itsdf07.widget.FTitlebarView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.goto_siminfo)
    Button gotoSiminfo;
    private FCustomDialog.Builder builder;
    private FCustomDialog mDialog;

    private void init() {
        FTitlebarView titlebarView = (FTitlebarView) findViewById(R.id.title);
        titlebarView.setTitleSize(20);
        titlebarView.setTitle("标题栏");
        titlebarView.setRightDrawable(0);
        titlebarView.setRightText("");
        titlebarView.setOnViewClick(new FTitlebarView.onViewClick() {
            @Override
            public void leftClick(View view) {
                Toast.makeText(MainActivity.this, "左边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick(View view) {
                Toast.makeText(MainActivity.this, "右边", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
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
//                onHttpDebug();
                doPostAsny();
//                doPostAsnyFile();
            }
        });

        builder = new FCustomDialog.Builder(this);
//        ivImg = (ImageView) findViewById(R.id.iv_img);
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0); // 设置饱和度
//        ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
//        ivImg.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可


    }

    private void showSingleButtonDialog(String alertText, String btnText, View.OnClickListener onClickListener) {
        mDialog = builder.setMessage(alertText)
                .setSingleButton(btnText, 0, 0, onClickListener)
                .createSingleButtonDialog();
        mDialog.show();
    }

    private void showTwoButtonDialog(String alertText, String confirmText, String cancelText, View.OnClickListener conFirmListener, View.OnClickListener cancelListener) {
        mDialog = builder.setMessage(alertText)
                .setTextFontSizeButton(15f)
                .setTextFontSizeContent(40f)
                .setPositiveButton(confirmText, 0, 0, conFirmListener)
                .setNegativeButton(cancelText, 0, 0, cancelListener)
                .createDoubleButtonDialog();
        mDialog.show();
    }

    /**
     * http接口调试
     */
    private void onHttpDebug() {
        String url = "http://poc.rchat.com.cn:9181/RchatMan/connectionDebug.do";
        OkHttp3Utils.postAsyn(url, "onHttpDebug", new OkHttp3CallbackImpl<OkBaseBean>() {
            @Override
            public void onStart() {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "开始访问");
            }

            @Override
            public void onSuccess(OkBaseBean baseBean) {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "访问成功,code:%s,desc:%s", baseBean.getCode(), baseBean.getDesc());
            }

            @Override
            public void onFailed(OkBaseBean bean) {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "访问成功,code:%s,desc:%s", bean.getCode(), bean.getDesc());

            }

            @Override
            public void onFinish() {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "访问结束");
            }
        });
//
    }

    /**
     * http接口调试
     */
    private void doPostAsny() {
        String url = "http://poc.rchat.com.cn:9182/RchatMan/connectionDebug.do";
        OkHttp3Utils.doPostAsynData(url, "onHttpDebug", new HttpBaseCallback() {
            @Override
            public void onSuccess(String result) {
//                ALog.dTag(OkHttp3Utils.TAG_HTTP, "result:%s", result);
            }

            @Override
            public void onFailure(NetCode netCode, String msg) {
//                ALog.dTag(OkHttp3Utils.TAG_HTTP, "code:%s,desc:%s,info:%s", netCode.getCode(), netCode.getDesc(), netCode.getInfo());
//                ALog.dTag(OkHttp3Utils.TAG_HTTP, "msg:%s,", msg);
            }
        });
//
    }

    /**
     * http接口调试
     */
    private void doPostAsnyFile() {
        String filePath = FFileUtils.getInnerSDPath(this) + "/rchat/poc/timg.png";
        File file = new File(filePath);
        String url = "http://192.168.0.99:8080/ROPplatform/uploadPhoto";
        Map<String, String> params = new HashMap<>();
        params.put("imageName", "100210044.png");
        params.put("userName", "100210044");
        params.put("md5", FMD5Utils.getFileMD5(file));
        OkHttp3Utils.doPostAsynFile(url, file, params, new HttpProgressCallback() {
            @Override
            public void onProgress(long currentLen, long totalLen) {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "totalLen:%s,currentLen:%s", totalLen, currentLen);
            }

            @Override
            public void onSuccess(String result) {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "result:%s", result);
            }

            @Override
            public void onFailure(NetCode netCode, String msg) {
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "code:%s,desc:%s,info:%s", netCode.getCode(), netCode.getDesc(), netCode.getInfo());
                ALog.dTag(OkHttp3Utils.TAG_HTTP, "msg:%s,", msg);
            }
        });
//
    }

    @OnClick({R.id.goto_siminfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goto_siminfo:
                startActivity(new Intent(this, SimMvpActivity.class));
                break;
        }
    }
}
