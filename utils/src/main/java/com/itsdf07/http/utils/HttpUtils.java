package com.itsdf07.http.utils;

import android.text.TextUtils;

import com.itsdf07.alog.ALog;
import com.itsdf07.http.delegate.HttpCallbackImpl;
import com.itsdf07.http.delegate.HttpConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Description ：Http请求工具类
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class HttpUtils {
    private static HttpUtils instance;
    private OkHttpClient okHttpClient;

    public static HttpUtils getInstance() {
        if (null == instance) {
            instance = new HttpUtils();
        }
        return instance;
    }

    private HttpUtils() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //默认15秒连接超时
        builder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

//        File httpCacheDirectory = new File(FileUtils.INNERSDPATH, "http_cache");
//        Cache cache = new Cache(httpCacheDirectory, 10240 * 1024 * 100); //100M
//        builder.cache(cache);

        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**--------------------    异步数据请求    --------------------**/
    /**
     * @param url      请求地址
     * @param json     json数据格式
     * @param callback 请求回调
     * @Description POST提交JSON数据
     */
    public static void postAsyn(String url, String json, HttpCallbackImpl callback) {
        postAsyn(url, json, callback, false);
    }

    /**
     * @param url      请求地址
     * @param json     json数据格式
     * @param callback 请求回调
     * @param isDecode 返回的数据是否需要解密
     * @Description POST提交JSON数据
     */
    public static void postAsyn(String url, String json, HttpCallbackImpl callback, boolean isDecode) {
        if (TextUtils.isEmpty(json)) {
            ALog.eTag(OkHttpRequest.TAG_HTTP, "Invalid request data.");
            return;
        }
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, null, json);
        OkHttpRequest.doEnqueue(request, callback, isDecode);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description POST请求
     */
    public static void postAsyn(String url, Map<String, String> params, HttpCallbackImpl callback) {
        postAsyn(url, params, callback, false);
    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @param isDecode 返回的数据是否需要解密
     * @Description POST请求
     */
    public static void postAsyn(String url, Map<String, String> params, HttpCallbackImpl callback, boolean isDecode) {
        if (params == null || params.isEmpty()) {
            ALog.eTag(OkHttpRequest.TAG_HTTP, "Invalid request data.");
            return;
        }
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, params, null);
        OkHttpRequest.doEnqueue(request, callback, isDecode);
    }

    /**--------------------    文件下载    --------------------**/
    /**
     * @param url          请求地址
     * @param destFileDir  目标文件存储的文件夹路径，如：Environment.getExternalStorageDirectory().getAbsolutePath()
     * @param destFileName 目标文件存储的文件名，如：gson-2.7.jar
     * @param callback     请求回调
     * @Description 文件下载
     */
    public static Call downloadAsynFile(String url, String destFileDir, String destFileName, HttpCallbackImpl callback) {
        Request request = OkHttpRequest.builderRequest(OkHttpRequest.HttpMethodType.POST, url, null, null);
        return OkHttpRequest.doDownloadEnqueue(request, destFileDir, destFileName, callback);
    }

}