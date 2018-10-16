package com.itsdf07.fcommon;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.itsdf07.R;
import com.itsdf07.alog.ALog;
import com.itsdf07.dialog.FLoadingDialog;

import butterknife.BindString;

/**
 * @Description Activity界面的通用内容封装
 * 1、加载对话框:FLoadingDialog->ProgressDialog
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public class BaseActivity extends AppCompatActivity implements IBaseView {
    @BindString(R.string.ftip_net_error)
    String tipNetError;
    @BindString(R.string.ftip_loading)
    String tipLoading;

    /**
     * 加载对话框
     */
    FLoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALog.d("%s:执行了onCreate ...", this);
    }

    @Override
    protected void onDestroy() {
        ALog.d("%s:执行了onDestroy ...", this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ALog.d("%s:执行了onStart ...", this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ALog.d("%s:执行了onRestart ...", this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ALog.d("%s:执行了onResume ...", this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ALog.d("%s:执行了onPause ...", this);
    }

    @Override
    public void showLoading() {
        showLoading("");
    }

    @Override
    public void showLoading(String content) {
        if (null == loadingDialog) {
            if (TextUtils.isEmpty(content)) {
                content = tipLoading;
            }
            loadingDialog = new FLoadingDialog(this, content);//getString(R.string.tip_loading)
            loadingDialog.setCancelable(false);//返回键取消对话框
//            loadingDialog.setCanceledOnTouchOutside(true);//点击对话框外取消对话框
        }
        loadingDialog.show();
    }

    @Override
    public void showLoading(int resId) {
        String content;
        try {
            content = getString(resId);
            showLoading(content);
        } catch (Resources.NotFoundException e) {
            ALog.e("未找到资源:%s", resId);
        }

    }

    @Override
    public void dismissLoading() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        } else {
            ALog.e("异常:loadingDialog == null,");
        }
    }

    @Override
    public void showToastShort(String msg) {

    }

    @Override
    public void showToastShort(int resId) {

    }

    @Override
    public void showToastNetError() {

    }

    @Override
    public void showToastNetError(String errorMsg) {

    }

    @Override
    public void showToastNetError(int errorMsg) {

    }

    @Override
    public void showToastNetError(String errCode, String errorMsg) {

    }
}
