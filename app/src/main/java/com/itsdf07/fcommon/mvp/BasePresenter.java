package com.itsdf07.fcommon.mvp;

import android.app.Activity;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */
public abstract class BasePresenter<V, M> {

    public M mModel;
    public V mView;
    public Activity mActivity;

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onPStart();
    }

    public void onPStart() {
    }

    public void onPResume() {
    }

    public void onPPause() {
    }

    public void onPDestroy() {
        this.mView = null;
        this.mModel = null;
        this.mActivity = null;
    }
}
