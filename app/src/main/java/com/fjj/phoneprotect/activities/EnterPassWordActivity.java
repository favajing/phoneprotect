package com.fjj.phoneprotect.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.ToastUtils;

public class EnterPassWordActivity extends AppCompatActivity {

    EditText etpwd;
    private String packagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass_word);
        etpwd = (EditText) findViewById(R.id.et_password);
        Intent intent = getIntent();
        packagename = intent.getStringExtra("packagename");
    }

    @Override
    public void onBackPressed() {
        //屏蔽返回键并打开桌面
        /*<action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.HOME" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.MONKEY"/>*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.MONKEY");
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        //隐藏后关闭窗口
        finish();
        super.onStop();
    }

    public void enterpassword(View view) {
        String pwd = etpwd.getText().toString();
        if (TextUtils.isEmpty(pwd) || !"123".equals(pwd)){
            ToastUtils.show(this,"密码错误!");
        }else{
            //正确后发送广播并传递数据
            Intent intent = new Intent();
            intent.setAction("com.fjj.phoneprotect.wangwang");
            intent.putExtra("packagename",packagename);
            sendBroadcast(intent);
            finish();
        }
    }
}
