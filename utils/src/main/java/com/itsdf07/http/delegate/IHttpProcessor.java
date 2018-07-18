package com.itsdf07.http.delegate;

import java.util.Map;

/**
 * @Description ：http代理接口类
 * 网络访问方式：post、get、del、update、put
 * @Author itsdf07
 * @Time 2018/07/18
 */
public interface IHttpProcessor {
    /**
     * @param url
     * @param params   参数列表
     * @param callback 返回结果回调
     */
    void post(String url, Map<String, Object> params, IHttpCallback callback);

    /**
     * @param url
     * @param params   参数列表
     * @param callback 返回结果回调
     */
    void get(String url, Map<String, Object> params, IHttpCallback callback);
}
