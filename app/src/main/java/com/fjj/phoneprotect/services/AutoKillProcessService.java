package com.fjj.phoneprotect.services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutoKillProcessService extends Service {

    private static final String TAG = "AutoKillProcessService";
    ScreenReceiver screenReceiver;
    Timer timer;
    TimerTask task;
    public AutoKillProcessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        screenReceiver =new ScreenReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver, intentFilter);
        //定时器
//        timer = new Timer();
//        task = new TimerTask() {
//            @Override
//            public void run() {
//                Log.i(TAG, "定时启动");
//            }
//        };
//        timer.schedule(task,0,1000);
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenReceiver);
//        timer.cancel();
//        task.cancel();
        super.onDestroy();

    }

    private class ScreenReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //监听锁屏
            Log.i(TAG, "锁屏啦");
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> rp = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : rp) {
                am.killBackgroundProcesses(info.processName);
            }
        }
    }
}
