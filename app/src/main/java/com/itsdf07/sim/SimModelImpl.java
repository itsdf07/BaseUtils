package com.itsdf07.sim;

import android.content.Context;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.itsdf07.utils.FSimUtils;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */
public class SimModelImpl implements SimContract.Model {
    @Override
    public String readSiminfo(Context context) {
        StringBuffer result = new StringBuffer();
        int simState = FSimUtils.getSimState(context);
        result.append("状态:" + FSimUtils.translateSimState(simState) + "(" + simState + ")");
        result.append("\nSim卡号:" + FSimUtils.getSimSerialNumber(context));
        result.append("\nSim供货商(代号:" + FSimUtils.getSimOperator(context) + "):" + FSimUtils.getSimOperatorName(context));
        String mcc_mnc = FSimUtils.getNetworkOperator(context);
        result.append("\nSim运营商(代号:" + mcc_mnc + "):" + FSimUtils.getNetworkOperatorName(context));
        if ("2".equals(mcc_mnc.substring(mcc_mnc.length() - 1 - 1))) {//电信
            CdmaCellLocation cdmaCellLocation = FSimUtils.getCdmaCellLocation(context);
            result.append("\n基站编号(CID):" + cdmaCellLocation.getSystemId());
            result.append("\n位置区域码(LAC):" + cdmaCellLocation.getNetworkId());
        } else {//移动、联通
            GsmCellLocation gsmCellLocation = FSimUtils.getGsmCellLocation(context);
            result.append("\n基站编号(CID):" + gsmCellLocation.getCid());
            result.append("\n位置区域码(LAC):" + gsmCellLocation.getLac());
        }

        return result.toString();
    }
}
