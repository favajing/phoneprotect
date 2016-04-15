package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.AppInfoUtils;
import com.fjj.phoneprotect.utils.StreamUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends Activity {

    TextView tvverson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Splash界面
        setContentView(R.layout.activity_main);
        tvverson = (TextView) findViewById(R.id.tv_splash_version);
        //获取版本号
        tvverson.setText(AppInfoUtils.getVersionName(this));
        //初始化logger
        Logger.init("splash");
        checkVersion();


    }

    /**
     * 检查版本
     */
    void checkVersion() {
//        AsyncHttpClient插件
        AsyncHttpClient shc = new AsyncHttpClient();
        String url = getString(R.string.checkurl);
        shc.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Logger.i("res:" + new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Logger.i("res:" + throwable.getMessage());
            }
        });

//创建url
        /*new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(getString(R.string.checkurl));
                    //链接
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    //设置方法
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);
                    //获取链接
                    int code = con.getResponseCode();
                    if (code == 200) {
                        InputStream input = con.getInputStream();
                        String res = StreamUtils.readStreamToString(input);
                        Logger.i("res:" + res);
                        if (res == null) {
                            Logger.e("解析失败");
                        } else {
                            JSONObject json = new JSONObject(res);
                            int versioncode = json.getInt("version");
                            String downurl = json.getString("downloadurl");
                            String desc = json.getString("desc");
                            Logger.i("version:" + versioncode);
                            Logger.i("downurl:" + downurl);
                            Logger.i("desc:" + desc);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("网络异常");
                }
                super.run();
            }
        }.start();*/
    }
}
