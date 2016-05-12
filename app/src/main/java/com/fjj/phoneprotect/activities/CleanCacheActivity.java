package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.utils.ToastUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CleanCacheActivity extends Activity {

    private static final String TAG = "CleanCacheActivity";
    TextView tvjindu;
    ProgressBar pbclear;
    RelativeLayout rlcacheprogress;
    ListView lvcachelist;
    private PackageManager pm;
    private List<AppInfo> appInfos;
    private int complatecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_cache);

        lvcachelist = (ListView) findViewById(R.id.lv_cachelist);
        rlcacheprogress = (RelativeLayout) findViewById(R.id.rl_cacheprogress);
        tvjindu = (TextView) findViewById(R.id.tv_jindu);
        pbclear = (ProgressBar) findViewById(R.id.pb_clearcache);
        pm = getPackageManager();
        appInfos = new ArrayList<>();
        compareCache();
    }

    private void compareCache() {
        complatecount = 0;
        rlcacheprogress.setVisibility(View.VISIBLE);
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
                        Thread.sleep(20);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }



    //清理缓存
    private class myDataObServer extends IPackageDataObserver.Stub{
        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {
            Log.i(TAG, "清理状态:" + succeeded);
        }
    }

    private class myObServer extends IPackageStatsObserver.Stub {
        //获取缓存成功后调用
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            try {
                complatecount++;
                AppInfo info = new AppInfo();
                String packageName1 = pStats.packageName;
                Drawable icon = pm.getPackageInfo(packageName1, 0).applicationInfo.loadIcon(pm);
                final String name = pm.getPackageInfo(packageName1, 0).applicationInfo.loadLabel(pm).toString();
                if (pStats.cacheSize > 0) {
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
                        if (complatecount >= pbclear.getMax()){
                            rlcacheprogress.setVisibility(View.GONE);
                            ToastUtils.show(CleanCacheActivity.this, "扫描完成!");
                            showListView();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showListView() {
        lvcachelist.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return appInfos.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                viewholder holder;
                if (convertView != null){
                    view = convertView;
                    holder = (viewholder) view.getTag();
                }else{
                    view = View.inflate(getApplicationContext(),R.layout.item_cleancache_cachelist,null);
                    holder = new viewholder();
                    holder.icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_name);
                    holder.size = (TextView) view.findViewById(R.id.tv_item_cachesize);
                }
                AppInfo info = appInfos.get(position);
                holder.icon.setImageDrawable(info.getAppicon());
                holder.name.setText(info.getAppname());
                holder.size.setText(Formatter.formatFileSize(getApplicationContext(),info.getSize()));
                return view;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
    }

    public void clearall(View view) {
        Method[] methods = PackageManager.class.getMethods();
        for (Method method : methods) {
            if ("freeStorageAndNotify".equals(method.getName())){
                try {
                    //注意 Long.MAX_VALUE  和 Integ.MAX_VALUE 旧手机识别不了但是虚拟机不删除
                    method.invoke(pm, Long.MAX_VALUE,new myDataObServer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class viewholder{
        public TextView name ;
        public TextView size;
        public ImageView icon;
    }

}
