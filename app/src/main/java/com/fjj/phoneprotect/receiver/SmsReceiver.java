package com.fjj.phoneprotect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.fjj.phoneprotect.R;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for(Object obj:objs){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();
            if("#*location*#".equals(body)){
                Log.i(TAG, "返回手机的位置信息");
                abortBroadcast();
            }else if("#*alarm*#".equals(body)){
                Log.i(TAG,"播放报警音乐");
                //多媒体音量跳到最大
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 1);
                MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
                //player.setLooping(true);//循环播放
                player.setVolume(1.0f, 1.0f);
                player.start();
                abortBroadcast();
            }else if("#*delete*#".equals(body)){
                Log.i(TAG,"远程销毁数据");
                abortBroadcast();
            }else if("#*lock*#".equals(body)){
                Log.i(TAG,"远程锁屏");
                abortBroadcast();
            }
        }
    }
}
