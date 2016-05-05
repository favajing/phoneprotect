package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.SystemInfoUtils;

public class TaskManagerActivity extends Activity {

    TextView taskcount ;
    TextView tasksize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        taskcount = (TextView) findViewById(R.id.tv_taskmanager_taskcount);
        tasksize = (TextView) findViewById(R.id.tv_taskmanager_tasksize);
        int cont = SystemInfoUtils.getRunningProcessCount(this);
        taskcount.setText("运行进程:" + cont + "个");
        long availRam = SystemInfoUtils.getAvailRam(this);
        long totalRam = SystemInfoUtils.getTotalRam(this);
        tasksize.setText("剩余/总内存:" + Formatter.formatFileSize(this,availRam) + "/" + Formatter.formatFileSize(this,totalRam));
    }
}
