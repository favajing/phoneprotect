package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.IntentUtils;

public class HomeActivity extends Activity {

    GridView gvlist;
    protected String[] names = {"手机防盗","通讯卫士","软件管理","进程管理","流量统计",
            "手机杀毒","缓存清理","高级工具","设置中心"};
    int[] icons={
            R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app_selector,
            R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gvlist = (GridView) findViewById(R.id.gv_home_list);
        gvlist.setAdapter(new MyBaseAdapter());
        gvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 8:
                        IntentUtils.startActivity(HomeActivity.this,SettingActivity.class);
                        break;
                }
            }
        });
    }

    private class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = View.inflate(getApplicationContext(), R.layout.item_home_activity, null);
            ImageView icon = (ImageView) v.findViewById(R.id.iv_item_icon);
            icon.setImageResource(icons[position]);
            TextView name = (TextView) v.findViewById(R.id.tv_item_name);
            name.setText(names[position]);
            return v;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
