package com.fjj.phoneprotect.engine;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ListView;

import com.fjj.phoneprotect.domain.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AppAdmin {
    /**
     * 获取手机应用程序信息
     * @return
     */
    public static List<AppInfo> findApp(Context context){
        ArrayList<AppInfo> res = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pk : installedPackages) {
            AppInfo info = new AppInfo();
            info.setApppackage(pk.packageName);
            info.setAppname(pk.applicationInfo.loadLabel(pm).toString());
            info.setAppicon(pk.applicationInfo.loadIcon(pm));
            String sourceDir = pk.applicationInfo.sourceDir;
            File file = new File(sourceDir);
            info.setSize(file.length());
            int flags = pk.applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) ==0 ){
                info.setIsuserapp(true);
            }else {
                info.setIsuserapp(false);
            }
            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) ==0 ){
                info.setInRom(true);
            }else {
                info.setInRom(false);
            }
            res.add(info);
        }
        return res;
    }
}
