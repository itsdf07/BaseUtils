package com.itsdf07.alog;

import com.itsdf07.utils.FileUtils;

/**
 * @Description ：It is used to determine log settings such as method count, thread info visibility
 * @Author itsdf07
 * @Time 2018/6/4
 */

public final class ALogSettings {
    /**
     * Log的TAG
     */
    private String TAG = "itsdf07";

    /**
     * 是否保存Log信息
     */
    private boolean isLog2Local = true;

    /**
     * Log本地文件存储路径
     * Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
     */
    private String logFilePath = FileUtils.INNERSDPATH;

    /**
     * 是否打印线程名称
     */
    private boolean isShowThreadInfo = false;

    /**
     * Determines to how logs will be printed
     */
    private ALogLevel logLevel = ALogLevel.FULL;

    /**
     * 设置Log信息中打印的函数栈中的函数计数
     */
    private int methodCount = 2;
    /**
     * 设置Log信息中打印函数栈的起始位置，即用于控制需要打印哪几个(methodCount)函数
     */
    private int methodOffset = 0;

    private ALogAdapterImpl aLogAdapter;

    public String getTag() {
        return TAG;
    }

    /**
     * 设置Log打印时的Tag
     *
     * @param tag
     * @return
     */
    public ALogSettings setTag(String tag) {
        if (null == tag) {
            throw new NullPointerException("tag may not be null");
        }
        if (tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        }
        this.TAG = tag;
        return this;
    }

    /**
     * 是否本地保存Log信息
     *
     * @return
     */
    public boolean isLog2Local() {
        return isLog2Local;
    }

    /**
     * 设置是否本地保存Log信息
     *
     * @param isLog2Local
     */
    public ALogSettings setLog2Local(boolean isLog2Local) {
        this.isLog2Local = isLog2Local;
        return this;
    }


    public String getLogFilePath() {
        return logFilePath;
    }

    /**
     * 设置Log存储路径
     *
     * @param logFilePath
     */
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }


    /**
     * 是否显示线程名称：
     *
     * @return false：不显示线程信息，即只打印内容
     */
    public boolean isShowThreadInfo() {
        return isShowThreadInfo;
    }

    /**
     * 不打印线程信息
     *
     * @return
     */
    public ALogSettings hideThreadInfo() {
        isShowThreadInfo = false;
        return this;
    }

    /**
     * 设置是否显示线程信息
     *
     * @param isShowThreadInfo
     * @return
     */
    public ALogSettings setShowThreadInfo(boolean isShowThreadInfo) {
        this.isShowThreadInfo = isShowThreadInfo;
        return this;
    }

    public ALogLevel getLogLevel() {
        return logLevel;
    }

    /**
     * 设置是否打印Log信息
     *
     * @param logLevel
     * @return
     * @see ALogLevel
     */
    public ALogSettings setLogLevel(ALogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    /**
     * 设置Log信息中打印的函数栈中的函数计数
     *
     * @param methodCount
     */
    public void setMethodCount(int methodCount) {
        this.methodCount = methodCount;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    /**
     * 设置Log信息中打印函数栈的起始位置，即用于控制需要打印哪几个(methodCount)函数
     *
     * @param methodOffset
     */
    public void setMethodOffset(int methodOffset) {
        this.methodOffset = methodOffset;
    }

    public ALogSettings setLogAdapter(ALogAdapterImpl adapter) {
        this.aLogAdapter = adapter;
        return this;
    }

    public ALogAdapterImpl getLogAdapter() {
        if (aLogAdapter == null) {
            aLogAdapter = new ALogAdapterImpl();
        }
        return aLogAdapter;
    }
}
