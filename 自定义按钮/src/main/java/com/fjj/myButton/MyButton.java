package com.fjj.myButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by favaj on 2016/5/16.
 */
public class MyButton extends View {

    private static final String TAG = "MyButton";
    //底层图片
    private Bitmap backgroundmap;
    //上层图片
    private Bitmap slide;
    //画笔
    private Paint paint;
    //上层图片左移长度
    private int slidedestence;
    //最大左移长度
    private int maxdes;
    //开关状态
    private boolean isopen;
    private float startx;
    private boolean isclick;


    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        paint = new Paint();

        isclick = true;
        //设置开关变量
        isopen = false;
        //设置偏移大小
        slidedestence = 0;

        // 抗锯齿
        paint.setAntiAlias(true);

        backgroundmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slide = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        //设置最大偏移
        maxdes = backgroundmap.getWidth() - slide.getWidth();
        //设置触摸监听事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //按下
                        isclick = true;
                        startx = event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE: //移动
                        float rawX = event.getRawX();
                        float indexx = rawX - startx + slidedestence;
                        if (indexx > 10) {
                            isclick = false;
                        }
                        if (indexx < 0) {
                            slidedestence = 0;
                        } else if (indexx > maxdes) {
                            slidedestence = maxdes;
                        } else {
                            slidedestence = (int) indexx;
                        }
                        invalidate();
                        startx = rawX;
                        break;
                    case MotionEvent.ACTION_UP: //抬起
                        if (isclick) {
                            isopen = !isopen;
                            if (isopen) {
                                slidedestence = maxdes;
                            } else {
                                slidedestence = 0;
                            }

                        } else {

                            if (slidedestence > maxdes / 2) {
                                isopen = true;
                                slidedestence = maxdes;
                            } else {
                                isopen = false;
                                slidedestence = 0;
                            }
                        }
                        // 那个方法会导致重新绘制

                        invalidate();
                        break;
                }
                return true;
            }
        });

    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundmap, 0, 0, paint);
        canvas.drawBitmap(slide, slidedestence, 0, paint);

    }

    //指定位置和大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundmap.getWidth(), backgroundmap.getHeight());
    }
}
