package com.fjj.phoneprotect;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.activities.Setup1Activity;
import com.fjj.phoneprotect.utils.IntentUtils;

public class LostFindActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
    }

    public void xiangdao(View view) {
        IntentUtils.startActivityAndFinish(LostFindActivity.this, Setup1Activity.class);
    }
}
