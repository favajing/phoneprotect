package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.ui.SettingCheckedView;

public class SettingActivity extends Activity {

//    RelativeLayout rlclick;
//    CheckBox cbstate;
    SettingCheckedView scvupdate;
    private SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        rlclick = (RelativeLayout) findViewById(R.id.rl_setting_updateclick);
//        cbstate = (CheckBox) findViewById(R.id.cb_setting_updatestate);
        scvupdate = (SettingCheckedView) findViewById(R.id.scv_update);
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
                if (scvupdate.isChecked()){
                    scvupdate.setChecked(false);
                    editor.putBoolean("update",false);
                }else {
                    scvupdate.setChecked(true);
                    editor.putBoolean("update",true);
                }
                editor.commit();
            }
        });
//        cbstate.setChecked(state);
//        rlclick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击改变update参数状态
//                SharedPreferences.Editor editor = sf.edit();
//                if (cbstate.isChecked()){
//                    cbstate.setChecked(false);
//                    editor.putBoolean("update",false);
//                }else {
//                    cbstate.setChecked(true);
//                    editor.putBoolean("update",true);
//                }
//                editor.commit();
//            }
//        });
    }
}
