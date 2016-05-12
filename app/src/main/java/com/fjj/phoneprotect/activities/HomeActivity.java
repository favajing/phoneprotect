package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.Md5Utils;
import com.fjj.phoneprotect.utils.ToastUtils;

public class HomeActivity extends Activity {

    GridView gvlist;
    protected String[] names = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计",
            "手机杀毒", "缓存清理", "高级工具", "设置中心"};
    int[] icons = {
            R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app_selector,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
            R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings
    };
    private SharedPreferences config;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private View view1;
    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        config = getSharedPreferences("config", MODE_PRIVATE);
        gvlist = (GridView) findViewById(R.id.gv_home_list);
        gvlist.setAdapter(new MyBaseAdapter());
        gvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //手机防盗
                        String password = config.getString("password", null);
                        if (TextUtils.isEmpty(password)) {
//第一次进入需要设置密码对话框
                            showSetPassWordDialog();
                        } else {
//设置过密码,输入密码对话框
                            showInputPassWordDialog(password);
                        }
                        break;
                    case 1:
                        IntentUtils.startActivity(HomeActivity.this, CallSmsSafeActivity.class);
                        break;
                    case 2:
                        IntentUtils.startActivity(HomeActivity.this, AppManagerActivity.class);
                        break;
                    case 3:
                        IntentUtils.startActivity(HomeActivity.this, TaskManagerActivity.class);
                        break;
                    case 4:
                        IntentUtils.startActivity(HomeActivity.this, TrafficManagerActivity.class);
                        break;
                    case 5:
                        IntentUtils.startActivity(HomeActivity.this, AntiVirusActivity.class);
                        break;
                    case 6:
                        IntentUtils.startActivity(HomeActivity.this, CleanCacheActivity.class);
                        break;
                    case 7:
                        IntentUtils.startActivity(HomeActivity.this, AtoolsActivity.class);
                        break;
                    case 8:
                        IntentUtils.startActivity(HomeActivity.this, SettingActivity.class);
                        break;
                }
            }


        });
    }

    /**
     * 输入密码对话框
     */
    private void showInputPassWordDialog(final String password) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("输入密码");
        view1 = View.inflate(this, R.layout.dialog_home_inputpassword, null);
        final EditText pwd = (EditText) view1.findViewById(R.id.et_dialoghome_pwd);
        yes = (Button) view1.findViewById(R.id.bt_dialoghome_yes);
        no = (Button) view1.findViewById(R.id.bt_dialoghome_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pd = pwd.getText().toString();
                if (TextUtils.isEmpty(pd)) {
                    ToastUtils.show(HomeActivity.this, "密码不能为空");
                } else if (!Md5Utils.encode(pd).equals(password)) {
                    ToastUtils.show(HomeActivity.this, "密码错误");
                } else {

                    dialog.dismiss();
                    boolean finish = config.getBoolean("finishsetup", false);
                    if (finish) {
//完成设置进入手机防盗
                        IntentUtils.startActivity(HomeActivity.this, LostFindActivity.class);
                    } else {
                        //第一次进入设置向导
                        IntentUtils.startActivity(HomeActivity.this,Setup1Activity.class);
                    }
                }
            }
        });
        builder.setView(view1);
        dialog = builder.show();
    }

    /**
     * 设置密码对话框
     */
    private void showSetPassWordDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("设置密码");
        view1 = View.inflate(this, R.layout.dialog_home_setpassword, null);
        final EditText pwd = (EditText) view1.findViewById(R.id.et_dialoghome_pwd);
        final EditText pwd2 = (EditText) view1.findViewById(R.id.et_dialoghome_pwd2);
        yes = (Button) view1.findViewById(R.id.bt_dialoghome_yes);
        no = (Button) view1.findViewById(R.id.bt_dialoghome_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pd = pwd.getText().toString();
                String pd2 = pwd2.getText().toString();
                if (TextUtils.isEmpty(pd) || TextUtils.isEmpty(pd2)) {
                    ToastUtils.show(HomeActivity.this, "密码不能为空");
                } else if (!pd.equals(pd2)) {
                    ToastUtils.show(HomeActivity.this, "两次密码不一致");
                } else {
                    SharedPreferences.Editor edit = config.edit();
                    edit.putString("password", Md5Utils.encode(pd));
                    edit.commit();
                    ToastUtils.show(HomeActivity.this, "设置成功");
                    dialog.dismiss();
                    showInputPassWordDialog(Md5Utils.encode(pd));
                }
            }
        });
        builder.setView(view1);
        dialog = builder.show();
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
