package com.fjj.youkumenudemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
    boolean showrllevel1;
    boolean showrllevel2;
    boolean showrllevel3;
    //123123

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showrllevel1 = true;
        initView();

        setAnim();
    }

    //菜单键的显示与隐藏
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU){
            if (showrllevel1){
                long startoffet = 0;
                //隐藏所有菜单 1级 2级 3级
                hideView(rllevel1, 0);
                showrllevel1 = false;
                if (showrllevel2){
                    startoffet += 200;
                    hideView(rllevel2,startoffet);
                    showrllevel2 = false;
                    if (showrllevel3){
                        startoffet += 200;
                        hideView(rllevel3,startoffet);
                        showrllevel3 = false;
                    }
                }

            }else{
                //显示 1级  2级菜单
                showView(rllevel1,0);
                showrllevel1 = true;
                showView(rllevel2,200);
                showrllevel2 = true;
            }
        }
        return true;
    }

    private void setAnim() {
        //点击2级菜单显示/隐藏3级
        ivmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showrllevel3) {
                    hideView(rllevel3, 0);
                    showrllevel3 = false;
                } else {
                    showView(rllevel3, 0);
                    showrllevel3 = true;
                }
            }
        });
        //点击1级菜单显示/隐藏2级和3级
        ivhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showrllevel2) {
                    //隐藏动画
                    hideView(rllevel2, 0);
                    showrllevel2 = false;
                    if (showrllevel3){
                        hideView(rllevel3,200);
                        showrllevel3 = false;
                    }

                } else {
                    //显示动画
                    showView(rllevel2, 0);
                    showrllevel2 = true;
                }
            }
        });
    }

    private void hideView(final ViewGroup view, long startoffet) {
        //旋转动画
        RotateAnimation ra = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        ra.setDuration(500);
        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ra.setStartOffset(startoffet);
        view.startAnimation(ra);
    }

    private void showView(ViewGroup view, long startoffet) {
        //旋转动画
        view.setVisibility(View.VISIBLE);
        RotateAnimation ra = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
        ra.setDuration(500);
        //设置延迟执行时间(毫秒)
        ra.setStartOffset(startoffet);
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
