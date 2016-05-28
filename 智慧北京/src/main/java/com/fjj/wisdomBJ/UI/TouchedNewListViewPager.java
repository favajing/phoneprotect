package com.fjj.wisdomBJ.UI;

import android.app.Notification;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.fjj.wisdomBJ.utils.LoggerUtils;

import java.util.logging.Logger;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.UI
 * @创建者: 范晶晶
 * @创建时间: 2016/5/24 11:15
 * @描述 拦截viewpager的父控件的touch事件
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class TouchedNewListViewPager extends ViewPager
{

    private static final String TAG = "TouchedNewListViewPager";
    private float mDownx;
    private float mDowny;

    public TouchedNewListViewPager(Context context)
    {
        super(context);
    }

    public TouchedNewListViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    //拦截父窗体的touch事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        int position = getCurrentItem();
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDownx = ev.getX();
                mDowny = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float movex = ev.getX();
                float diffx = movex - mDownx;

                float movey = ev.getY();
                float diffy = movey - mDowny;
                //y轴不拦截,以便于下拉刷新
                if (Math.abs(diffy) >  Math.abs(diffx) ){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {

                    if (position == 0)
                    {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else if (position == getAdapter().getCount() - 1)
                    {
                        //最后位置,右滑拦截
                        if (diffx > 0){
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }else { //最后位置,左滑不拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
