package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.engine.AppAdmin;

import java.io.File;
import java.util.Formatter;
import java.util.List;

public class AppManagerActivity extends Activity {

    TextView androidunused;
    TextView sdunused;
    ListView lvapplist;
    LinearLayout llloadding;
    private List<AppInfo> appinfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        androidunused = (TextView) findViewById(R.id.tv_appmanager_androidunused);
        sdunused = (TextView) findViewById(R.id.tv_appmanager_sdunused);
        File androidfile = Environment.getDataDirectory();
        long freeSpace = androidfile.getFreeSpace();
        androidunused.setText("内存可用:" + android.text.format.Formatter.formatFileSize(this, freeSpace));
        File sdfile = Environment.getExternalStorageDirectory();
        sdunused.setText("SD卡可用:" + android.text.format.Formatter.formatFileSize(this, sdfile.getFreeSpace()));

        lvapplist = (ListView) findViewById(R.id.lv_appmanager_applist);
        llloadding = (LinearLayout) findViewById(R.id.ll_appmanager_loadding);
        initListView();
    }

    private void initListView() {
        llloadding.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                appinfos = AppAdmin.findApp(AppManagerActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvapplist.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return appinfos.size();
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = View.inflate(AppManagerActivity.this, R.layout.item_appmanager_apps, null);
                                TextView appname = (TextView) view.findViewById(R.id.tv_appname);
                                appname.setText(appinfos.get(position).getAppname());
                                ImageView icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                                icon.setImageDrawable(appinfos.get(position).getAppicon());
                                TextView isrow = (TextView) view.findViewById(R.id.tv_isrow);
                                boolean inRom = appinfos.get(position).isInRom();
                                if (inRom) {
                                    isrow.setText("手机内存");
                                } else {
                                    isrow.setText("内存卡");
                                }
                                TextView appsize = (TextView) view.findViewById(R.id.tv_appsize);
                                appsize.setText(android.text.format.Formatter.formatFileSize(AppManagerActivity.this, appinfos.get(position).getSize()));
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
                        });
                        llloadding.setVisibility(View.INVISIBLE);
                    }
                });

                super.run();
            }
        }.start();

    }
}
