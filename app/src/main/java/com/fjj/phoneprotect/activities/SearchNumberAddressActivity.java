package com.fjj.phoneprotect.activities;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.dao.NumberAddressDao;
import com.fjj.phoneprotect.utils.ToastUtils;

public class SearchNumberAddressActivity extends AppCompatActivity {

    TextView tvresult;
    EditText etphone;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_number_address);
        tvresult = (TextView) findViewById(R.id.tv_search_result);
        etphone = (EditText) findViewById(R.id.et_search_phone);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    public void searchphone(View view) {
        String number = etphone.getText().toString().trim();
        if(TextUtils.isEmpty(number)){
            ToastUtils.show(this, "号码不能为空");
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etphone.startAnimation(shake);
            vibrator.vibrate(100);
            return;
        }
        String address = NumberAddressDao.getAddress(number);
        tvresult.setText("归属地:"+address);
    }
}
