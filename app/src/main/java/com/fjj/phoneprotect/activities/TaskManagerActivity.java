package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.TaskInfo;
import com.fjj.phoneprotect.engine.TaskInfoProvider;
import com.fjj.phoneprotect.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActivity extends Activity {

    TextView taskcount ;
    TextView tasksize;
    ListView tasklist;
    LinearLayout llload;
    private List<TaskInfo> userprocess;
    private List<TaskInfo> sysprocess;
    private BaseAdapter adapter;

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

        tasklist = (ListView) findViewById(R.id.lv_appmanager_applist);
        llload = (LinearLayout) findViewById(R.id.ll_appmanager_loadding);
        initdata();
    }

    private void initdata() {
        llload.setVisibility(View.INVISIBLE);
        new Thread(){
            @Override
            public void run() {
                //获取进程数据
                List<TaskInfo> infos = TaskInfoProvider.findRunningProcessInfos(getApplicationContext());
                userprocess = new ArrayList<TaskInfo>();
                sysprocess = new ArrayList<TaskInfo>();
                for (TaskInfo info : infos) {
                    if (info.isuser()) {
                        userprocess.add(info);
                    }else {
                        sysprocess.add(info);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置listview数据适配器
                        adapter = new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return userprocess.size() + sysprocess.size();
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                return null;
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }
                        };
                        tasklist.setAdapter(adapter);
                    }
                });
            }
        }.start();
    }
}
