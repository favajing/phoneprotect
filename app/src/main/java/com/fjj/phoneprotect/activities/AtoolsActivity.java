package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.engine.SmsTools;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.ToastUtils;

public class AtoolsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
    }

    public void search(View view) {
        IntentUtils.startActivity(this, SearchNumberAddressActivity.class);
    }

    public void commonphone(View view) {
        IntentUtils.startActivity(this, CommonNumberActivity.class);
    }

    public void returnsms(View view) {

    }

    /**
     * 备份短信
     *
     * @param view
     */
    public void backupsms(View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        //设置进度样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("备份中...");
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                boolean res = SmsTools.backUpSms(AtoolsActivity.this, "backsms.xml", new SmsTools.IBackUpSms() {
                    @Override
                    public void beforeBack(int totalcount) {
                        dialog.setMax(totalcount);
                    }

                    @Override
                    public void onBack(int currentprogress) {
                        dialog.setProgress(currentprogress);
                    }
                });
                if (res) {
                    ToastUtils.show(AtoolsActivity.this, "备份成功!");
                } else {
                    ToastUtils.show(AtoolsActivity.this, "备份失败!");
                }
                dialog.dismiss();
                super.run();
            }
        }.start();
    }
    public void lockapp(View view) {
        IntentUtils.startActivity(this,AppLockActivity.class);
    }
}
