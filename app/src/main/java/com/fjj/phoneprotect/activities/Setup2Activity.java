package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;

public class Setup2Activity extends SetupBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
    }

    @Override
    public void shownext() {
        IntentUtils.startActivityAndFinish(Setup2Activity.this,Setup3Activity.class);
    }

    @Override
    public void showprev() {
        IntentUtils.startActivityAndFinish(Setup2Activity.this,Setup1Activity.class);
    }
}
