package com.fjj.phoneprotect.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.engine.AppAdmin;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class AppManagerActivity extends Activity {

    TextView apptype;
    /**
     * 系统可用空间
     */
    TextView androidunused;
    /**
     * sd卡可用空间
     */
    TextView sdunused;
    /**
     * app信息
     */
    ListView lvapplist;
    /**
     * 加载中...
     */
    LinearLayout llloadding;
    private List<AppInfo> appinfos;
    private List<AppInfo> userappinfos;
    private List<AppInfo> sysappinfos;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        apptype = (TextView) findViewById(R.id.tv_appmanager_apptype);
        androidunused = (TextView) findViewById(R.id.tv_appmanager_androidunused);
        sdunused = (TextView) findViewById(R.id.tv_appmanager_sdunused);
        //获取系统可用空间
        File androidfile = Environment.getDataDirectory();
        long freeSpace = androidfile.getFreeSpace();
        androidunused.setText("内存可用:" + android.text.format.Formatter.formatFileSize(this, freeSpace));
        //获取sd卡可用空间
        File sdfile = Environment.getExternalStorageDirectory();
        sdunused.setText("SD卡可用:" + android.text.format.Formatter.formatFileSize(this, sdfile.getFreeSpace()));

        lvapplist = (ListView) findViewById(R.id.lv_appmanager_applist);
        llloadding = (LinearLayout) findViewById(R.id.ll_appmanager_loadding);
        initListView();
    }

    @Override
    protected void onDestroy() {
        dismisPopupWindow();
        super.onDestroy();
    }

    private void initListView() {
        llloadding.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                appinfos = AppAdmin.findApp(AppManagerActivity.this);
                userappinfos = new ArrayList<AppInfo>();
                sysappinfos = new ArrayList<AppInfo>();
                //区分用户和系统应用程序
                for (AppInfo appinfo : appinfos) {
                    if (appinfo.isuserapp()) {
                        userappinfos.add(appinfo);
                    } else {
                        sysappinfos.add(appinfo);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvapplist.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return userappinfos.size() + sysappinfos.size() + 2;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                //分类显示用户与系统程序
                                if (position == 0) {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setTextSize(20);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setBackgroundColor(Color.GRAY);
                                    textView.setText("用户程序: " + userappinfos.size() + "个");
                                    return textView;
                                } else if (position == userappinfos.size() + 1) {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setTextSize(20);
                                    textView.setTextColor(Color.WHITE);
                                    textView.setBackgroundColor(Color.GRAY);
                                    textView.setText("系统程序: " + sysappinfos.size() + "个");
                                    return textView;
                                } else if (position <= userappinfos.size()) {
                                    position--;
                                } else {
                                    position -= 2;
                                }
                                //判断是用户还是系统程序
                                AppInfo appInfo;
                                if (position < userappinfos.size()) {
                                    appInfo = userappinfos.get(position);
                                } else {
                                    appInfo = sysappinfos.get(position - userappinfos.size());
                                }
                                View view;
                                viewholder holder;
                                //优化--复用过期view
                                if (convertView != null && convertView instanceof RelativeLayout) {
                                    view = convertView;
                                    //使用tag标签提高查找view效率
                                    holder = (viewholder) view.getTag();
                                } else {
                                    view = View.inflate(AppManagerActivity.this, R.layout.item_appmanager_apps, null);
                                    holder = new viewholder();
                                    holder.appname = (TextView) view.findViewById(R.id.tv_appname);
                                    holder.icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                                    holder.isrow = (TextView) view.findViewById(R.id.tv_isrow);
                                    holder.appsize = (TextView) view.findViewById(R.id.tv_appsize);
                                    view.setTag(holder);
                                }


                                holder.appname.setText(appInfo.getAppname());
                                holder.icon.setImageDrawable(appInfo.getAppicon());
                                boolean inRom = appInfo.isInRom();
                                if (inRom) {
                                    holder.isrow.setText("手机内存");
                                } else {
                                    holder.isrow.setText("内存卡");
                                }
                                holder.appsize.setText(android.text.format.Formatter.formatFileSize(AppManagerActivity.this, appInfo.getSize()));
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
                        //模拟浮动标识符
                        lvapplist.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                                switch (scrollState) {
                                    case SCROLL_STATE_TOUCH_SCROLL:
                                        dismisPopupWindow();
                                        break;
                                }
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                if (firstVisibleItem <= userappinfos.size()) {
                                    apptype.setText("用户程序: " + userappinfos.size() + "个");
                                } else {
                                    apptype.setText("系统程序: " + sysappinfos.size() + "个");
                                }
                            }
                        });
                        //点击显示悬浮窗体
                        lvapplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //分类显示用户与系统程序
                                if (position == 0) {
                                    return ;
                                } else if (position == userappinfos.size() + 1) {
                                    return ;
                                } else if (position <= userappinfos.size()) {
                                    position--;
                                } else {
                                    position -= 2;
                                }
                                //判断是用户还是系统程序
                                AppInfo appInfo;
                                if (position < userappinfos.size()) {
                                    appInfo = userappinfos.get(position);
                                } else {
                                    appInfo = sysappinfos.get(position - userappinfos.size());
                                }
                                View view1 = View.inflate(AppManagerActivity.this,R.layout.item_appmanager_popup,null);
                                //添加动画
                                AlphaAnimation aa = new AlphaAnimation(0.0f,1.0f);
                                aa.setDuration(300);
                                ScaleAnimation sa =new ScaleAnimation(0.5f,1.0f,0.5f,1.0f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.5f);
                                sa.setDuration(300);
                                AnimationSet set = new AnimationSet(false);
                                set.addAnimation(aa);
                                set.addAnimation(sa);
                                view1.setAnimation(set);
                                //保证只有一个悬浮窗体
                                dismisPopupWindow();
                                //创建对象
                                popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                //设置背景保证可以播放动画
                                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                int[] location = new int[2];
                                view.getLocationInWindow(location);
                                popupWindow.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 60, location[1]);
                            }
                        });
                    }

                });

                super.run();
            }
        }.start();

    }

    private void dismisPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }


    class viewholder {
        public TextView appname;
        public TextView isrow;
        public TextView appsize;
        public ImageView icon;
    }

}
