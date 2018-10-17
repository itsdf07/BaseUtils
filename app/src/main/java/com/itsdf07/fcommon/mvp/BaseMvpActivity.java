package com.itsdf07.fcommon.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.itsdf07.fcommon.BaseActivity;
import com.itsdf07.utils.FUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description mvp基类封装
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public abstract class BaseMvpActivity<P extends BasePresenter, M extends IBaseModel> extends BaseActivity {
    public P presenter;
    public M model;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        initDataBeforeView();
        setContentView(getLayoutId());
        //Activity 中使用，一定要在setContentView()之后再写 ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        presenter = FUtils.getT(this, 0);
        model = FUtils.getT(this, 1);
        presenter.mActivity = this;
        this.initPresenter();
        this.initView();
        this.initDataAfterView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != presenter) {
            presenter.onPStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != presenter) {
            presenter.onPResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != presenter) {
            presenter.onPPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
            unbinder = null;
        }
        if (null != presenter) {
            presenter.onPDestroy();
            presenter = null;
        }

        super.onDestroy();
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetContentView() {

    }

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 在setContentView之前需要初始化的数据
     */
    public abstract void initDataBeforeView();

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 在initView之后需要处理的数据
     */
    public abstract void initDataAfterView();

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getLayoutId();
}
