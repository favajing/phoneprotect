package com.fjj.phoneprotect.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2016/4/16.
 */
public class IntentUtils {
    /**
     * 开启新界面
     *
     * @param activity
     * @param cls
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity.getApplicationContext(), cls);
        activity.startActivity(intent);
    }

    /**
     * 开启新界面,同时关闭旧的
     *
     * @param activity
     * @param cls
     */
    public static void startActivityAndFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity.getApplicationContext(), cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 延迟开启新界面
     *
     * @param activity
     * @param cls
     * @param time
     */
    public static void startActivity(final Activity activity, final Class<?> cls, final long time) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(activity, cls);
                activity.startActivity(intent);
            }

            ;
        }.start();
    }

    /**
     * 开启系统自带安装应用界面
     * @param activity
     * @param uri 需要安装的应用文件地址
     */
    public static void startInsertActivity(Activity activity, Uri uri) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
