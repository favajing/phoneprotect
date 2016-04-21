package com.fjj.phoneprotect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.init("boot");
        Logger.i("手机启动啦");
        SharedPreferences config = context.getSharedPreferences("config", context.MODE_PRIVATE);
        boolean safestate = config.getBoolean("safestate", false);
        if (safestate){//判断是否开启防盗状态
            //获取sim卡信息
            String sim = config.getString("sim", null) + "123";
            //获取保存sim卡信息
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String realsim = tm.getSimSerialNumber();
            if (!sim.equals(realsim)){
                //判断是否一致,不是则发送短信至安全号码
                String safephone = config.getString("safephone", null);
                SmsManager.getDefault().sendTextMessage(safephone, null, "sim change", null, null);
            }
        }else {
            Logger.i("没有开启防盗保护!");
        }
    }
}
