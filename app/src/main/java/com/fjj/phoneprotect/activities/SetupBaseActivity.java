package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
/**
 * Created by favaj on 2016/4/19.
 * 提取setup公共部分
 */
public abstract class SetupBaseActivity extends Activity {

    protected SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = getSharedPreferences("config", MODE_PRIVATE);
    }

    public abstract void shownext();
    public abstract void showprev();

    public void next(View view){
        shownext();
    }
    public void prev(View view){
        showprev();
    }
}
