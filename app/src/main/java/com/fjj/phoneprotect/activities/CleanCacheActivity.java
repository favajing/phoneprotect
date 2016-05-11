package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.utils.ToastUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CleanCacheActivity extends Activity {

    TextView tvjindu;
    ProgressBar pbclear;
    private PackageManager pm;
    private List<AppInfo> appInfos;
    private int complatecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_cache);

        tvjindu = (TextView) findViewById(R.id.tv_jindu);
        pbclear = (ProgressBar) findViewById(R.id.pb_clearcache);
        pm = getPackageManager();
        appInfos = new ArrayList<>();
        compareCache();
    }

    private void compareCache() {
        complatecount = 0;
        new Thread() {
            @Override
            public void run() {
                List<PackageInfo> allpackages = pm.getInstalledPackages(0);
                //进度相关的控件可以在子线程中更新,内部判断
                pbclear.setMax(allpackages.size());
                for (PackageInfo pk : allpackages) {
                    String packageName = pk.packageName;
                    try {
                        //通过反射获取getPackageSizeInfo方法
                        Method method = PackageManager.class.getMethod(
                                "getPackageSizeInfo", String.class,
                                IPackageStatsObserver.class);

                        method.invoke(pm, packageName, new myObServer());
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ToastUtils.show(CleanCacheActivity.this,"扫描完成!");
            }
        }.start();

    }

    private class myObServer extends IPackageStatsObserver.Stub{
        //获取缓存成功后调用
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            try {
                complatecount++;
                AppInfo info = new AppInfo();
                String packageName1 = pStats.packageName;
                Drawable icon = pm.getPackageInfo(packageName1, 0).applicationInfo.loadIcon(pm);
                final String name = pm.getPackageInfo(packageName1, 0).applicationInfo.loadLabel(pm).toString();
                if (pStats.cacheSize > 0){
                    info.setApppackage(packageName1);
                    info.setSize(pStats.cacheSize);
                    info.setAppname(name);
                    info.setAppicon(icon);
                    appInfos.add(info);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvjindu.setText("正在扫描:" + name);
                        pbclear.setProgress(complatecount);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
