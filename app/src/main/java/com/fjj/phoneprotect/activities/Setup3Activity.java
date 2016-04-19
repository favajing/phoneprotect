package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;

public class Setup3Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    @Override
    public void shownext() {
        IntentUtils.startActivityAndFinish(Setup3Activity.this, Setup4Activity.class);
    }

    @Override
    public void showprev() {
        IntentUtils.startActivityAndFinish(Setup3Activity.this,Setup2Activity.class);
    }
}
