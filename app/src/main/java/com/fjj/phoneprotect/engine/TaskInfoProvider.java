package com.fjj.phoneprotect.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.TaskInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by favaj on 2016/5/5.
 */
public class TaskInfoProvider {
    public static List<TaskInfo> findRunningProcessInfos(Context context) {
        List<TaskInfo> res = new ArrayList<>();
        //获取进程管理器
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();
        //获取所有运行中的进程
        List<ActivityManager.RunningAppProcessInfo> runprocesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo rp : runprocesses) {
            TaskInfo info = new TaskInfo();
            //获取进程包名
            String packagename = rp.processName;
            int size = activityManager.getProcessMemoryInfo(new int[]{rp.pid})[0].getTotalPrivateDirty() * 1024;
            info.setMemsize(size);
            info.setPackagename(packagename);
            try {
                //根据包管理器获取包信息
                PackageInfo packageInfo = pm.getPackageInfo(packagename, 0);
                //获取应用程序名称
                String name = packageInfo.applicationInfo.loadLabel(pm).toString();
                info.setTaskName(name);
                //获取图标
                Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
                info.setIcon(icon);
                //判断是否为用户进程
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    info.setIsuser(false);
                } else {
                    info.setIsuser(true);
                }
            } catch (PackageManager.NameNotFoundException e) {
                info.setTaskName(packagename);
                info.setIcon(context.getResources().getDrawable(R.drawable.callmsgsafe));
            }
            res.add(info);
        }
        return res;
    }
}
