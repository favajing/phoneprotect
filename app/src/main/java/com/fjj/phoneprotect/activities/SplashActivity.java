package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.AppInfoUtils;
import com.fjj.phoneprotect.utils.IntentUtils;
import com.fjj.phoneprotect.utils.StreamUtils;
import com.fjj.phoneprotect.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
        //根据公共参数判断是否需要自动更新
        SharedPreferences sf = getSharedPreferences("config", MODE_PRIVATE);
        if (sf.getBoolean("update",false)){
            checkVersion();
        }else{
            IntentUtils.startActivity(SplashActivity.this,HomeActivity.class,2000);
        }
    }

    /**
     * 检查版本
     */
    void checkVersion() {
        //AsyncHttpClient插件
        /*AsyncHttpClient shc = new AsyncHttpClient();
        String url = getString(R.string.checkurl);
        shc.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Logger.i("res:" + new String(bytes));
                Log.d("splash", "res:" + new String(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Logger.i("res:" + throwable.getMessage());
            }
        });*/

//创建url
        new Thread() {
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
                        //读取文字信息
                        String res = StreamUtils.readStreamToString(input);
                        if (res == null) {
                            Logger.e("解析失败");
                        } else {
                            //解析json
                            JSONObject json = new JSONObject(res);
                            int versioncode = json.getInt("version");
                            final String downurl = json.getString("downloadurl");
                            final String desc = json.getString("desc");
                            //判断版本
                            if (versioncode > AppInfoUtils.getVersionCode(getApplicationContext())) {
                                //有新版本
                                //ToastUtils.show(SplashActivity.this, "有新版本");
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //弹出对话框
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startBuilder(desc, downurl);
                                    }
                                });
                            } else {
                                //无新版本,延迟2秒后进入主界面
                                IntentUtils.startActivity(SplashActivity.this, HomeActivity.class, 2000);
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    ToastUtils.show(SplashActivity.this, "配置错误");
                    IntentUtils.startActivity(SplashActivity.this, HomeActivity.class, 1000);
                    e.printStackTrace();
                } catch (JSONException e) {
                    ToastUtils.show(SplashActivity.this, "解析错误");
                    IntentUtils.startActivity(SplashActivity.this, HomeActivity.class, 1000);
                    e.printStackTrace();
                } catch (IOException e) {
                    ToastUtils.show(SplashActivity.this, "网络错误");
                    IntentUtils.startActivity(SplashActivity.this, HomeActivity.class, 1000);
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    //弹出升级对话框
    private void startBuilder(String desc, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("升级提示");
        // 设置描述信息
        builder.setMessage(desc);
        // 设置确定和取消按钮
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //断点下载
                //设置安装包保存路径
                final File downfile = new File(Environment.getExternalStorageDirectory(), "phoneprotect.apk");
                if (downfile.exists()) {
                    IntentUtils.startInsertActivity(SplashActivity.this, Uri.fromFile(downfile));
                } else {
                    HttpUtils http = new HttpUtils();
                    http.download(url, downfile.getAbsolutePath(), true, new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            ToastUtils.show(SplashActivity.this, "下载成功");
                            //安装apk
                        /*<intent-filter>
                        <action android:name="android.intent.action.VIEW" />
                        <category android:name="android.intent.category.DEFAULT" />
                        <data android:scheme="content" />
                        <data android:scheme="file" />
                        <data android:mimeType="application/vnd.android.package-archive" />
                        </intent-filter>*/
                            IntentUtils.startInsertActivity(SplashActivity.this, Uri.fromFile(downfile));
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            ToastUtils.show(SplashActivity.this, "下载失败");
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IntentUtils.startActivityAndFinish(SplashActivity.this, HomeActivity.class);
            }
        });
        builder.show();
    }
}
