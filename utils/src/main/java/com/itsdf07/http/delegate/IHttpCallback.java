package com.itsdf07.http.delegate;

/**
 * @Description ：Http请求过程中的回调
 * @Author itsdf07
 * @Time 2018/07/18
 */
public interface IHttpCallback {
    /**
     * @param result
     * @param isDecode
     * @Description http成功响应
     */
    void onSuccess(String result, boolean isDecode);

    /**
     * @param code
     * @param error
     * @Description http响应失败
     */
    void onFailure(String code, String error);
}
