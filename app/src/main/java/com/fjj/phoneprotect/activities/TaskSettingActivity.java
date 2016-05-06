package com.fjj.phoneprotect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.services.AutoKillProcessService;
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.ServiceStautsUtils;

public class TaskSettingActivity extends AppCompatActivity {

    SettingCheckedView scvshowsys;
    SettingCheckedView autokillprc;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_setting);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        autokillprc = (SettingCheckedView) findViewById(R.id.scv_autokillprc);
        //锁屏自动清理进程
        autokillprc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AutoKillProcessService.class);
                if (autokillprc.isChecked()) {
                    autokillprc.setChecked(false);
                    stopService(intent);
                } else {
                    autokillprc.setChecked(true);
                    startService(intent);
                }
            }
        });
        //是否显示进程
        scvshowsys = (SettingCheckedView) findViewById(R.id.scv_showsysprc);
        scvshowsys.setChecked(sp.getBoolean("showsys", true));
        scvshowsys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                if (scvshowsys.isChecked()) {
                    scvshowsys.setChecked(false);
                    edit.putBoolean("showsys", false);
                } else {
                    scvshowsys.setChecked(true);
                    edit.putBoolean("showsys", true);
                }
                edit.commit();
            }
        });
    }

    //判断服务是否启动设置checkbox状态
    @Override
    protected void onStart() {
        super.onStart();
        autokillprc.setChecked(ServiceStautsUtils.isServiceRunning(this, "com.fjj.phoneprotect.services.AutoKillProcessService"));
    }
}
