package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by favaj on 2016/5/22.
 * 拦截父控件的touch事件
 */
public class TouchedTabPageIndicator extends TabPageIndicator
{
    public TouchedTabPageIndicator(Context context)
    {
        super(context);
    }

    public TouchedTabPageIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
