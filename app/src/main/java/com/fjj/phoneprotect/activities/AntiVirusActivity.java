package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.dao.AntiVirusDao;
import com.fjj.phoneprotect.utils.Md5Utils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AntiVirusActivity extends Activity {

    private static final String TAG = "AntiVirusActivity";
    ImageView ivcenter;
    LinearLayout llapps;
    ProgressBar pbsearching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_virus);

        ivcenter = (ImageView) findViewById(R.id.iv_antivirus_centerimg);
        llapps = (LinearLayout) findViewById(R.id.ll_apps);
        pbsearching = (ProgressBar) findViewById(R.id.pb_searching);

        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置动画效果为匀速
        ra.setInterpolator(new LinearInterpolator());
        ra.setDuration(2000);
        ra.setRepeatCount(-1);
        ivcenter.startAnimation(ra);
        working();
    }

    private void working() {
        new Thread() {
            @Override
            public void run() {
                // 遍历手机里面的每一个应用程序信息. 查询他的特征码在病毒数据库是否存在.
                PackageManager pm = getPackageManager();
                List<PackageInfo> packinfos = pm
                        .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES + PackageManager.GET_SIGNATURES);
                pbsearching.setMax(packinfos.size());
                pbsearching.setProgress(0);
                for (PackageInfo packinfo : packinfos) {
                    try {
                        String apkpath = packinfo.applicationInfo.sourceDir;
                        //获取应用程序签名
//                        System.out.println("程序名:"+packinfo.applicationInfo.loadLabel(pm));
//                        System.out.println("签名:"+ Md5Utils.encode(packinfo.signatures[0].toCharsString()));
                        File file = new File(apkpath);
                        MessageDigest digest = MessageDigest.getInstance("md5");
                        FileInputStream fis = new FileInputStream(file);
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = fis.read(buffer)) != -1) {
                            digest.update(buffer, 0, len);
                        }
                        byte[] result = digest.digest();
                        StringBuffer sb = new StringBuffer();
                        for (byte b : result) {
                            String str = Integer.toHexString(b & 0xff);
                            if (str.length() == 1) {
                                sb.append("0");
                            }
                            sb.append(str);
                        }
                        String md5 = sb.toString();
//                        Log.i(TAG, packinfo.applicationInfo.loadLabel(pm) + ":" + md5);
                        // 检查md5的特征码在病毒数据库里面是否存在.
                        final String info = AntiVirusDao.isVirus(md5);
                        final String appname = packinfo.applicationInfo
                                .loadLabel(pm).toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv = new TextView(AntiVirusActivity.this);
                                if (info == null){
                                    tv.setText(appname + ":正常");
                                    tv.setTextColor(Color.GREEN);
                                }else{
                                    tv.setText(appname + ":" + info);
                                    tv.setTextColor(Color.RED);
                                }
                                llapps.addView(tv, 0);
                                int progress = pbsearching.getProgress()+1;
                                pbsearching.setProgress(progress);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
