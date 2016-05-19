package com.fjj.wisdomBJ;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fjj.wisdomBJ.utils.CacheUtils;
import com.fjj.wisdomBJ.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名: com.fjj.wisdomBJ
 * @类名: GuideActivity
 * @创建者: 范晶晶
 * @创建时间: 2016/5/17 17:13
 * @描述 引导页面
 */

public class GuideActivity extends Activity implements View.OnClickListener
{

    private ViewPager       mVp;
    private List<ImageView> mImage; //资源视图
    private Button          mBtnenter; //进入主界面按钮
    private LinearLayout    mLlbgcontainer; //静态圆点容器
    private int             mPointdesetence; //两点直接的距离
    private ImageView       mIvredpoint; //动态圆点

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化视图
        initView();

        //初始化数据
        initData();
    }

    private void initData()
    {
        mImage = new ArrayList<>();

        int[]     resarr = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        ImageView piv;
        int       index  = 0;
        for (int i : resarr) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(i);
            mImage.add(iv);

            //添加静态圆点
            piv = new ImageView(this);
            piv.setBackgroundResource(R.drawable.shape_guide_pointsouce);
            if (index > 0) {
                //设置属性
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 15;
                piv.setLayoutParams(lp);
            }

            mLlbgcontainer.addView(piv);
            index++;
        }


        mVp.setAdapter(new GuideAdapter());
        //监听页面切换监听事件
        mVp.addOnPageChangeListener(new GuideChangeListener());

    }

    private void initView()
    {
        mVp = (ViewPager) findViewById(R.id.vp_guide_view);
        mBtnenter = (Button) findViewById(R.id.btn_guide_enter);
        mLlbgcontainer = (LinearLayout) findViewById(R.id.ll_guide_bgpoint);
        mIvredpoint = (ImageView) findViewById(R.id.iv_guide_redpoint);

        //添加控件渲染完毕后的监听事件
        mLlbgcontainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                mLlbgcontainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //计算静态圆点直接的距离
                mPointdesetence = mLlbgcontainer.getChildAt(1).getLeft() - mLlbgcontainer.getChildAt(0).getLeft();
            }
        });

        mBtnenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        CacheUtils.setBoolean(this,SplashUI.KEY_FIRST_START,true);
        IntentUtils.startActivityAndFinish(GuideActivity.this,MainActivity.class);
    }

    private class GuideChangeListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvredpoint.getLayoutParams();
            layoutParams.leftMargin = (int) (mPointdesetence * positionOffset + position * mPointdesetence);

            mIvredpoint.setLayoutParams(layoutParams);
        }

        @Override
        public void onPageSelected(int position)
        {
            mBtnenter.setVisibility(mImage.size() - 1 == position ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

    private class GuideAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return mImage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            //获取视图
            ImageView imageView = mImage.get(position);
            //添加到容器
            container.addView(imageView);

            return imageView;
        }

        //删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
