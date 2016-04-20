package com.fjj.phoneprotect.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
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
}
