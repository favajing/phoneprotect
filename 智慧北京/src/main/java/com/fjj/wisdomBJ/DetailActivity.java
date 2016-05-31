package com.fjj.wisdomBJ;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailActivity extends Activity
{
    
    private static final String TAG = "DetailActivity";
    @ViewInject(R.id.wv_detail)
    private WebView wv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initData();
    }
    
    private void initData()
    {
        //注入
        ViewUtils.inject(this);
        
        Intent intent = getIntent();
        if (intent != null)
        {
            String newsurl = intent.getStringExtra("newsurl");
            
            // 页面显示
            wv.loadUrl(newsurl);
            // mWebView.loadUrl("http://www.itheima.com");
            // WebView的设置

            WebSettings settings = wv.getSettings();
            settings.setJavaScriptEnabled(true); // 设置js可见
            settings.setBuiltInZoomControls(true);// 设置放大缩小的控件
            settings.setUseWideViewPort(true);// 双击缩放
//            settings.setTextSize(WebSettings.TextSize.LARGER);//设置字体
        }

        // 监听
        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.d(TAG, "页面开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.d(TAG, "页面结束加载");
            }
        });

        wv.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                Log.d(TAG, "进度 ：" + newProgress);
            }
        });
    }
    
    public void back(View view)
    {
        finish();
    }
}
