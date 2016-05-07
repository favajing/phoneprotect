package com.fjj.phoneprotect.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateWidgetService extends Service {

    private static final String TAG = "UpdateWidgetService";
    private Timer timer;
    private TimerTask task;

    public UpdateWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
//                Log.i(TAG, "每个3秒钟刷新");
            }
        };
        timer.schedule(task, 0,3000);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
        super.onDestroy();
    }
}
