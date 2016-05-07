package com.fjj.phoneprotect.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.fjj.phoneprotect.services.UpdateWidgetService;

public class MyWidget extends AppWidgetProvider {
    public MyWidget() {
    }

    //第一次创建时执行
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    //最后一个被删除后执行
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent intent = new Intent(context, UpdateWidgetService.class);
        context.stopService(intent);
    }

    //每次创建时执行
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent intent = new Intent(context, UpdateWidgetService.class);
        context.startService(intent);
    }
    //每次删除后执行
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
