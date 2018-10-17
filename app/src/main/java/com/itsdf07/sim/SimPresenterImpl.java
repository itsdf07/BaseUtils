package com.itsdf07.sim;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public class SimPresenterImpl extends SimContract.Presenter {

    @Override
    void readSimInfo() {
        String simInfo = mModel.readSiminfo(mActivity);
        mView.displaySimInfo(simInfo);
    }
}
