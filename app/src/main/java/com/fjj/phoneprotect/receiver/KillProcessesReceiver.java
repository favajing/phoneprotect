package com.fjj.phoneprotect.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class KillProcessesReceiver extends BroadcastReceiver {
    private static final String TAG = "KillProcessesReceiver";

    public KillProcessesReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //一键清理进程
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> rp = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : rp) {
            am.killBackgroundProcesses(info.processName);
        }
    }
}
