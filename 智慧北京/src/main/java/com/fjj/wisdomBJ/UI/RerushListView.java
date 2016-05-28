package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.wisdomBJ.R;
import com.fjj.wisdomBJ.utils.LoggerUtils;


/**
 * Created by favaj on 2016/5/27.
 */
public class RerushListView extends ListView
{
    
    
    private final static int    SATATE_DOWNRERUSH = 0; //下拉刷新
    private final static int    SATATE_UPERUSH    = 1; //上拉刷新
    private final static int    SATATE_RERUSHING  = 2; //正在刷新
    private static final String TAG               = "RerushListView";
    private              int    currentstate      = SATATE_DOWNRERUSH;
    
    private RelativeLayout  mRlMain;
    private LinearLayout    mView;
    private TextView        mTvstate;
    private TextView        mTvdate;
    private ImageView       mIvarrow;
    private ProgressBar     mPbpregress;
    private float           mStarty;
    private int             mMeasuredheight;
    private RotateAnimation mUPAnimation;
    private RotateAnimation mDOWNAnimation;
    
    public RerushListView(Context context)
    {
        super(context);
        initHeadView();
        initAnimation();
    }
    
    //两个不能少
    public RerushListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initHeadView();
        initAnimation();
    }
    
    private void initAnimation()
    {
        
        mUPAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUPAnimation.setFillAfter(true);
        mUPAnimation.setDuration(300);
        
        mDOWNAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDOWNAnimation.setFillAfter(true);
        mDOWNAnimation.setDuration(300);
    }

    private void initHeadView()
    {
        //通过
        mView = (LinearLayout) View.inflate(getContext(), R.layout.rerush_activity, null);
        mRlMain = (RelativeLayout) mView.findViewById(R.id.rl_main);
        mTvstate = (TextView) mView.findViewById(R.id.tv_rerush_state);
        mTvdate = (TextView) mView.findViewById(R.id.tv_rerush_date);
        mIvarrow = (ImageView) mView.findViewById(R.id.iv_rerush_arrow);
        mPbpregress = (ProgressBar) mView.findViewById(R.id.pb_rerush_progress);

        addHeaderView(mView);

        mRlMain.measure(0, 0);
        mMeasuredheight = mRlMain.getMeasuredHeight();

        mView.setPadding(0, -mMeasuredheight, 0, 0);
    }

    public void addFLView(View view)
    {
        mView.addView(view);
    }

    //下拉显示刷新控件,setPadding
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
                if (firstvis == 0)
                {
                    int paddingTop = diffy - mMeasuredheight;
                    mView.setPadding(0, paddingTop, 0, 0);
                    LoggerUtils.w(TAG, paddingTop + "");
                    if (paddingTop < 0 && currentstate != SATATE_DOWNRERUSH)
                    {
                        //下拉刷新
                        currentstate = SATATE_DOWNRERUSH;
                        rerushUI();
                    } else if (paddingTop >= 0 && currentstate != SATATE_UPERUSH)
                    {
                        //上拉刷新
                        currentstate = SATATE_UPERUSH;
                        rerushUI();

                    }
                    //消费掉
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void rerushUI()
    {
        switch (currentstate)
        {
            case SATATE_DOWNRERUSH:
                //文字下拉刷新
                mTvstate.setText("下拉刷新");
                //箭头动画
                mIvarrow.startAnimation(mDOWNAnimation);
                //箭头显示,进度隐藏
                mIvarrow.setVisibility(View.VISIBLE);
                mPbpregress.setVisibility(View.INVISIBLE);
                break;
            case SATATE_UPERUSH:
                //文字上拉刷新
                mTvstate.setText("上拉刷新");
                //箭头动画

                mIvarrow.startAnimation(mUPAnimation);

                //箭头显示,进度隐藏
                mIvarrow.setVisibility(View.VISIBLE);
                mPbpregress.setVisibility(View.INVISIBLE);
                break;
            case SATATE_RERUSHING:
                break;
        }
    }
}
