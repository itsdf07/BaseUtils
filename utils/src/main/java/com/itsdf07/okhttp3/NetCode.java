package com.itsdf07.okhttp3;

/**
 * @Description
 * @Author itsdf07
 * @Time 2018/9/13/013
 */

public enum NetCode {
    SUCCESS(200, "请求成功"),

    FAILED(6000,"请求失败"),
    UNKNOW(6001,"未知错误"),
    CODE_6010(6010,"返回的数据response为空"),
    CODE_6011(6011,"返回的数据response失败"),
    CODE_6020(6020,"返回的数据body为空"),
    CODE_6021(6021,"返回的数据body里的内容为空"),

    //610_ 开头的为 Gson
    CODE_6100(6100, ""),
    /**
     * Gson的fromJson解析结果为null
     */
    CODE_6101(6101, "fromJson 解析的bean为null"),
    /**
     * Gson的fromJson解析结果的数据缺失
     */
    CODE_6102(6102, "fromJson 解析的bean的数据缺失:"),

    //620_ 开头的为
    CODE_6200(6200, ""),


    //690_ 开头的为 开头的为Exception 异常
    CODE_6900(6900, "Exception ..."),
    CODE_6901(6901, "SocketException ..."),
    CODE_6902(6902, "JsonSyntaxException ..."),
    CODE_6903(6903, "IOException ...");

    /**
     * code
     */
    private int code;
    /**
     * code对应的描述
     */
    private String desc;


    private NetCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 普通方法
    public static String getDesc(int code) {
        for (NetCode c : NetCode.values()) {
            if (c.getCode().equals(code)) {
                return c.desc;
            }
        }
        return null;
    }

    // get set 方法
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code + "";
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static void main(String[] args) {
        System.out.println(NetCode.SUCCESS.getCode() + "," + NetCode.SUCCESS.getDesc());
    }
}
