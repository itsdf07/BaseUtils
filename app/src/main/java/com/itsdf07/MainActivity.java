package com.itsdf07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.itsdf07.adapter.DemoListAdapter;
import com.itsdf07.alog.ALog;
import com.itsdf07.bean.ItemDemoCaseBean;
import com.itsdf07.dialog.FCustomDialog;
import com.itsdf07.entity.FAppInfo;
import com.itsdf07.example.Slide2UnlockActivity;
import com.itsdf07.fcommon.BaseActivity;
import com.itsdf07.okhttp3.NetCode;
import com.itsdf07.okhttp3.OkHttp3Utils;
import com.itsdf07.okhttp3.bean.OkBaseBean;
import com.itsdf07.okhttp3.callback.HttpBaseCallback;
import com.itsdf07.okhttp3.callback.HttpProgressCallback;
import com.itsdf07.okhttp3.impl.OkHttp3CallbackImpl;
import com.itsdf07.utils.FAppInfoUtils;
import com.itsdf07.utils.FFileUtils;
import com.itsdf07.utils.FMD5Utils;
import com.itsdf07.utils.FSimUtils;
import com.itsdf07.views.FTitlebarView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    private Unbinder unbinder;
    @BindView(R.id.id_demos)
    ListView mDemoList;
    private FCustomDialog.Builder builder;
    private FCustomDialog mDialog;

    private DemoListAdapter mDemoListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @OnClick({R.id.id_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_test:
                onTest();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, Slide2UnlockActivity.class));
                break;
            case 1:

                break;
            case 2:

                break;
            default:
                break;
        }
    }

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


        builder = new FCustomDialog.Builder(this);
//        ivImg = (ImageView) findViewById(R.id.iv_img);
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0); // 设置饱和度
//        ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
//        ivImg.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可

        mDemoList.setOnItemClickListener(this);
        mDemoListAdapter = new DemoListAdapter(this);
        mDemoListAdapter.setData(initDemosData());
        mDemoList.setAdapter(mDemoListAdapter);

    }

    /**
     * 初始化Demo数据源
     *
     * @return
     */
    private List<ItemDemoCaseBean> initDemosData() {
        List<ItemDemoCaseBean> demos = new ArrayList<>();
        String[] titleDatas = {"自定义UI", "封装Tools"};
        String[] descDatas = {"高级UI，实现手动绘制过程", "日常Tools积累，方便快捷实现功能"};
        if (titleDatas.length != descDatas.length) {
            ItemDemoCaseBean bean = new ItemDemoCaseBean();
            bean.setIcon(R.mipmap.ic_launcher_round);
            bean.setTitle("数据异常");
            bean.setDesc("数据对照长度异常，请检查数据源！");
            demos.add(bean);
            return demos;
        }
        for (int i = 0; i < titleDatas.length; i++) {
            ItemDemoCaseBean bean = new ItemDemoCaseBean();
            bean.setIcon(R.mipmap.ic_launcher_round);
            bean.setTitle(titleDatas[i]);
            bean.setDesc(descDatas[i]);
            demos.add(bean);
        }
        return demos;
    }

    private void onTest() {
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


}
