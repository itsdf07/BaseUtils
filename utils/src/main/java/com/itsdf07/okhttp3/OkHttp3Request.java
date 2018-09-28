package com.itsdf07.okhttp3;

import android.text.TextUtils;

import com.itsdf07.alog.ALog;
import com.itsdf07.okhttp3.callback.HttpBaseCallback;
import com.itsdf07.okhttp3.callback.HttpProgressCallback;
import com.itsdf07.okhttp3.impl.OkHttp3CallbackImpl;
import com.itsdf07.okhttp3.progress.ProgressRequestBody;
import com.itsdf07.utils.FMD5Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @Description ：
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class OkHttp3Request {

    public static final String TAG_HTTP = "tag_okhttp3";

    static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//JSON数据格式
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");//二进制流数据

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
        return OkHttp3Utils.getInstance().getOkHttpClient();
    }


    /**
     * 数据请求成功
     * 将http请求成功结果转至主线程
     *
     * @param result
     * @param callback
     * @param isDecode
     */
    public static void sendSuccessResultCallback(final String result, final OkHttp3CallbackImpl callback, final boolean isDecode) {
        ALog.dTag(TAG_HTTP, "isDecode:%s,result:%s", isDecode, result);
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(result, isDecode);
                callback.onFinish();
            }
        });
    }

    /**
     * 数据请求失败
     * 将http请求失败结果转至主线程
     *
     * @param netCode
     * @param message
     * @param callback
     */
    public static void sendFailResultCallback(final NetCode netCode, final String message, final OkHttp3CallbackImpl callback) {
        ALog.eTag(TAG_HTTP, "NetCode:%s,msg:%s", netCode.getCode(), message);
        if (callback == null) {
            return;
        }
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(netCode.getCode(), netCode.getDesc());
                callback.onFinish();
            }
        });
    }


    /**
     * 数据请求成功
     * 将http请求成功结果转至主线程
     *
     * @param result
     * @param callback
     */
    public static void sendSuccessResultCallback(final String result, final HttpBaseCallback callback) {
        ALog.dTag(TAG_HTTP, "result:%s", result);

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                if (null != callback) {
                    callback.onSuccess(result);
                }
            }
        });
    }

    /**
     * 数据请求失败
     * 将http请求失败结果转至主线程
     *
     * @param netCode
     * @param message
     * @param callback
     */
    public static void sendFailResultCallback(final NetCode netCode, final String message, final HttpBaseCallback callback) {
        ALog.eTag(TAG_HTTP, "NetCode:%s,\nmsg:%s", netCode.getCode(), message);
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                if (null != callback) {
                    callback.onFailure(netCode, netCode.getDesc());
                }
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
    public static void sendProgressResultCallback(final long currentTotalLen, final long totalLen, final OkHttp3CallbackImpl callback, final boolean isFinish) {
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
     * @param url      请求地址
     * @param file     上传文件
     * @param callback 请求回调
     * @Description Request对象
     */
    public static Request builderFileRequest(String url, File file, HttpProgressCallback callback) {
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("imageName", "100210044.jpg");
        builder.addHeader("userName", "100210044");
        builder.addHeader("md5", FMD5Utils.getFileMD5(file));
        if (file != null) {
            RequestBody body = builderFileFormData(file, callback);
            builder.post(body);
        }

        return builder.build();
    }


    /**
     * @param file     上传文件
     * @param callback 请求回调
     * @Description RequestBody对象
     */
    private static RequestBody builderFileFormData(File file, final HttpProgressCallback callback) {
        ProgressRequestBody progressRequestBody = null;
        if (file != null) {
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_STREAM, file);
            progressRequestBody = new ProgressRequestBody(requestBody, new ProgressRequestBody.Listener() {
                @Override
                public void onRequestProgress(final long byteWritted, final long contentLength) {
                    mPlatform.execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onProgress(byteWritted, contentLength);
                        }
                    });
                }
            });
        }

        return progressRequestBody;
    }

    /**
     * @param methodType 请求方式
     * @param url        请求地址
     * @param params     请求参数
     * @param json       json数据格式
     * @Description Request对象
     */
    public static Request builderRequest(HttpMethodType methodType, String url, Map<String, String> params, String json) {
        Request.Builder builder = new Request.Builder().url(url);
        ALog.dTag(TAG_HTTP, "HttpMethodType:%s->url:%s", methodType.name(), url);
        if (methodType == HttpMethodType.POST) {
            if (json != null) {
                ALog.dTag(TAG_HTTP, "body(json):%s", json);
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
        String body = "";
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                builder.add(key, value);
                body += key + ":" + value + ",";
            }
        }
        ALog.dTag(TAG_HTTP, "body(map):%s", body);
        return builder.build();
    }

    /**
     * 默认不需要对返回的数据解密
     *
     * @param request  Request对象
     * @param callback 请求回调
     * @param isDecode 是否需要解密
     * @Description 异步请求
     */
    public static void doEnqueue(final Request request, final OkHttp3CallbackImpl callback, final boolean isDecode) {
        if (null != callback) {
            callback.onStart();
        }
        getOkHttpClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                NetCode netCode = NetCode.UNKNOW;
                if (e instanceof UnknownHostException) {
                    netCode = NetCode.CODE_6904;
                } else if (e instanceof SocketTimeoutException) {
//                    if (null != e.getMessage()) {
//                        if (e.getMessage().contains("failed to connect to")) {
//                            //TODO 连接超时
//                        }
//                        if (e.getMessage().equals("timeout")) {
//                            //TODO 读写超时
//                        }
//                    }
                    netCode = NetCode.CODE_6905;
                }
                sendFailResultCallback(netCode, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null == response) {
                    sendFailResultCallback(NetCode.CODE_6010, "服务器成功响应，但是response为空", callback);
                    return;
                }
                if (null == response.body()) {
                    sendFailResultCallback(NetCode.CODE_6020, "服务器成功响应，但是body为空", callback);
                    return;
                }
                String result = response.body().string();
                if (TextUtils.isEmpty(result)) {
                    sendFailResultCallback(NetCode.CODE_6021, "服务器成功响应，但是body里的内容为空", callback);
                    return;
                }
                try {
                    if (response.isSuccessful()) {
                        sendSuccessResultCallback(result, callback, isDecode);
                    } else {
                        sendFailResultCallback(NetCode.CODE_6011, "code:" + response.code() + ",msg:" + response.message(), callback);
                    }
                } catch (Exception e) {
                    sendFailResultCallback(NetCode.CODE_6900, e.getMessage(), callback);
                }
            }
        });
    }


    /**
     * Post数据请求
     *
     * @param request  Request对象
     * @param callback 请求回调
     * @Description 异步请求
     */
    public static void doPostEnqueue(final Request request, final HttpBaseCallback callback) {
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                NetCode netCode = NetCode.UNKNOW;
                if (e instanceof UnknownHostException) {
                    netCode = NetCode.CODE_6904;
                } else if (e instanceof SocketTimeoutException) {
//                    if (null != e.getMessage()) {
//                        if (e.getMessage().contains("failed to connect to")) {
//                            //TODO 连接超时
//                        }
//                        if (e.getMessage().equals("timeout")) {
//                            //TODO 读写超时
//                        }
//                    }
                    netCode = NetCode.CODE_6905;
                }
                sendFailResultCallback(netCode, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null == response) {
                    sendFailResultCallback(NetCode.CODE_6010, "服务器成功响应，但是response为空", callback);
                    return;
                }
                if (null == response.body()) {
                    sendFailResultCallback(NetCode.CODE_6020, "服务器成功响应，但是body为空", callback);
                    return;
                }
                String result = response.body().string();
                if (TextUtils.isEmpty(result)) {
                    sendFailResultCallback(NetCode.CODE_6021, "服务器成功响应，但是body里的内容为空", callback);
                    return;
                }
                try {
                    if (response.isSuccessful()) {
                        sendSuccessResultCallback(result, callback);
                    } else {
                        sendFailResultCallback(NetCode.CODE_6011, "code:" + response.code() + ",msg:" + response.message(), callback);
                    }
                } catch (Exception e) {
                    sendFailResultCallback(NetCode.CODE_6900, e.getMessage(), callback);
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
    public static Call doDownloadEnqueue(final Request request, final String destFileDir, final String destFileName, final OkHttp3CallbackImpl callback) {
        if (null != callback) {
            callback.onStart();
        }
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(NetCode.FAILED, e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (null == response) {
                    sendFailResultCallback(NetCode.CODE_6010, "服务器成功响应，但是response为空", callback);
                    return;
                }
                if (null == response.body()) {
                    sendFailResultCallback(NetCode.CODE_6020, "服务器成功响应，但是body为空", callback);
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
                        sendFailResultCallback(NetCode.CODE_6901, e.getMessage(), callback);
                    } else {
                        sendFailResultCallback(NetCode.CODE_6903, e.getMessage(), callback);
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

    public static void main(String[] args) {

    }

}