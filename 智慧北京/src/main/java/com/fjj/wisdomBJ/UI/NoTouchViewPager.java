package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.UI
 * @创建者: 范晶晶
 * @创建时间: 2016/5/19 16:43
 * @描述 禁用滑动的ViewPage
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class NoTouchViewPager extends ViewPager
{
    public NoTouchViewPager(Context context)
    {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return false;
    }
}
