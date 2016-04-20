package com.fjj.phoneprotect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "boot";

    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "手机启动啦");
    }
}
