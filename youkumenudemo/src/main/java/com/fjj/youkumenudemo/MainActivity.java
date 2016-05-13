package com.fjj.youkumenudemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    RelativeLayout rllevel1;
    RelativeLayout rllevel2;
    RelativeLayout rllevel3;
    ImageView ivhome;
    ImageView ivmenu;
    boolean showrllevel2;
    boolean showrllevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        setAnim();
    }

    private void setAnim() {
        ivmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showrllevel3) {
                    hideView(rllevel3);
                    showrllevel3 = false;
                } else {
                    showView(rllevel3);
                    showrllevel3 = true;
                }
            }
        });

        ivhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showrllevel2) {
//隐藏动画
                    hideView(rllevel2);
                    showrllevel2 = false;

                } else {
//显示动画
                    showView(rllevel2);
                    showrllevel2 = true;
                }
            }
        });
    }

    private void hideView(final View view) {
        RotateAnimation ra = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        ra.setDuration(500);
        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (view.getId() == R.id.rl_level2){
                    if (showrllevel3){
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideView(rllevel3);
                                        showrllevel3 = false;
                                    }
                                });
                            }
                        }.start();
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(ra);
    }

    private void showView(View view) {
        view.setVisibility(View.VISIBLE);
        RotateAnimation ra = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        ra.setDuration(500);
        view.startAnimation(ra);
    }

    private void initView() {
        rllevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
        rllevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        rllevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
        ivhome = (ImageView) findViewById(R.id.icon_home);
        ivmenu = (ImageView) findViewById(R.id.icon_menu);
    }
}
