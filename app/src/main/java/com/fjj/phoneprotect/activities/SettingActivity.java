package com.fjj.phoneprotect.activities;

import android.app.Activity;
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
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.ServiceStautsUtils;

public class SettingActivity extends Activity {

//    RelativeLayout rlclick;
//    CheckBox cbstate;
    SettingCheckedView scvupdate;
    SettingCheckedView scvblacknumber;
    SettingCheckedView scvnumberaddress;
    private SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        scvblacknumber = (SettingCheckedView) findViewById(R.id.scv_blacknumber);
        scvupdate = (SettingCheckedView) findViewById(R.id.scv_update);
        scvnumberaddress = (SettingCheckedView) findViewById(R.id.scv_numberaddress);
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
    }

    @Override
    protected void onStart() {
        //读取黑名单开启状态
        boolean blackstate = ServiceStautsUtils.isServiceRunning(this,"com.fjj.phoneprotect.services.CallSmsSafeService");
        scvblacknumber.setChecked(blackstate);

        boolean numberaddressstate = ServiceStautsUtils.isServiceRunning(this,"com.fjj.phoneprotect.services.NumberAddressService");
        scvnumberaddress.setChecked(numberaddressstate);
        super.onStart();
    }
}
