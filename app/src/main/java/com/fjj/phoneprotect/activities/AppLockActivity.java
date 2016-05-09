package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.dao.AppLockInfoDao;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.engine.AppAdmin;

import java.util.ArrayList;
import java.util.List;

public class AppLockActivity extends Activity {

    Button btnunlock;
    Button btnlock;
    TextView tvunlock;
    TextView tvlock;
    LinearLayout lllock;
    LinearLayout llunlock;
    ListView lvunlockapplist;
    ListView lvlockapplist;
    boolean showlockstate;
    private List<AppInfo> appInfos;
    private List<AppInfo> unlockappinfos;
    private List<AppInfo> lockappinfos;
    private AppLockInfoDao dao;
    private BaseAdapter unlockadapter;
    private BaseAdapter lockadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        tvunlock = (TextView) findViewById(R.id.tv_unlock);
        tvlock = (TextView) findViewById(R.id.tv_lock);
        btnunlock = (Button) findViewById(R.id.btn_unlock);
        btnlock = (Button) findViewById(R.id.btn_lock);
        lllock = (LinearLayout) findViewById(R.id.ll_lock);
        llunlock = (LinearLayout) findViewById(R.id.ll_unlock);
        lvunlockapplist = (ListView) findViewById(R.id.lv_unlockapplist);
        lvlockapplist = (ListView) findViewById(R.id.lv_lockapplist);

        showlockstate = false;
        dao = new AppLockInfoDao(this);
        initData();

    }

    private void initData() {
        if (appInfos == null) {
            appInfos = AppAdmin.findApp(this);
        }
        unlockappinfos = new ArrayList<>();
        lockappinfos = new ArrayList<>();
        for (AppInfo appInfo : appInfos) {
            if (dao.isLock(appInfo.getApppackage())) {
                lockappinfos.add(appInfo);
            } else {
                unlockappinfos.add(appInfo);
            }
        }

        unlockadapter = new BaseAdapter() {
            @Override
            public int getCount() {
                tvunlock.setText("未加锁程序:" + unlockappinfos.size());
                return unlockappinfos.size();
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view1;
                viewholder holder;
                if (convertView != null) {
                    view1 = convertView;
                    holder = (viewholder) view1.getTag();
                } else {
                    view1 = View.inflate(getApplicationContext(), R.layout.item_applock_unlock, null);
                    holder = new viewholder();
                    holder.icon = (ImageView) view1.findViewById(R.id.iv_item_icon);
                    holder.name = (TextView) view1.findViewById(R.id.tv_item_name);
                    holder.action = (ImageView) view1.findViewById(R.id.iv_item_action);
                    view1.setTag(holder);
                }

                AppInfo appInfo = unlockappinfos.get(position);
                holder.icon.setImageDrawable(appInfo.getAppicon());
                holder.name.setText(appInfo.getAppname());
                holder.action.setImageResource(R.drawable.list_button_lock_pressed);
                holder.action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppInfo info = unlockappinfos.get(position);
                        dao.add(info.getApppackage());
                        lockappinfos.add(info);
                        unlockappinfos.remove(info);
                        notifyDataSetChanged();
                        lockadapter.notifyDataSetChanged();
                    }
                });
                return view1;
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
        lvunlockapplist.setAdapter(unlockadapter);
    }

    public void unlock(View view) {
        if (showlockstate) {
            btnunlock.setBackgroundResource(R.drawable.tab_left_pressed);
            btnlock.setBackgroundResource(R.drawable.tab_right_default);
            llunlock.setVisibility(View.VISIBLE);
            lllock.setVisibility(View.GONE);
            showlockstate = false;
        }
    }

    public void lock(View view) {
        if (!showlockstate) {
            btnunlock.setBackgroundResource(R.drawable.tab_left_default);
            btnlock.setBackgroundResource(R.drawable.tab_right_pressed);
            llunlock.setVisibility(View.GONE);
            lllock.setVisibility(View.VISIBLE);
            if (lockadapter == null) {
                lockadapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        tvlock.setText("加锁程序:" + lockappinfos.size());
                        return lockappinfos.size();
                    }

                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        View view1;
                        viewholder holder;
                        if (convertView != null) {
                            view1 = convertView;
                            holder = (viewholder) view1.getTag();
                        } else {
                            view1 = View.inflate(getApplicationContext(), R.layout.item_applock_unlock, null);
                            holder = new viewholder();
                            holder.icon = (ImageView) view1.findViewById(R.id.iv_item_icon);
                            holder.name = (TextView) view1.findViewById(R.id.tv_item_name);
                            holder.action = (ImageView) view1.findViewById(R.id.iv_item_action);
                            view1.setTag(holder);
                        }

                        AppInfo appInfo = lockappinfos.get(position);
                        holder.icon.setImageDrawable(appInfo.getAppicon());
                        holder.name.setText(appInfo.getAppname());
                        holder.action.setImageResource(R.drawable.list_button_unlock_pressed);
                        holder.action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppInfo info = lockappinfos.get(position);
                                dao.delete(info.getApppackage());
                                unlockappinfos.add(info);
                                lockappinfos.remove(info);
                                notifyDataSetChanged();
                                unlockadapter.notifyDataSetChanged();
                            }
                        });
                        return view1;
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
                lvlockapplist.setAdapter(lockadapter);
            }
            showlockstate = true;
        }
    }
    class viewholder{
        public TextView name ;
        public ImageView icon;
        public ImageView action;
    }

}
