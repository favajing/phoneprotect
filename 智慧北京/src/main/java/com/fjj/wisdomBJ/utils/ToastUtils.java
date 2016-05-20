package com.fjj.wisdomBJ.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.utils
 * @创建者: 范晶晶
 * @创建时间: 2016/5/20 14:53
 * @描述 吐司通用工具
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class ToastUtils
{
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
