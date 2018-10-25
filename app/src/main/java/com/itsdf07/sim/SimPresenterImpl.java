package com.itsdf07.sim;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.itsdf07.alog.ALog;
import com.itsdf07.utils.FSimUtils;

import java.util.List;


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

    @Override
    public void onPResume() {
        super.onPResume();
        FSimUtils.listenPhoneState(mActivity, new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                        | PhoneStateListener.LISTEN_CELL_LOCATION
                        | PhoneStateListener.LISTEN_SERVICE_STATE);
//        FSimUtils.listenPhoneState(mActivity, new MyPhoneStateListener(),
//                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_LOCATION);
    }

    @Override
    public void onPPause() {
        super.onPPause();
        FSimUtils.listenPhoneState(mActivity, new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_NONE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {

        /*
         * Get the Signal strength from the provider, each tiome there is an
         * update 从得到的信号强度,每个tiome供应商有更新
         */
        //这个方法只有在信号强度改变时才调用，或者程序刚刚启动时调用，如果想看到信号强度提示，
        // 那就等信号改变或者重启程序
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
//            Method method1 = null;
//
//            try {
//                method1 = signalStrength.getClass().getMethod("getAsuLevel");
//                int dbm = (Integer) method1.invoke(signalStrength);
//                ALog.dTag("Signal", "dbm:%s", dbm);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }


            StringBuffer sb = new StringBuffer();
            List<NeighboringCellInfo> list = FSimUtils.getNeighboringCellInfos(mActivity);
            for (NeighboringCellInfo info : list) {
                sb.append("NetWorkType:" + info.getNetworkType() + "\n").append("Cid:" + info.getCid() + "\n").append("Lac:" + info.getLac() + "\n").append("Psc" + info.getPsc() + "\n").append(info.getRssi() + "\n");
            }
            sb.append(signalStrength.toString() + "\nLevel:" + signalStrength.getLevel());
            ALog.dTag("Signal", "signalStrength:%s", sb.toString());
            mView.displaySignalStrength(sb.toString());

//            mView.displaySignalStrength("GsmSinnalStrength:" + signalStrength.getGsmSignalStrength() + "\n"
//                    + "GsmBitErrorRate:" + signalStrength.getGsmBitErrorRate() + "\n"
//                    + "CdmaDbm:" + signalStrength.getCdmaDbm() + "\n"
//                    + "CdmaEcio:" + signalStrength.getCdmaEcio() + "\n"
//                    + "EvdoDbm:" + signalStrength.getEvdoDbm() + "\n"
//                    + "EvdoEcio:" + signalStrength.getEvdoEcio() + "\n"
//                    + "EvdoSnr:" + signalStrength.getEvdoSnr() + "\n"
//                    + "Level:" + signalStrength.getLevel());
            /**
             * 信号强度
             */
//            gsmSignalStrength = signalStrength.getGsmSignalStrength();

        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
            ALog.dTag("Signal", "location:%s", location);
            mView.displayBaseLocation(location);
        }
    }
}
