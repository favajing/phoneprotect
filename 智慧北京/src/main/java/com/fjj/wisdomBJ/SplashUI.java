package com.fjj.wisdomBJ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.fjj.wisdomBJ.utils.CacheUtils;
import com.fjj.wisdomBJ.utils.IntentUtils;

/**
 * @包名: com.fjj.wisdomBJ
 * @类名: SplashUI
 * @创建者: 范晶晶
 * @创建时间: 2016/5/17 16:18
 * @描述 欢迎界面
 */
public class SplashUI extends Activity
{

    public static final java.lang.String KEY_FIRST_START = "firststart";
    private static final int              duration        = 2000;
    private RelativeLayout rlbg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlbg = (RelativeLayout) findViewById(R.id.rl_splash_background);

        initView();
    }

    private void initView()
    {
        //旋转
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(duration);
        ra.setFillAfter(true);
        //缩放
        ScaleAnimation sa = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(duration);
        sa.setFillAfter(true);
        //透明度
        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(duration);
        aa.setFillAfter(true);

        AnimationSet as = new AnimationSet(false);
        as.addAnimation(ra);
        as.addAnimation(sa);
        as.addAnimation(aa);
        as.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                boolean firstview = CacheUtils.getBoolean(getApplicationContext(), KEY_FIRST_START, false);
                if (!firstview) {
                    //显示引导页面
                    IntentUtils.startActivityAndFinish(SplashUI.this, GuideActivity.class);
                } else {
                    IntentUtils.startActivityAndFinish(SplashUI.this, MainActivity.class);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        rlbg.startAnimation(as);
    }
}
