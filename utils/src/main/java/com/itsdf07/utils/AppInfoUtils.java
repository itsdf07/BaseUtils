package com.itsdf07.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.itsdf07.alog.ALog;
import com.itsdf07.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: App相关信息获取的工具类
 * @Author itsdf07
 * @Time 2018/7/6 11:09
 */

public class AppInfoUtils {

    /**
     * 获取app的 versionCode
     *
     * @param context
     * @return 如 1
     */
    public static int getVersionCode(Context context) {
        if (null == context) {
            ALog.e("带入的content参数为null");
            return -1;
        }
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = -1;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取app的 versionName
     *
     * @param context
     * @return 如 1.0.0
     */
    public static String getVersionName(Context context) {
        if (null == context) {
            ALog.e("带入的content参数为null");
            return "";
        }
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取application中指定的meta-data。本例中，调用方法时key如：UMENG_CHANNEL
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (null == context) {
            ALog.e("带入的content参数为null");
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }
//    使用getAppMetaData（context,"getAppMetaData"）

    /**
     * 获取已安装应用信息（不包含系统自带）
     *
     * @param context
     * @return 已安装应用列表，异常时返回 null
     */
    public static ArrayList<AppInfo> getInstallApps(@NonNull Context context) {
        ArrayList<AppInfo> infos = new ArrayList<>();
        if (null == context) {
            ALog.e("带入的content参数为null");
            return null;
        }
        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : apps) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // 非系统应用
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                appInfo.setVersionName(packageInfo.versionName);
                appInfo.setVersionCode(packageInfo.versionCode);
                appInfo.setPackageName(packageInfo.packageName);
                infos.add(appInfo);
            }
        }
        return infos;
    }

    /**
     * 获取某个非系统预装的app信息
     *
     * @param context
     * @param packageName 对应的app包名
     * @return 查找到的安装应用信息，异常、无结果时返回 null
     */
    public static AppInfo getInstallApp(@NonNull Context context, String packageName) {
        AppInfo appInfo = null;
        if (null == context) {
            ALog.e("带入的content参数为null");
            return appInfo;
        }
        List<PackageInfo> apps = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : apps) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (packageInfo.packageName.equals(packageName)) {
                    appInfo = new AppInfo();
                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                    appInfo.setVersionName(packageInfo.versionName);
                    appInfo.setVersionCode(packageInfo.versionCode);
                    appInfo.setPackageName(packageInfo.packageName);
                    break;
                }
            }
        }
        return appInfo;
    }
}
