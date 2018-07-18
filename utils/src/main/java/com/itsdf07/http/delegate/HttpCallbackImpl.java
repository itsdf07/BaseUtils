package com.itsdf07.http.delegate;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itsdf07.alog.ALog;
import com.itsdf07.http.bean.BaseBean;
import com.itsdf07.http.utils.NetErrCode;
import com.itsdf07.http.utils.OkHttpRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description ：网络返回的结果基础类
 * @Author itsdf07
 * @Time 2018/07/18
 */
public abstract class HttpCallbackImpl<Result extends BaseBean> implements IHttpCallback {

    @Override
    public void onSuccess(String result) {
        ALog.dTag(OkHttpRequest.TAG_HTTP, "onSuccess : result = %s", result);
        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        try {
            Result bean = (Result) gson.fromJson(result, clz);
            if (null == bean) {
                onFailure(NetErrCode.ERR_GSON_RESULT_FAILURE, NetErrCode.translateNetCode(NetErrCode.ERR_GSON_RESULT_FAILURE));
                return;
            }
            if (TextUtils.isEmpty(bean.getCode())) {
                onFailure(NetErrCode.ERR_CODE_NULL_FAILURE, NetErrCode.translateNetCode(NetErrCode.ERR_CODE_NULL_FAILURE));
                return;
            }
            if (bean.getCode().equals("200")) {
                onSuccess(bean);
            } else {
                onFailure(bean.getCode(), bean.getDesc());
            }
        } catch (JsonSyntaxException e) {
            onFailure(NetErrCode.ERR_GSON_RESULT_FAILURE, NetErrCode.translateNetCode(NetErrCode.ERR_GSON_RESULT_FAILURE));
        }
    }

    @Override
    public void onFailure(String code, String error) {
        BaseBean bean = new BaseBean();
        bean.setCode(code);
        bean.setDesc(error);
        onFailureResult(bean);
    }

    /**
     * @param object
     * @return
     */
    private Class<?> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    /***************************************** abstract **************************************************/

    /**
     * 开始请求
     */
    public abstract void onStart();

    /**
     * 成功请求http并成功解析响应的Json结果的回调
     *
     * @param result gson解析对象
     */
    public abstract void onSuccess(Result result);

    /**
     * @param currentTotalLen 进度
     * @param totalLen        总量
     * @Description 上传或下载时进度回调
     */
    public void onProgress(long currentTotalLen, long totalLen) {
    }

    /**
     * 应答错误码
     *
     * @param bean code + desc
     */
    public abstract void onFailureResult(BaseBean bean);

    /**
     * 请求结束
     */
    public abstract void onFinish();
}