package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.LostFindActivity;
import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;

public class Setup4Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
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
        IntentUtils.startActivityAndFinish(Setup4Activity.this,Setup4Activity.class);
    }
}
