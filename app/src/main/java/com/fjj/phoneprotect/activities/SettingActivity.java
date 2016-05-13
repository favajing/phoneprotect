package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.services.CallSmsSafeService;
import com.fjj.phoneprotect.services.NumberAddressService;
import com.fjj.phoneprotect.services.WatchDogService;
import com.fjj.phoneprotect.ui.SettingChangeView;
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.ServiceStautsUtils;

public class SettingActivity extends Activity {

//    RelativeLayout rlclick;
//    CheckBox cbstate;
    /**
     * 自动更新
     */
    SettingCheckedView scvupdate;
    /**
     * 黑名单拦截
     */
    SettingCheckedView scvblacknumber;
    /**
     * 归属地查询
     */
    SettingCheckedView scvnumberaddress;

    SettingCheckedView scvwatchdog;
    /**
     * 提示框风格
     */
    SettingChangeView scstyle;
    private SharedPreferences sf;
    private static final String items[] = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        scvblacknumber = (SettingCheckedView) findViewById(R.id.scv_blacknumber);
        scvupdate = (SettingCheckedView) findViewById(R.id.scv_update);
        scvnumberaddress = (SettingCheckedView) findViewById(R.id.scv_numberaddress);
        scstyle = (SettingChangeView) findViewById(R.id.sc_style);
        scstyle.setDesc(items[sp.getInt("which", 0)]);
        scstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("归属地提示框风格");

                builder.setSingleChoiceItems(items, sp.getInt("which", 0), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("which", which);
                        editor.commit();
                        dialog.dismiss();
                        scstyle.setDesc(items[which]);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        throw  new NullPointerException("");
                    }
                });
                builder.show();
            }
        });
        //获取共享参数
        sf = getSharedPreferences("config", BIND_AUTO_CREATE);
        //读取配置设置checkbox状态
        boolean state = sf.getBoolean("update", false);

        scvupdate.setChecked(state);
        scvupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击改变update参数状态
                SharedPreferences.Editor editor = sf.edit();
                if (scvupdate.isChecked()) {
                    scvupdate.setChecked(false);
                    editor.putBoolean("update", false);
                } else {
                    scvupdate.setChecked(true);
                    editor.putBoolean("update", true);
                }
                editor.commit();
            }
        });

        scvwatchdog = (SettingCheckedView) findViewById(R.id.scv_watchdog);
        scvblacknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CallSmsSafeService.class);
                if (scvblacknumber.isChecked()) {
                    stopService(intent);
                    scvblacknumber.setChecked(false);
                } else {
                    startService(intent);
                    scvblacknumber.setChecked(true);
                }
            }
        });

        scvnumberaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, NumberAddressService.class);
                if (scvnumberaddress.isChecked()) {
                    stopService(intent);
                    scvnumberaddress.setChecked(false);
                } else {
                    startService(intent);
                    scvnumberaddress.setChecked(true);
                }
            }
        });

        scvwatchdog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, WatchDogService.class);
                if (scvwatchdog.isChecked()) {
                    stopService(intent);
                    scvwatchdog.setChecked(false);
                } else {
                    startService(intent);
                    scvwatchdog.setChecked(true);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        //读取黑名单开启状态
        boolean blackstate = ServiceStautsUtils.isServiceRunning(this, "com.fjj.phoneprotect.services.CallSmsSafeService");
        scvblacknumber.setChecked(blackstate);
//归属地状态
        boolean numberaddressstate = ServiceStautsUtils.isServiceRunning(this, "com.fjj.phoneprotect.services.NumberAddressService");
        scvnumberaddress.setChecked(numberaddressstate);
        boolean watchdogstate = ServiceStautsUtils.isServiceRunning(this, "com.fjj.phoneprotect.services.WatchDogService");
        scvwatchdog.setChecked(watchdogstate);
        super.onStart();
    }
}
