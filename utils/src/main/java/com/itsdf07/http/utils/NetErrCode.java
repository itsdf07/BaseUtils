package com.itsdf07.http.utils;

import android.text.TextUtils;

/**
 * @Description ：请求网络错误
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class NetErrCode {
    /**
     * http响应成功
     */
    public static final String SUCCESS = "200";

    /**
     * http响应失败，比如无网络的情况
     */
    public static final String ERR_1_FAILURE = "-1001";
    /**
     * http响应成功，但是Response为null
     */
    public static final String ERR_2_RESPONSE_NULL = "-2001";
    /**
     * http响应成功，但是response.body().string()失败
     */
    public static final String ERR_2_RESPONSE_PARSE = "-2002";
    /**
     * http响应成功后，在做本地Gson解析时失败
     */
    public static final String ERR_2_GSON_RESULT = "-2003";
    /**
     * http响应成功后，本地解析结果中，Code为空
     */
    public static final String ERR_2_CODE_NULL = "-2004";

    /**
     * http响应成功，但是中途取消了
     */
    public static final String ERR_2_RESPONSE_CANCLE = "-2005";

    /**
     * http响应成功，但是MD5校验失败
     */
    public static final String ERR_2_RESPONSE_MD5 = "-2006";

    /**
     * 网络码内容转义
     *
     * @param code
     * @return
     */
    public static String translateNetCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return "传入的网络转换码不能为空";
        }
//        String translateResult = Utils.getApplication().getString(R.string.tip_net_error);
//        if (ERR_1_FAILURE.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_net_error);
//        } else if (ERR_2_RESPONSE_NULL.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_response_error);
//        } else if (ERR_2_RESPONSE_PARSE.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_response_result_error);
//        } else if (ERR_2_GSON_RESULT.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_gson_result_error);
//        } else if (ERR_2_CODE_NULL.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_gson_code_error);
//        } else if (ERR_2_RESPONSE_CANCLE.equals(code)) {
//            translateResult = Utils.getApplication().getString(R.string.tip_gson_code_error);
//        }
//        return translateResult;
        return code;
    }
}
