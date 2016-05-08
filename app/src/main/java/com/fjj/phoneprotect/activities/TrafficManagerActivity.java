package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import com.fjj.phoneprotect.R;

public class TrafficManagerActivity extends Activity {

    TextView info1;
    TextView info2;
    TextView info3;
    TextView info4;
    TextView info5;
    TextView info6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_manager);
        info1 = (TextView) findViewById(R.id.tv_traffic_info1);
        info2 = (TextView) findViewById(R.id.tv_traffic_info2);
        info3 = (TextView) findViewById(R.id.tv_traffic_info3);
        info4 = (TextView) findViewById(R.id.tv_traffic_info4);
        info5 = (TextView) findViewById(R.id.tv_traffic_info5);
        info6 = (TextView) findViewById(R.id.tv_traffic_info6);

        long mobilerx = TrafficStats.getMobileRxBytes(); //获取手机(2g/3g/4g)下载的数据信息单位是byte
        long mobiletx = TrafficStats.getMobileTxBytes(); //获取手机 2g/3g/4g上传的数据信息

        long totalrx = TrafficStats.getTotalRxBytes();//获取全部端口下载的流量数据. 包括wifi和 2g/3g/4g的流量
        long totaltx = TrafficStats.getTotalTxBytes();//获取全部端口上传的流量数据. 包括wifi和 2g/3g/4g的流量

        long browserrx = TrafficStats.getUidRxBytes(10004);
        long browsertx =TrafficStats.getUidTxBytes(10004);

        info1.setText("手机上传:" + Formatter.formatFileSize(this, mobiletx));
        info2.setText("手机下载:"+Formatter.formatFileSize(this, mobilerx));
        info3.setText("wifi上传:"+Formatter.formatFileSize(this, totaltx - mobiletx));
        info4.setText("wifi下载:"+Formatter.formatFileSize(this, totalrx- mobilerx));
        info5.setText("浏览器上传:"+Formatter.formatFileSize(this, browsertx));
        info6.setText("浏览器下载:"+Formatter.formatFileSize(this, browserrx));
    }
}
