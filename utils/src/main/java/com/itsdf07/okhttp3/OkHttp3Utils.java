package com.itsdf07.okhttp3;

import android.text.TextUtils;

import com.itsdf07.alog.ALog;
import com.itsdf07.okhttp3.callback.HttpBaseCallback;
import com.itsdf07.okhttp3.callback.HttpProgressCallback;
import com.itsdf07.okhttp3.impl.OkHttp3CallbackImpl;
import com.itsdf07.utils.FFileUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Description ：Http请求工具类
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class OkHttp3Utils {
    private static OkHttp3Utils instance;
    private OkHttpClient okHttpClient;

    public static OkHttp3Utils getInstance() {
        if (null == instance) {
            instance = new OkHttp3Utils();
        }
        return instance;
    }

    private OkHttp3Utils() {
        init();
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //默认15秒连接超时
        builder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

//        File httpCacheDirectory = new File(FFileUtils.INNERSDPATH, "http_cache");
//        Cache cache = new Cache(httpCacheDirectory, 10240 * 1024 * 100); //100M
//        builder.cache(cache);

        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 检查请求URL
     */
    private static boolean checkUrl(String url) {
        HttpUrl parsed = HttpUrl.parse(url);
        return parsed != null && !TextUtils.isEmpty(url);
    }

    /**--------------------    异步数据请求    --------------------**/
    /**
     * @param url      请求地址
     * @param json     json数据格式
     * @param callback 请求回调
     * @Description POST提交JSON数据
     */
    public static void postAsyn(String url, String json, OkHttp3CallbackImpl callback) {
        postAsyn(url, json, callback, false);
    }

    /**
     * @param url      请求地址
     * @param json     json数据格式
     * @param callback 请求回调
     * @param isDecode 返回的数据是否需要解密
     * @Description POST提交JSON数据
     */
    public static void postAsyn(String url, String json, OkHttp3CallbackImpl callback, boolean isDecode) {
        if (TextUtils.isEmpty(json)) {
            ALog.eTag(OkHttp3Request.TAG_HTTP, "无效的请求数据");
            return;
        }
        Request request = OkHttp3Request.builderRequest(OkHttp3Request.HttpMethodType.POST, url, null, json);
        OkHttp3Request.doEnqueue(request, callback, isDecode);
    }

    /**
     * Post异步请求
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void doPostAsynData(String url, String json, HttpBaseCallback callback) {
        if (!checkUrl(url)) {
            if (null != callback) {
                callback.onFailure(NetCode.CODE_6002, "请求的URL异常:" + url);
            }
            return;
        }
        if (TextUtils.isEmpty(json)) {
            if (null != callback) {
                callback.onFailure(NetCode.CODE_6003, "请求的URL异常:" + url);

            }
            return;
        }
        Request request = OkHttp3Request.builderRequest(OkHttp3Request.HttpMethodType.POST, url, null, json);
        OkHttp3Request.doPostEnqueue(request, callback);
    }

    /**--------------------    文件上传    --------------------**/

    /**
     * @param url      请求地址
     * @param file     上传文件
     * @param callback 请求回调
     * @Description 单文件上传
     */
    public static void doPostAsynFile(String url, File file, Map<String, String> params, HttpProgressCallback callback) {
        if (!checkUrl(url)) {
            if (null != callback) {
                callback.onFailure(NetCode.CODE_6002, "请求的URL异常:" + url);
            }
            return;
        }
        if (!FFileUtils.isFileExists(file)) {
            if (null != callback) {
                callback.onFailure(NetCode.CODE_6004, "上传的文件不存在，请检查...");
            }
            return;
        }
        Request request = OkHttp3Request.builderFileMapRequest(url, file, params, callback);
        OkHttp3Request.doPostEnqueue(request, callback);
    }

    /**--------------------    文件下载    --------------------**/
    /**
     * @param url          请求地址
     * @param destFileDir  目标文件存储的文件夹路径，如：Environment.getExternalStorageDirectory().getAbsolutePath()
     * @param destFileName 目标文件存储的文件名，如：gson-2.7.jar
     * @param callback     请求回调
     * @Description 文件下载
     */
    public static Call downloadAsynFile(String url, String destFileDir, String destFileName, OkHttp3CallbackImpl callback) {
        Request request = OkHttp3Request.builderRequest(OkHttp3Request.HttpMethodType.POST, url, null, null);
        return OkHttp3Request.doDownloadEnqueue(request, destFileDir, destFileName, callback);
    }

}