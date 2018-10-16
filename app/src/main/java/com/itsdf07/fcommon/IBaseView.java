package com.itsdf07.fcommon;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/10/16/016
 */

public interface IBaseView {

    /**
     * 显示加载对话框
     */
    void showLoading();

    /**
     * 显示加载对话框
     *
     * @param content
     */
    void showLoading(String content);

    /**
     * 显示加载对话框
     *
     * @param resId
     */
    void showLoading(int resId);

    /**
     * 取消加载对话框
     */
    void dismissLoading();

    /**
     * 短暂显示Toast提示
     *
     * @param msg
     */
    void showToastShort(String msg);

    /**
     * 短暂显示Toast提示
     *
     * @param resId
     */
    void showToastShort(int resId);

    /**
     * 显示网络异常toast
     **/
    void showToastNetError();

    /**
     * 显示网络异常toast
     *
     * @param errorMsg
     */
    void showToastNetError(String errorMsg);

    /**
     * 显示网络异常toast
     *
     * @param errorMsg
     */
    void showToastNetError(int errorMsg);

    /**
     * 显示网络异常toast
     *
     * @param errCode
     * @param errorMsg
     */
    void showToastNetError(String errCode, String errorMsg);


}
