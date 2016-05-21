package com.fjj.wisdomBJ.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.fjj.wisdomBJ.Controller.BaseController;
import com.fjj.wisdomBJ.Controller.GovaController;
import com.fjj.wisdomBJ.Controller.HomeController;
import com.fjj.wisdomBJ.Controller.NewsController;
import com.fjj.wisdomBJ.Controller.SettingController;
import com.fjj.wisdomBJ.Controller.SmartController;
import com.fjj.wisdomBJ.MainActivity;
import com.fjj.wisdomBJ.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.fragment
 * @创建者: 范晶晶
 * @创建时间: 2016/5/18 17:57
 * @描述 内容界面容器
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class ContentFragment extends BaseFragment
{
    @ViewInject(R.id.vp_content)
    private ViewPager            mVpcontent;
    @ViewInject(R.id.rg_content)
    private RadioGroup           mRg;
    private List<BaseController> mControllers;
    private int                  currentItem;

    @Override
    protected View initView()
    {
        View view = View.inflate(mActivity, R.layout.content, null);

        //使用xutils注入
        ViewUtils.inject(this, view);

        return view;
    }

    //初始化数据
    @Override
    protected void initData()
    {
        super.initData();


        mControllers = new ArrayList<>();
        //添加首页控制器
        mControllers.add(new HomeController(mActivity));
        mControllers.add(new NewsController(mActivity));
        mControllers.add(new SmartController(mActivity));
        mControllers.add(new GovaController(mActivity));
        mControllers.add(new SettingController(mActivity));

        mVpcontent.setAdapter(new ContentAdapter());

        //添加radiogroup点击事件,切换viewpage
        mRg.setOnCheckedChangeListener(new ContentOnCheckedChangeListener());

        //设置默认选中button
        mRg.check(R.id.rb_home);

        currentItem = 0;
    }

    public void switchContent(int position)
    {
        mControllers.get(currentItem).switchContent(position);
    }

    private class ContentOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch (checkedId) {
                case R.id.rb_home:
                    //切换viewpage
                    currentItem = 0;
                    //设置侧边栏不可滑动
                    setSlidingMode(false);
                    break;
                case R.id.rb_news:
                    currentItem = 1;
                    setSlidingMode(true);
                    break;
                case R.id.rb_smart:
                    currentItem = 2;
                    setSlidingMode(true);
                    break;
                case R.id.rb_gova:
                    currentItem = 3;
                    setSlidingMode(true);
                    break;
                case R.id.rb_setting:
                    currentItem = 4;
                    setSlidingMode(false);
                    break;
            }
            mVpcontent.setCurrentItem(currentItem);
        }
    }

    /**
     * 设置侧边栏是否可以滑动出现
     *
     * @param istouch
     */
    private void setSlidingMode(boolean istouch)
    {
        MainActivity ma = (MainActivity) mActivity;//获取它的宿主实例
        SlidingMenu  sm = ma.getSlidingMenu();
        sm.setTouchModeAbove(istouch ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
    }

    private class ContentAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            return mControllers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            BaseController ctl  = mControllers.get(position);
            View           view = ctl.getmBaseView();
            container.addView(view);

            ctl.initData(mActivity);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
