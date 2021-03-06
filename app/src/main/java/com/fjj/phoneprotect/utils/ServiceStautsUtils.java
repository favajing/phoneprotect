package com.fjj.phoneprotect.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 服务运行状态的工具类
 *
 */
public class ServiceStautsUtils {
    /**
     * 判断服务是否处于运行状态
     * @param context 上下文
     * @param classname 服务的全路径类名
     * @return  true 服务运行中  false 服务已经停止了.
     */
    public static boolean isServiceRunning(Context context,String classname){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(1000);
        for(ActivityManager.RunningServiceInfo info:infos){
            String runningclassname = info.service.getClassName();
            if(classname.equals(runningclassname)){
                return true;
            }
        }
        return false;
    }
}