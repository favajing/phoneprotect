package com.fjj.phoneprotect.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/18.
 */
public class FoucesTextView  extends TextView{
    public FoucesTextView(Context context) {
        super(context);
    }

    public FoucesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoucesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
