package com.itsdf07.http.bean;

/**
 * Created by itsdf07 on 2017/7/20.
 */

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
}
