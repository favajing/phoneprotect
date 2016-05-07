package com.fjj.phoneprotect.services;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.receiver.MyWidget;
import com.fjj.phoneprotect.utils.SystemInfoUtils;

import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateWidgetService extends Service {

    private static final String TAG = "UpdateWidgetService";
    private Timer timer;
    private TimerTask task;
    private AppWidgetManager manager;

    public UpdateWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //获取系统桌面更新widget的服务
        manager = AppWidgetManager.getInstance(this);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                ComponentName provider = new ComponentName(getApplicationContext(), MyWidget.class);
                //告诉桌面布局文件去哪里寻找  从哪个包里找谁
                RemoteViews view = new RemoteViews(getPackageName(), R.layout.process_widget);
                view.setTextViewText(R.id.process_count, "正在运行的软件:" + SystemInfoUtils.getRunningProcessCount(getApplicationContext()));
                view.setTextViewText(R.id.process_memory, "可用内存:" + android.text.format.Formatter.formatFileSize(getApplicationContext(), SystemInfoUtils.getAvailRam(getApplicationContext())));
                //定义一个广播意图
                Intent intent = new Intent();
                intent.setAction("com.fjj.phoneprotect.killProcesses");
                //延期的意图, 因为这个意图不是由当前应用程序立刻执行的,而是传递给别的进程 由别的进程执行.可以开启一个意图广播服务--现在只需要发送一个广播
                PendingIntent pintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //给View注册点击事件 view 对象  pendingIntent是一个描述信息，描述的是一个意图
                //返回的对象有其他应用程序调用 把这个延期的意图给另外一个程序，该程序就可以执行你要执行的动作了
                view.setOnClickPendingIntent(R.id.btn_clear, pintent);
                //组件名-更新 谁，远程的View对象
                manager.updateAppWidget(provider, view);

            }
        };
        timer.schedule(task, 0, 3000);
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
