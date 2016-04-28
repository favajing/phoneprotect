package com.fjj.phoneprotect.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.fjj.phoneprotect.R;
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
    private WindowManager wm;
    private View view;
    private float startx;
    private float starty;
    private WindowManager.LayoutParams params;

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

    //定义电话监听类
    private class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲状态
                    if (wm != null) {

                        wm.removeView(view);
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃状态, 有人给你打电话了.
                    String address = NumberAddressDao.getAddress(incomingNumber);
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                    showMyToast(address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接通电话

                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }

    //注册拨打电话广播接受者
    public class CallPhoneReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String phone = getResultData();
            String address = NumberAddressDao.getAddress(phone);
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
            showMyToast(address);
        }
    }

    //自定义吐司
    private void showMyToast(String address) {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //获取用户的设置
        final SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        int sh = sp.getInt("which", 0);
        int[] bgs = {R.drawable.call_locate_white, R.drawable.call_locate_orange, R.drawable.call_locate_blue,
                R.drawable.call_locate_gray, R.drawable.call_locate_green};
        view = View.inflate(this, R.layout.toast_numberaddress_layout, null);
        //拖动效果
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        startx = event.getRawX();
                        starty = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        float dx = event.getRawX() - startx;
                        float dy = event.getRawY() - starty;
                        params.x += dx;
                        params.y += dy;
                        wm.updateViewLayout(view, params);
                        startx = event.getRawX();
                        starty = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP://抬起
                        //记录位置
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putInt("x", params.x);
                        edit.putInt("y", params.y);
                        edit.commit();
                        break;
                }
                return true;
            }
        });
        //设置背景
        view.setBackgroundResource(bgs[sh]);
        TextView viewById = (TextView) view.findViewById(R.id.tv_toast_address);
        viewById.setText(address);
        params = new WindowManager.LayoutParams();
        //初始化位置
        params.x = sp.getInt("x", 0);
        params.y = sp.getInt("y", 0);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE; //实现拖动需要这个模板,且需要添加SYSTEM_ALERT_WINDOW权限
        wm.addView(view, params);
    }
}
