package com.itsdf07.fcommon.example;

import com.itsdf07.alog.ALog;

/**
 * Created by itsdf07 on 2017/7/25.
 */

public class ExamplePresenterImpl extends ExampleContract.Presenter {
    @Override
    String textMethod() {
        String msg = "这是为了证明xxx";
        ALog.d(msg);
        return msg;
    }
}
