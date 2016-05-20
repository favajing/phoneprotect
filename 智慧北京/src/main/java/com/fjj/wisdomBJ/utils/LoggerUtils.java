package com.fjj.wisdomBJ.utils;

import android.util.Log;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.utils
 * @创建者: 范晶晶
 * @创建时间: 2016/5/20 15:09
 * @描述 日志管理
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class LoggerUtils
{
    private static final int VERBOSE  = 1;
    private static final int DEBUG    = 2;
    private static final int INFO     = 3;
    private static final int WARN     = 4;
    private static final int ERROR    = 5;
    //切换变量过滤需要显示的log日志
    private static       int LOGLEVEL = 0;

    public static void v(String tag, String msg)
    {
        if (VERBOSE > LOGLEVEL) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg)
    {
        if (DEBUG > LOGLEVEL) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg)
    {
        if (INFO > LOGLEVEL) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg)
    {
        if (WARN > LOGLEVEL) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg)
    {
        if (ERROR > LOGLEVEL) {
            Log.e(tag, msg);
        }
    }

}
