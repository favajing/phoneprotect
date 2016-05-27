package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fjj.wisdomBJ.R;


/**
 * Created by favaj on 2016/5/27.
 */
public class RerushListView extends ListView
{


    private RelativeLayout mRlMain;
    private LinearLayout   mView;
    private float          mStarty;
    private int mMeasuredheight;

    public RerushListView(Context context)
    {
        super(context);
        initHeadView();
    }

    //两个不能少
    public RerushListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initHeadView();
    }

    private void initHeadView()
    {
        //通过
        mView = (LinearLayout) View.inflate(getContext(), R.layout.rerush_activity, null);
        mRlMain = (RelativeLayout) mView.findViewById(R.id.rl_main);

        addHeaderView(mView);

        mRlMain.measure(0, 0);
        mMeasuredheight = mRlMain.getMeasuredHeight();

        mView.setPadding(0, -mMeasuredheight, 0, 0);
    }

    public void addFLView(View view){
        mView.addView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mStarty = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float movey = ev.getY();
                int diffy = (int) (movey - mStarty);
                int firstvis = getFirstVisiblePosition();
                if (firstvis == 0){

                    mView.setPadding(0, diffy - mMeasuredheight, 0, 0);
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
