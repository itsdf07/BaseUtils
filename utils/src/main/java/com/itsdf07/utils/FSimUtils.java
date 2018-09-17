package com.itsdf07.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.itsdf07.alog.ALog;

/**
 * @Description: Sim卡相关信息工具类，如
 * 1、设备的SIM卡状态
 * @Author itsdf07
 * @Time 2018/7/9 14:48
 */

public class FSimUtils {

    /**
     * 获取设备SIM卡状态信息<br>
     * TelephonyManager.SIM_STATE_UNKNOWN: 0 - 未知状态<br>
     * TelephonyManager.SIM_STATE_ABSENT: 1 - 没有SIM卡
     * TelephonyManager.SIM_STATE_PIN_REQUIRED: 2 - 需要PIN解锁
     * TelephonyManager.SIM_STATE_PUK_REQUIRED: 3 - 需要PUK解锁
     * TelephonyManager.SIM_STATE_NETWORK_LOCKED: 4 - 需要NetworkPIN解锁
     * TelephonyManager.SIM_STATE_READY: 5 - 良好
     *
     * @param context
     * @return
     */
    public static int getSimState(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return -1;
        }
        TelephonyManager tm = getTM(context);
        int simState = tm.getSimState();
        return simState;
    }

    /**
     * 获取Sim卡号
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return "";
        }
        TelephonyManager tm = getTM(context);
        //需要<uses-permission android:name="android.permission.READ_PHONE_STATE" />
        String imSerialNumber = tm.getSimSerialNumber();
        return TextUtils.isEmpty(imSerialNumber) ? "无法取得SIM卡号" : imSerialNumber.toString();
    }

    /**
     * 获取供货商代码
     *
     * @param context
     * @return
     */
    public static String getSimOperator(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return "";
        }
        TelephonyManager tm = getTM(context);
        String simOperator = tm.getSimOperator();
        return TextUtils.isEmpty(simOperator) ? "无法取得供货商代码" : simOperator;
    }

    /**
     * 获取供货商名
     *
     * @param context
     * @return
     */
    public static String getSimOperatorName(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return "";
        }
        TelephonyManager tm = getTM(context);
        String simOperatorName = tm.getSimOperatorName();
        return TextUtils.isEmpty(simOperatorName) ? "无法取得供货商名" : simOperatorName;
    }

    /**
     * 获取网络运营商代码
     *
     * @param context
     * @return
     */
    public static String getNetworkOperator(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return "";
        }
        TelephonyManager tm = getTM(context);
        String networkOperator = tm.getNetworkOperator();
        return TextUtils.isEmpty(networkOperator) ? "无法取得网络运营商代码" : networkOperator;
    }


    /**
     * 获取网络运营商名称
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        if (null == context) {
            ALog.e("参数错误");
            return "";
        }
        TelephonyManager tm = getTM(context);
        String networkOperatorName = tm.getNetworkOperatorName();
        return TextUtils.isEmpty(networkOperatorName) ? "无法取得网络运营商名称" : networkOperatorName;
    }

    /**
     * 取得相关系统服务
     *
     * @param context
     * @return
     */
    private static TelephonyManager getTM(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);//取得相关系统服务
    }

    /**
     * 获取设备SIM卡状态信息<br>
     * TelephonyManager.SIM_STATE_UNKNOWN: 0 - 未知状态<br>
     * TelephonyManager.SIM_STATE_ABSENT: 1 - 没有SIM卡
     * TelephonyManager.SIM_STATE_PIN_REQUIRED: 2 - 需要PIN解锁
     * TelephonyManager.SIM_STATE_PUK_REQUIRED: 3 - 需要PUK解锁
     * TelephonyManager.SIM_STATE_NETWORK_LOCKED: 4 - 需要NetworkPIN解锁
     * TelephonyManager.SIM_STATE_READY: 5 - 良好
     *
     * @param state
     * @return
     */
    public static String translateSimState(int state) {
        String result = "未知状态";
        switch (state) {
            case TelephonyManager.SIM_STATE_UNKNOWN: //未知状态：0
                result = "未知状态";
                break;
            case TelephonyManager.SIM_STATE_ABSENT:  //没有SIM卡：1
                result = "没有SIM卡";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:  //需要PIN解锁：2
                result = "需要PIN解";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:  //需要PUK解锁：3
                result = "需要PUK解锁";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:  //需要NetworkPIN解锁：4
                result = "需要NetworkPIN解锁";
                break;
            case TelephonyManager.SIM_STATE_READY://良好:5
                result = "良好";
                break;
            default:
                break;
        }
        return result;
    }
}
