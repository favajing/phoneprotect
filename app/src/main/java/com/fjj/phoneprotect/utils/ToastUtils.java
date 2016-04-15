package com.fjj.phoneprotect.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by favaj on 2016/4/15.
 */
public class ToastUtils {
    /**
     * 通用显示提示工具
     * @param activity
     * @param str
     */
    public static void show(final Activity activity, final String str) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
     * 通用显示提示工具
     * @param activity
     * @param str
     * @param duration
     */
    public static void show(final Activity activity, final String str, final int duration) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(activity, str, duration).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, str, duration).show();
                }
            });
        }
    }
}
