package com.itsdf07.sim;

import android.content.Context;
import android.location.Location;
import android.telephony.CellLocation;

import com.itsdf07.fcommon.IBaseView;
import com.itsdf07.fcommon.mvp.BasePresenter;
import com.itsdf07.fcommon.mvp.IBaseModel;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16
 */
public interface SimContract {
    interface View extends IBaseView {
        /**
         * 显示Sim卡信息
         *
         * @param info
         */
        void displaySimInfo(String info);

        void displayBaseLocation(CellLocation location);

        void displaySignalStrength(String signalStrength);

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        /**
         * 读取Sim卡信息
         */
        abstract void readSimInfo();
    }

    interface Model extends IBaseModel {
        /**
         * 读取Sim卡信息
         */
        String readSiminfo(Context context);
    }
}
