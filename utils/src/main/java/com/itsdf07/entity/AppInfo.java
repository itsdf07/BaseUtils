package com.itsdf07.entity;

/**
 * @Description:
 * @Author itsdf07
 * @Time 2018/7/6 11:31
 */

public class AppInfo {
    private String appName = "";
    private String packageName = "";
    private String versionName = "";
    private int versionCode = 1;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "appName:" + appName + ",versionName:" + versionName + ",versionCode:" + versionCode + ",packageName:" + packageName;
    }
}
