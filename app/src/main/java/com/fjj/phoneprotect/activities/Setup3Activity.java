package com.fjj.phoneprotect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.ToastUtils;

public class Setup3Activity extends SetupBaseActivity {

    EditText etphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        etphone = (EditText) findViewById(R.id.et_setup3_phone);
        String safephone = config.getString("safephone", null);
        if (!TextUtils.isEmpty(safephone)){
            etphone.setText(safephone);
        }
    }

    @Override
    public void shownext() {
        String phone = etphone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.show(this,"安全号码不能为空");
            return;
        }else{
            SharedPreferences.Editor edit = config.edit();
            edit.putString("safephone",phone);
            edit.commit();
        }
        IntentUtils.startActivityAndFinish(Setup3Activity.this, Setup4Activity.class);
    }

    @Override
    public void showprev() {
        IntentUtils.startActivityAndFinish(Setup3Activity.this, Setup2Activity.class);
    }

    public void findContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivityForResult(intent, 0);
    }

    //获取界面返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String phone = data.getStringExtra("phone").split(":")[1].replace(" ", "").replace("-", "");
            etphone.setText(phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
