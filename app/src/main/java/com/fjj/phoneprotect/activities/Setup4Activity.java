package com.fjj.phoneprotect.activities;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.receiver.MyAdmin;
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.IntentUtils;

public class Setup4Activity extends SetupBaseActivity {

    SettingCheckedView scksafebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        scksafebtn = (SettingCheckedView) findViewById(R.id.sck_setup4_safebtn);
        boolean safestate = config.getBoolean("safestate", false);
        scksafebtn.setChecked(safestate);
        scksafebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (scksafebtn.isChecked()){
                    SharedPreferences.Editor edit = config.edit();
                    edit.putBoolean("safestate",false);
                    edit.commit();
                    scksafebtn.setChecked(false);
                }else{
                    SharedPreferences.Editor edit = config.edit();
                    edit.putBoolean("safestate",true);
                    edit.commit();
                    scksafebtn.setChecked(true);
                }
            }
        });
    }

    @Override
    public void shownext() {
        SharedPreferences.Editor edit = config.edit();
        edit.putBoolean("finishsetup",true);
        edit.commit();
        IntentUtils.startActivityAndFinish(Setup4Activity.this, LostFindActivity.class);
    }

    @Override
    public void showprev() {
        IntentUtils.startActivityAndFinish(Setup4Activity.this,Setup3Activity.class);
    }

    public void setadmin(View view) {
        //一建锁屏，底层是广播，开启广播--意图，必须先激活权限
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName who = new ComponentName(this, MyAdmin.class);
        // 把要激活的组件名告诉系统
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "要求所有的员工都开启这个管理员权限,如果不开启,扣200块钱,赶紧开启吧,开启送大礼包");
        startActivity(intent);
    }
}
