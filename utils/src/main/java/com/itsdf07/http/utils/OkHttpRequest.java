package com.itsdf07.http.utils;

import android.text.TextUtils;

import com.itsdf07.alog.ALog;
import com.itsdf07.http.delegate.HttpCallbackImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description ：
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class OkHttpRequest {

    public static final String TAG_HTTP = "http";

    static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//JSON数据格式
    public static final MediaType MEDIA_TYPE_URL_ENCODED = MediaType.parse("application/x-www-form-urlencoded");

    private static Platform mPlatform;

    /**
     * @Description 请求方式
     */
    enum HttpMethodType {
        GET,
        POST,
    }

    private static OkHttpClient getOkHttpClient() {
        if (null == mPlatform) {
            mPlatform = Platform.get();
        }
        return HttpUtils.getInstance().getOkHttpClient();
    }


    /**
     * 数据请求成功
     * 将http请求成功结果转至主线程
     *
     * @param result
     * @param callback
     */
    public static void sendSuccessResultCallback(final String result, final HttpCallbackImpl callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                ALog.dTag(TAG_HTTP, "result:", result);
                callback.onSuccess(result);
                callback.onFinish();
            }
        });
    }

    /**
     * 数据请求失败
     * 将http请求失败结果转至主线程
     *
     * @param code
     * @param message
     * @param callback
     */
    public static void sendFailResultCallback(final String code, final String message, final HttpCallbackImpl callback) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                ALog.dTag(TAG_HTTP, "code:%s,message:%s", code, message);
                callback.onFailure(code, NetErrCode.translateNetCode(code));
                callback.onFinish();
            }
        });
    }

    /**
     * 文件下载进度
     * 线程转为主线程
     *
     * @param currentTotalLen
     * @param totalLen
     * @param callback
     * @param isFinish        是否下载完成
     */
    public static void sendProgressResultCallback(final long currentTotalLen, final long totalLen, final HttpCallbackImpl callback, final boolean isFinish) {
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                if (isFinish) {
                    callback.onFinish();
                } else {
                    callback.onProgress(currentTotalLen, totalLen);
                }
            }
        });
    }

    /**--------------------    异步数据请求    --------------------**/

    /**
     * @param methodType 请求方式
     * @param url        请求地址
     * @param params     请求参数
     * @param json       json数据格式
     * @Description Request对象
     */
    public static Request builderRequest(HttpMethodType methodType, String url, Map<String, String> params, String json) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            if (json != null) {
                RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
                builder.post(body);
            } else {
                RequestBody body = builderFormData(params);
                builder.post(body);
            }
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }

        return builder.build();
    }

    public static Request builderRequest(HttpMethodType methodType, String url, Map<String, String> params, String json, MediaType mediaType) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            if (json != null) {
                RequestBody body = RequestBody.create(mediaType, json);
                builder.post(body);
            } else {
                RequestBody body = builderFormData(params);
                builder.post(body);
            }
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }

        return builder.build();
    }

    /**
     * @param params 请求参数
     * @Description RequestBody对象
     */
    private static RequestBody builderFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }

        return builder.build();
    }

    /**
     * @param request  Request对象
     * @param callback 请求回调
     * @Description 异步请求
     */
    public static void doEnqueue(final Request request, final HttpCallbackImpl callback) {
        if (null != callback) {
            callback.onStart();
        }
        getOkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(NetErrCode.ERR_FAILURE, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null == response) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_NULL, "The Response obj is null", callback);
                    return;
                }
                if (null == response.body()) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_NULL, "The Response body is null", callback);
                    return;
                }
                String result = response.body().string();
                if (TextUtils.isEmpty(result)) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_PARSE, "The Response content null", callback);
                    return;
                }
                try {
                    if (response.isSuccessful()) {
                        sendSuccessResultCallback(result, callback);
                    } else {
                        sendFailResultCallback(response.code() + "", response.message(), callback);
                    }
                } catch (Exception e) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_PARSE, e.getMessage(), callback);
                }
            }
        });
    }

    /**--------------------    异步文件下载    --------------------**/

    /**
     * @param request      Request对象
     * @param destFileDir  目标文件存储的文件目录
     * @param destFileName 目标文件存储的文件名
     * @param callback     请求回调
     * @Description 异步下载请求
     */
    public static Call doDownloadEnqueue(final Request request, final String destFileDir, final String destFileName, final HttpCallbackImpl callback) {
        if (null != callback) {
            callback.onStart();
        }
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(NetErrCode.ERR_FAILURE, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (null == response) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_NULL, "The Response obj is null", callback);
                    return;
                }
                if (null == response.body()) {
                    sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_NULL, "The Response body is null", callback);
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    byte[] buffer = new byte[2048];
                    int len;
                    long currentTotalLen = 0L;
                    long totalLen = response.body().contentLength();

                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, destFileName);
                    if (file.exists()) {
                        //如果文件存在则删除
                        file.delete();
                    }
                    fileOutputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                        currentTotalLen += len;
                        sendProgressResultCallback(currentTotalLen, totalLen, callback, false);
                    }
                    fileOutputStream.flush();
                    sendProgressResultCallback(currentTotalLen, totalLen, callback, true);
                } catch (IOException e) {
                    if (e instanceof SocketException) {
                        sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_CANCLE, e.getMessage(), callback);
                    } else {
                        sendFailResultCallback(NetErrCode.ERR_FAILURE_RESPONSE_PARSE, e.getMessage(), callback);
                    }
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        return call;
    }
}
