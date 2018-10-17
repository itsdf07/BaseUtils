package com.itsdf07.sim;

import android.content.Context;

import com.itsdf07.MainActivity;
import com.itsdf07.alog.ALog;
import com.itsdf07.utils.FSimUtils;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */
public class SimModelImpl implements SimContract.Model {
    @Override
    public String readSiminfo(Context context) {
        String result = "";
        int simState = FSimUtils.getSimState(context);
        result += "状态:" + FSimUtils.translateSimState(simState) + "(" + simState + ")";

        result += "\nSim卡号:" + FSimUtils.getSimSerialNumber(context);
        result += "\nSim供货商(代号:" + FSimUtils.getSimOperator(context) + "):" + FSimUtils.getSimOperatorName(context);
        result += "\nSim运营商(代号:" + FSimUtils.getNetworkOperator(context) + "):" + FSimUtils.getNetworkOperatorName(context);

        return result;
    }
}
