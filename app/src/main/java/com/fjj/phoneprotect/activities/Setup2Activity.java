package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.ui.SettingCheckedView;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.ToastUtils;

public class Setup2Activity extends SetupBaseActivity {

    private TelephonyManager tm;
    SettingCheckedView sck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        //获取系统服务
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        sck = (SettingCheckedView) findViewById(R.id.sck_setup2_bind);
        //初始化
        String sim = config.getString("sim", null);
        sck.setChecked(!TextUtils.isEmpty(sim));
        sck.setOnClickListener(new View.OnClickListener() {
            private SharedPreferences.Editor edit;
            @Override
            public void onClick(View v) {
                if (sck.isChecked()){
                    //点击取消绑定
                    edit = config.edit();
                    edit.putString("sim", null);
                    sck.setChecked(false);
                }else{
                    //绑定后记录下来
                    String simOperatorName = tm.getSimSerialNumber();
                    //tm.getLine1Number();//sim卡芯片写的有电话才能获取到
                    edit = config.edit();
                    edit.putString("sim", simOperatorName);
                    sck.setChecked(true);
                }
                edit.commit();
            }
        });
    }

    @Override
    public void shownext() {
        if (sck.isChecked()){
            IntentUtils.startActivityAndFinish(Setup2Activity.this, Setup3Activity.class);
        }else{
            ToastUtils.show(this,"手机防盗必须绑定SIM卡!");
        }
    }

    @Override
    public void showprev() {
        IntentUtils.startActivityAndFinish(Setup2Activity.this, Setup1Activity.class);
    }
}
