package com.fjj.phoneprotect.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.fjj.phoneprotect.activities.EnterPassWordActivity;
import com.fjj.phoneprotect.db.dao.AppLockInfoDao;

import java.util.List;

public class WatchDogService extends Service {
    private static final String TAG = "WatchDogService";
    private boolean iswatch = true;
    private String topname;
    private String packagename;
    private InnerStopWatchReceive receive;
    private List<ActivityManager.RunningTaskInfo> taskInfo;
    private Intent intent;
    private ActivityManager am;
    private List<String> alllockinfos;
    private ContentObserver contentObserver;
    private AppLockInfoDao dao;

    public WatchDogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receive = new InnerStopWatchReceive();
        iswatch = true;
        //注册广播接收者监听输入密码后传来的包名
        IntentFilter filter = new IntentFilter("com.fjj.phoneprotect.wangwang");
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receive, filter);
        //获取数据库中锁定程序的数据
        dao = new AppLockInfoDao(getApplicationContext());
        alllockinfos = dao.FindAll();
        //输入密码窗口意图
        intent = new Intent(WatchDogService.this, EnterPassWordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //获取ActivityManager
        am = ((ActivityManager) getSystemService(ACTIVITY_SERVICE));
        //注册内容观察者,接收修改数据后传来的消息
        Uri uri = Uri.parse("content://com.fjj.phoneprotect.dbchange");
        contentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                //数据库变化后重新获取
                alllockinfos = dao.FindAll();
            }
        };
        getContentResolver().registerContentObserver(uri, true, contentObserver);

        startThread();
    }
    /**
     * 开启线程
     */
    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                while (iswatch) {
                    try {
                        //优化
                        // TODO: 2016/5/10 5.0以上版本由于权限问题无法获取所有运行中的程序列表
                        if (Build.VERSION.SDK_INT <= 20) { //For versions less than lollipop
                            taskInfo = am.getRunningTasks(1);
                            topname = taskInfo.get(0).topActivity.getPackageName();
                        } else { //For versions Lollipop and above
                            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
                            for (ActivityManager.RunningAppProcessInfo task : tasks) {
                                if (task.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                    topname = task.processName;
                                }
                            }
                        }
                        if (alllockinfos.contains(topname) && !topname.equals(WatchDogService.this.packagename)) {
                            //已经锁定
                            Log.i(TAG, topname + "已锁定");
                            intent.putExtra("packagename", topname);
                            startActivity(intent);
                        } else {
                            //没有锁定
                            Log.i(TAG, topname + "未锁定");
                        }
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        //停止循环
        iswatch = false;
        //注销广播接收者
        unregisterReceiver(receive);
        receive = null;
        //注销内容观察者
        getContentResolver().unregisterContentObserver(contentObserver);
        super.onDestroy();
    }

    private class InnerStopWatchReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
                iswatch = true;
                startThread();
            }
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                iswatch = false;
            }
            if ("com.fjj.phoneprotect.wangwang".equals(intent.getAction())){
                packagename = intent.getStringExtra("packagename");
            }
        }
    }
}
