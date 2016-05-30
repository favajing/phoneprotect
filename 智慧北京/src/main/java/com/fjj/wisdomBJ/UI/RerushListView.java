package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.wisdomBJ.R;
import com.fjj.wisdomBJ.utils.LoggerUtils;
import com.nineoldandroids.animation.ValueAnimator;


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
    private int           mStarty;
    private int             mMeasuredheight;
    private RotateAnimation mUPAnimation;
    private RotateAnimation mDOWNAnimation;
    private int             mPaddingTop;
    private onRefreshView   mMyListen;
    
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
        mPaddingTop = -mMeasuredheight;

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
                mStarty = (int) (ev.getY() + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) (ev.getY() + 0.5f);

                float diffY = moveY - mStarty;
                float movey = ev.getY();
                int diffy = (int) (movey - mStarty);
                mPaddingTop = diffy - mMeasuredheight;
                //判断当前状态,如果是正在刷新在不响应
                if (currentstate == SATATE_RERUSHING)
                {
                    break;
                }
                // 如果第一个View是可见的情况下，并且headerView完全可见的情况下
                if (mView != null)
                {
                    // 如果CustomHeaderView没有完全露出来，不去响应下拉刷新

                    // 取出listView的左上角的点
                    int[] lliw = new int[2];
                    this.getLocationInWindow(lliw);


                    // 取出customheaderView左上角的点
                    int[] hliw = new int[2];
                    mView.getLocationInWindow(hliw);


                    if (hliw[1] < lliw[1])
                    {
                        // 不响应下拉刷新
                        return super.onTouchEvent(ev);
                    }
                }

                int firstvis = getFirstVisiblePosition();
                if (firstvis == 0)
                {
                    if (diffY > 0)
                    {


                        mView.setPadding(0, mPaddingTop, 0, 0);
                        LoggerUtils.w(TAG, mPaddingTop + "");
                        if (mPaddingTop < 0 && currentstate != SATATE_DOWNRERUSH)
                        {
                            //下拉刷新
                            currentstate = SATATE_DOWNRERUSH;
                            rerushUI();
                        } else if (mPaddingTop >= 0 && currentstate != SATATE_UPERUSH)
                        {
                            //上拉刷新
                            currentstate = SATATE_UPERUSH;
                            rerushUI();

                        }
                        //消费掉,响应touch
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mStarty = 0;// 清空数据

                if (getFirstVisiblePosition() == 0)
                {
                    //释放刷新
                    if (currentstate == SATATE_UPERUSH)
                    {
                        //松开刷新
                        mView.setPadding(0, 0, 0, 0);
                        currentstate = SATATE_RERUSHING;
                        int start = mPaddingTop;
                        int end = 0;
                        doHeaderAnimation(start, end);
                        rerushUI();
                        //调用刷新接口
                        if (mMyListen != null)
                        {
                            mMyListen.RefreshView();
                        }

                    } else if (currentstate == SATATE_DOWNRERUSH)
                    { //下拉刷新状态
                        mView.setPadding(0, -mMeasuredheight, 0, 0);
                        int start = mPaddingTop;
                        int end = -mMeasuredheight;
                        doHeaderAnimation(start, end);
                    }
                    mPaddingTop = -mMeasuredheight;
                }


                break;
        }
        return super.onTouchEvent(ev);
    }

    private void doHeaderAnimation(int start, int end)
    {
        // 模拟数据的变化 100-->0 100,90,80
        ValueAnimator animator;
        animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int animatedValue = (Integer) animation.getAnimatedValue();

                mView.setPadding(0, animatedValue, 0, 0);
            }
        });
        animator.start();
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
                //文字上拉刷新
                mTvstate.setText("正在刷新...");
                //箭头动画

                mIvarrow.clearAnimation();

                //箭头显示,进度隐藏
                mIvarrow.setVisibility(View.INVISIBLE);
                mPbpregress.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setOnRefreshView(onRefreshView listen)
    {
        mMyListen = listen;
    }

    public interface onRefreshView
    {
        void RefreshView();
    }

    public void RerushSecuss()
    {
        currentstate = SATATE_DOWNRERUSH;
        // UI更新
        rerushUI();

        // 做动画
        int start = 0;
        int end   = -mMeasuredheight;
        doHeaderAnimation(start, end);

    }
}
