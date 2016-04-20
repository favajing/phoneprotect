package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Conference;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;

public class LostFindActivity extends Activity {

    TextView tvphone;
    CheckBox cksafestate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        tvphone = (TextView) findViewById(R.id.tv_lost_phone);
        cksafestate = (CheckBox) findViewById(R.id.tv_lost_safestate);
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        String safephone = config.getString("safephone", null);
        boolean safestate = config.getBoolean("safestate", false);
        tvphone.setText(safephone);
        cksafestate.setChecked(safestate);

    }

    public void xiangdao(View view) {
        IntentUtils.startActivityAndFinish(LostFindActivity.this, Setup1Activity.class);
    }
}
