package com.fjj.phoneprotect.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

public class GPSService extends Service {
    private static final String TAG = "SmsReceiver";

    public GPSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //指定精确度
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //指定耗电量
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        String provider = lm.getBestProvider(criteria, true);
        Log.wtf(TAG, "最好的提供者:" + provider);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.wtf(TAG, "获取位置错误");
            return;
        }
        lm.requestLocationUpdates(provider, 0, 0, new LocationListener() {
            //当位置提供者状态变化调用的方法.
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            //当位置提供者可用的时候调用的方法.
            @Override
            public void onProviderEnabled(String provider) {

            }

            //当位置提供者不可用的时候调用的方法.
            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                location.getLongitude(); //经度
                location.getLatitude();  //纬度
                String text = ("J:" + location.getLongitude() + "W:" + location.getLatitude());
                Log.wtf(TAG, "location:" + text);
                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                Log.wtf(TAG, "safephone" + sp.getString("safephone", ""));
                SmsManager.getDefault().sendTextMessage(sp.getString("safephone", ""), null, text, null, null);
                Log.wtf(TAG, "send right");
                if (ActivityCompat.checkSelfPermission(GPSService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GPSService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.wtf(TAG, "获取位置错误");
                    return;
                }
                //在获取位置成功后停止获取动作
                lm.removeUpdates(this);
                //关闭服务
                stopSelf();
            }
        });

    }
}
