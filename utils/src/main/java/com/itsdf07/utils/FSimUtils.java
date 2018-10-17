package com.itsdf07.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import com.itsdf07.alog.ALog;

/**
 * @Description: Sim卡相关信息工具类，如
 * 1、设备的SIM卡状态
 * <p>
 * MCC，Mobile Country Code，移动国家代码（中国的为460）；
 * MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）； 
 * LAC，Location Area Code，位置区域码；
 * CID，Cell Identity，基站编号；
 * BSSS，Base station signal strength，基站信号强度。
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
     * 获取网络运营商代码,MCC + MNC,如：46000
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
     * 中国移动和中国联通获取LAC、CID的方式
     *
     * @param context
     * @return
     */
    public static GsmCellLocation getGsmCellLocation(Context context) {
        // 中国移动和中国联通获取LAC、CID的方式
        GsmCellLocation location = (GsmCellLocation) getTM(context).getCellLocation();
        return location;
    }

    /**
     * 中国电信获取LAC、CID的方式
     *
     * @param context
     * @return
     */
    public static CdmaCellLocation getCdmaCellLocation(Context context) {
        // 中国电信获取LAC、CID的方式
        CdmaCellLocation location = (CdmaCellLocation) getTM(context).getCellLocation();
        return location;
    }

    /**
     * 封装在PhoneStateListener类中
     * LISTEN_NONE：停止监听更新（一般onPause方法中把所有的监听关闭掉）；
     * <p>
     * LISTEN_SERVICE_STATE：监听网络服务状态的变化；
     * <p>
     * LISTEN_SIGNAL_STRENGTH：监听网络信号强度的变化（单个）；
     * <p>
     * LISTEN_SIGNAL_STRENGTHS：监听网络信号强度的变化（多个）；
     * <p>
     * LISTEN_MESSAGE_WAITING_INDICATOR：监听消息的变化指标；
     * <p>
     * LISTEN_CALL_FORWARDING_INDICATOR：监听呼叫转移的变化指标；
     * <p>
     * LISTEN_CELL_LOCATION：监听设备的位置变化。请注意,这将导致频繁的回调侦听器；
     * <p>
     * LISTEN_CALL_STATE：监听设备呼叫状态的变化
     *
     * @param context
     * @param listener
     * @param events
     */
    public static void listenPhoneState(Context context, PhoneStateListener listener, int events) {
        getTM(context).listen(listener, events);
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
