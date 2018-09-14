package com.itsdf07.okhttp3.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itsdf07.alog.ALog;
import com.itsdf07.okhttp3.NetCode;
import com.itsdf07.okhttp3.OkHttp3Request;
import com.itsdf07.okhttp3.bean.BaseBean;
import com.itsdf07.okhttp3.callback.OkHttp3Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description ：网络返回的结果基础类
 * @Author itsdf07
 * @Time 2018/09/13
 */
public abstract class OkHttp3CallbackImpl<Result extends BaseBean> implements OkHttp3Callback {

    @Override
    public void onSuccess(String result, boolean isDecode) {
        ALog.dTag(OkHttp3Request.TAG_HTTP, "isDecode:%s,data:%s", isDecode, result);
        if (isDecode) {
            //如果数据需要解密，则接收到后不处理直接抛出
            BaseBean bean = new BaseBean();
            bean.setDecode(true);
            bean.setEncrptyData(result);
            onSuccess((Result) bean);
            return;
        }
        Gson gson = new Gson();
        try {
            Class<?> clz = analysisClassInfo(this);
            Result bean = (Result) gson.fromJson(result, clz);
            if (null == bean) {
                onFailure(NetCode.CODE_6101.getCode(), NetCode.CODE_6101.getDesc());
                return;
            }
            if (TextUtils.isEmpty(bean.getCode())) {
                onFailure(NetCode.CODE_6102.getCode(), NetCode.CODE_6102.getDesc() + "code");
                return;
            }
            if (bean.getCode().equals(NetCode.SUCCESS.getCode())) {
                bean.setDecode(false);
                onSuccess(bean);
            } else {
                onFailure(bean.getCode(), bean.getDesc());
            }
        } catch (JsonSyntaxException e) {
            onFailure(NetCode.CODE_6902.getCode(), NetCode.CODE_6902.getDesc());
        } catch (Exception e) {
            onFailure(NetCode.CODE_6900.getCode(), NetCode.CODE_6900.getDesc());

        }
    }

    @Override
    public void onFailure(String code, String msg) {
        BaseBean bean = new BaseBean();
        bean.setCode(code);
        bean.setDesc(msg);
        onFailed(bean);
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
     * 应答错误码
     *
     * @param bean code + desc
     */
    public abstract void onFailed(BaseBean bean);

    /**
     * @param currentTotalLen 进度
     * @param totalLen        总量
     * @Description 上传或下载时进度回调
     */
    public void onProgress(long currentTotalLen, long totalLen) {
    }

    /**
     * 请求结束
     */
    public abstract void onFinish();
}
