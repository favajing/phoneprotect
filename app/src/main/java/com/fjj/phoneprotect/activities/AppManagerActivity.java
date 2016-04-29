package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fjj.phoneprotect.R;

import java.io.File;
import java.util.Formatter;

public class AppManagerActivity extends Activity {

    TextView androidunused;
    TextView sdunused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        androidunused = (TextView) findViewById(R.id.tv_appmanager_androidunused);
        sdunused = (TextView) findViewById(R.id.tv_appmanager_sdunused);
        File androidfile = Environment.getDataDirectory();
        long freeSpace = androidfile.getFreeSpace();
        androidunused.setText("内存可用:" + android.text.format.Formatter.formatFileSize(this, freeSpace));
        File sdfile = Environment.getExternalStorageDirectory();
        sdunused.setText("SD卡可用:" + android.text.format.Formatter.formatFileSize(this,sdfile.getFreeSpace()));
    }
}
