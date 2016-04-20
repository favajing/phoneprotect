package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.fjj.phoneprotect.R;

/**
 * Created by favaj on 2016/4/19.
 * 提取setup公共部分
 */
public abstract class SetupBaseActivity extends Activity {

    protected SharedPreferences config;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = getSharedPreferences("config", MODE_PRIVATE);
        detector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            //滑行 滑翔 手指在屏幕上滑动
            //e1 开始, e2 结束 手指的事件
            //velocityX Y 水平和竖直方向的速度  px/s
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //屏蔽上下滑动
                if (Math.abs(e2.getRawY() - e1.getRawY()) > 100){
                    return  false;
                }

                if (e2.getRawX() - e1.getRawX() > 50){
                    showprev();
                    overridePendingTransition(R.anim.tran_prev_in, R.anim.tran_prev_out);
                    return true;
                }
                if (e1.getRawX() - e2.getRawX() > 50){
                    shownext();
                    overridePendingTransition(R.anim.tran_next_in,R.anim.tran_next_out);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public abstract void shownext();
    public abstract void showprev();

    public void next(View view){
        shownext();
        overridePendingTransition(R.anim.tran_next_in,R.anim.tran_next_out);
    }
    public void prev(View view){
        showprev();
        overridePendingTransition(R.anim.tran_prev_in, R.anim.tran_prev_out);
    }
}
