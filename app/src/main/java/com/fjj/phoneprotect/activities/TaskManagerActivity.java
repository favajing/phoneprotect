package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.TaskInfo;
import com.fjj.phoneprotect.engine.TaskInfoProvider;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.SystemInfoUtils;
import com.fjj.phoneprotect.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActivity extends Activity {

    TextView taskcount;
    TextView tasksize;
    ListView tasklist;
    LinearLayout llload;
    TextView tasktype;
    private List<TaskInfo> userprocess;
    private List<TaskInfo> sysprocess;
    private BaseAdapter adapter;
    private TaskInfo appInfo;
    private int totalcont;
    private long availRam;
    private long totalRam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        taskcount = (TextView) findViewById(R.id.tv_taskmanager_taskcount);
        tasksize = (TextView) findViewById(R.id.tv_taskmanager_tasksize);
        tasktype = (TextView) findViewById(R.id.tv_taskmanager_tasktype);
        totalcont = SystemInfoUtils.getRunningProcessCount(this);
        taskcount.setText("运行进程:" + totalcont + "个");
        availRam = SystemInfoUtils.getAvailRam(this);
        totalRam = SystemInfoUtils.getTotalRam(this);
        tasksize.setText("剩余/总内存:" + Formatter.formatFileSize(this, availRam) + "/" + Formatter.formatFileSize(this, totalRam));

        tasklist = (ListView) findViewById(R.id.lv_appmanager_applist);
        llload = (LinearLayout) findViewById(R.id.ll_appmanager_loadding);
        initdata();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    private void initdata() {
        llload.setVisibility(View.INVISIBLE);
        new Thread() {
            @Override
            public void run() {
//                获取进程数据
//                获取进程数据
                List<TaskInfo> infos = TaskInfoProvider.findRunningProcessInfos(getApplicationContext());
                userprocess = new ArrayList<TaskInfo>();
                sysprocess = new ArrayList<TaskInfo>();
                for (TaskInfo info : infos) {
                    if (info.isuser()) {
                        userprocess.add(info);
                    } else {
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
                                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                                if (sp.getBoolean("showsys",true)){
                                    return userprocess.size() + sysprocess.size() + 2;
                                }else {
                                    return userprocess.size() + 1;
                                }
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                //分类显示用户与系统程序
                                if (position == 0) {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setTextSize(20);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setBackgroundColor(Color.GRAY);
                                    textView.setText("用户进程: " + userprocess.size() + "个");
                                    return textView;
                                } else if (position == userprocess.size() + 1) {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setTextSize(20);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setBackgroundColor(Color.GRAY);
                                    textView.setText("系统进程: " + sysprocess.size() + "个");
                                    return textView;
                                } else if (position <= userprocess.size()) {
                                    position--;
                                } else {
                                    position -= 2;
                                }
                                //判断是用户还是系统程序
                                if (position < userprocess.size()) {
                                    appInfo = userprocess.get(position);
                                } else {
                                    appInfo = sysprocess.get(position - userprocess.size());
                                }
                                View view;
                                viewholder holder;
                                //优化--复用过期view
                                if (convertView != null && convertView instanceof RelativeLayout) {
                                    view = convertView;
                                    //使用tag标签提高查找view效率
                                    holder = (viewholder) view.getTag();
                                } else {
                                    view = View.inflate(TaskManagerActivity.this, R.layout.item_taskmanager_layout, null);
                                    holder = new viewholder();
                                    holder.name = (TextView) view.findViewById(R.id.tv_name);
                                    holder.icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                                    holder.memsize = (TextView) view.findViewById(R.id.tv_memsize);
                                    holder.check = (CheckBox) view.findViewById(R.id.cb_check);
                                    view.setTag(holder);
                                }
                                //设置文本信息
                                holder.name.setText(appInfo.getTaskName());
                                holder.memsize.setText("内存占用:" + Formatter.formatFileSize(TaskManagerActivity.this, appInfo.getMemsize()));
                                holder.icon.setImageDrawable(appInfo.getIcon());
                                holder.check.setChecked(appInfo.ischecked());
                                if (appInfo.getPackagename().equals(getPackageName())){
                                    holder.check.setVisibility(View.GONE);
                                }
                                return view;
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
                        //模拟浮动标识符
                        tasklist.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                                switch (scrollState) {
                                    case SCROLL_STATE_TOUCH_SCROLL:
                                        break;
                                }
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                if (firstVisibleItem <= userprocess.size()) {
                                    tasktype.setText("用户进程: " + userprocess.size() + "个");
                                } else {
                                    tasktype.setText("系统进程: " + sysprocess.size() + "个");
                                }
                            }
                        });
                        //设置点击事件切换checkbox
                        tasklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //分类显示用户与系统程序
                                if (position == 0) {

                                    return;
                                } else if (position == userprocess.size() + 1) {
                                    return;
                                } else if (position <= userprocess.size()) {
                                    position--;
                                } else {
                                    position -= 2;
                                }
                                //判断是用户还是系统程序
                                if (position < userprocess.size()) {
                                    appInfo = userprocess.get(position);
                                } else {
                                    appInfo = sysprocess.get(position - userprocess.size());
                                }
                                if (!appInfo.getPackagename().equals(getPackageName())){
                                    if (appInfo.ischecked()) {
                                        appInfo.setIschecked(false);
                                    } else {
                                        appInfo.setIschecked(true);
                                    }
                                }

                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }.start();
    }

    //全选
    public void selectall(View view) {
        for (TaskInfo usp : userprocess) {
            if (!usp.getPackagename().equals(getPackageName())){
                usp.setIschecked(true);
            }
        }
        for (TaskInfo sysproces : sysprocess) {
            sysproces.setIschecked(true);
        }
        adapter.notifyDataSetChanged();
    }

    //反选
    public void unselect(View view) {
        for (TaskInfo usp : userprocess) {
            boolean ischecked = usp.ischecked();
            usp.setIschecked(!ischecked);
        }
        for (TaskInfo sysproces : sysprocess) {
            boolean ischecked = sysproces.ischecked();
            sysproces.setIschecked(!ischecked);
        }
        adapter.notifyDataSetChanged();
    }

    //清理
    public void clear(View view) {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int count = 0;
        long killsize = 0;
        ArrayList<TaskInfo> kill = new ArrayList<>();
        for (TaskInfo up : userprocess) {
            if (up.ischecked()) {
                am.killBackgroundProcesses(up.getPackagename());
                count++;
                killsize += up.getMemsize();
                kill.add(up);
            }
        }
        userprocess.removeAll(kill);
        kill.clear();
        for (TaskInfo sysproces : sysprocess) {
            if (sysproces.ischecked()) {
                am.killBackgroundProcesses(sysproces.getPackagename());
                count++;
                killsize += sysproces.getMemsize();
                kill.add(sysproces);
            }
        }
        sysprocess.removeAll(kill);
        if (count > 0){
            adapter.notifyDataSetChanged();
            totalcont -= count;
            taskcount.setText("运行进程:" + totalcont + "个");
            availRam += killsize;
            tasksize.setText("剩余/总内存:" + Formatter.formatFileSize(this, availRam) + "/" + Formatter.formatFileSize(this, totalRam));
            ToastUtils.show(TaskManagerActivity.this, "清理" + count + "个进程,释放" + Formatter.formatFileSize(this, killsize) + "内存");
        }
    }

    public void setting(View view) {
        IntentUtils.startActivity(this,TaskSettingActivity.class);
    }

    class viewholder {
        public TextView name;
        public TextView memsize;
        public CheckBox check;
        public ImageView icon;
    }
}
