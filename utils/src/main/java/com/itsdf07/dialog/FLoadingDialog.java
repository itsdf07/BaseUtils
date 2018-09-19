package com.itsdf07.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.itsdf07.utils.R;


/**
 * Created by itsdf07 on 2017/7/19.
 */

public class FLoadingDialog extends ProgressDialog {

    private String mLoadingTitle;
    private TextView mLoadingTv;

    public FLoadingDialog(Context context, String content) {
        super(context, R.style.LoadingDialog_Theme);
        this.mLoadingTitle = content;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        initView();
        initData();
    }

    private void initData() {
        mLoadingTv.setText(mLoadingTitle);
    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    private void initView() {
        setContentView(R.layout.view_floadingdialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
    }
}
//    FLoadingDialog dialog =new FLoadingDialog(MainActivity.this, "正在加载中...");
////打开
//dialog.show();
////隐藏
//        dialog.dismiss();