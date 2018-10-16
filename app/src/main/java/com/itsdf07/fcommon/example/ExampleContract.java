package com.itsdf07.fcommon.example;

import com.itsdf07.fcommon.IBaseView;
import com.itsdf07.fcommon.mvp.BasePresenter;
import com.itsdf07.fcommon.mvp.IBaseModel;

/**
 * MVP中Contract类定义案例
 * Created by itsdf07 on 2017/7/25.
 */

public interface ExampleContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract String textMethod();
    }

    interface Model extends IBaseModel {
    }
}
