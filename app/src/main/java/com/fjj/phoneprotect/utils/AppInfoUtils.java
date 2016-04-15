package com.fjj.phoneprotect.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2016/4/15.
 */
public class AppInfoUtils {
    /**
     * 获取版本名称
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //不可能发生
            return  "";
        }
    }
    /**
     * 获取版本号
     * @param context 上下文
     * @return
     */
    public static int getVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //不可能发生
            return  0;
        }
    }
}
