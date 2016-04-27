package com.fjj.phoneprotect.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.fjj.phoneprotect.db.dao.BlackNumberDao;
import com.fjj.phoneprotect.db.dao.NumberAddressDao;
import com.fjj.phoneprotect.utils.ToastUtils;

import java.lang.reflect.Method;

//import com.android.internal.telephony.*;

public class NumberAddressService extends Service {
    private static final String TAG = "CallSmsSafeService";
    /**
     * 电话呼叫状态变化的监听器
     */
    private MyPhoneListener listener;
    //电话管理的服务.
    private TelephonyManager tm;

    CallPhoneReceive receive;

    public NumberAddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "服务被创建了");
        //得到电话管理的服务
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //注册电话呼叫状态的监听器.
        listener = new MyPhoneListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        //注册广播接受者
        receive = new CallPhoneReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receive, intentFilter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "服务被销毁了");
        //注销电话服务
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
        //注销广播接受者
        unregisterReceiver(receive);
        receive = null;
        super.onDestroy();
    }

    private class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲状态

                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态, 有人给你打电话了.
                    String address = NumberAddressDao.getAddress(incomingNumber);
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接通电话

                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }

    //注册拨打电话广播接受者
    public class CallPhoneReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String phone = getResultData();
            String address = NumberAddressDao.getAddress(phone);
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
        }
    }
}
