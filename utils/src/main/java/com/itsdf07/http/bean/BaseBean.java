package com.itsdf07.http.bean;

/**
 * @Description ：网络应答报文基础类
 * @Author itsdf07
 * @Time 2018/07/18
 */
public class BaseBean {
    /**
     * code : 200
     * desc : 服务器成功处理请求
     */

    /**
     * 应答码
     */
    private String code;
    /**
     * 应答码描述
     */
    private String desc;
    /**
     * 加密数据
     */
    private String encrptyData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEncrptyData() {
        return encrptyData;
    }

    public void setEncrptyData(String encrptyData) {
        this.encrptyData = encrptyData;
    }
}
