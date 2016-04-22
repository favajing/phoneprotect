package com.fjj.phoneprotect.receiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.orhanobut.logger.Logger;

public class BootReceiver extends BroadcastReceiver {

    private boolean issend;

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

//                String SENT = "sms_sent";
//
//                Intent sentIntent = new Intent(SENT);
//                PendingIntent sendIntent= PendingIntent.getBroadcast(context, 0, sentIntent,
//                        0);
//// register the Broadcast Receivers
//                context.registerReceiver(new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context _context, Intent _intent) {
//                        switch (getResultCode()) {
//                            case Activity.RESULT_OK:
//                                issend = true;
//                                break;
//                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                                break;
//                            case SmsManager.RESULT_ERROR_RADIO_OFF:
//                                break;
//                            case SmsManager.RESULT_ERROR_NULL_PDU:
//                                break;
//                        }
//                    }
//                }, new IntentFilter(SENT));
//
//                while (!issend){
//                    SmsManager.getDefault().sendTextMessage(safephone, null, "sim change", sendIntent, null);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                SmsManager.getDefault().sendTextMessage(safephone, null, "sim change", null, null);
            }
        }else {
            Logger.i("没有开启防盗保护!");
        }
    }
}
