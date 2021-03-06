package com.fjj.wisdomBJ.Controller.News;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fjj.wisdomBJ.Bean.NewsCenterBean;
import com.fjj.wisdomBJ.MainActivity;
import com.fjj.wisdomBJ.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller.News
 * @创建者: 范晶晶
 * @创建时间: 2016/5/20 17:47
 * @描述 新闻中心中，新闻菜单对应的控制器
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class NewsMenuController extends NewsBaseController
{

    private final List<NewsCenterBean.NewsDomain> mListMenus;//新闻数据
    @ViewInject(R.id.vp_newsmenu)
    private       ViewPager                       vp;//viewpage
    @ViewInject(R.id.tpi_newsmenu)
    private       TabPageIndicator                tpi;//viewpage
    @ViewInject(R.id.iv_newsmenu_next)
    private       ImageView                       ivnext;//imageview

    public NewsMenuController(Context mContext, List<NewsCenterBean.NewsDomain> children)
    {
        super(mContext);
        mListMenus = children;
    }

    @Override
    public View initView(Context mContext)
    {
        View view = View.inflate(mContext, R.layout.newsmenu_activity, null);
        //注入
        ViewUtils.inject(this, view);

        return view;
    }

    @Override
    public void initData(Context context)
    {
        vp.setAdapter(new NewsMenuPagerAdapter());
        //填充插件
        tpi.setViewPager(vp);

        tpi.setOnPageChangeListener(new NewsMenuOnPageChangeListener());

        ivnext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                vp.setCurrentItem(vp.getCurrentItem() + 1);
            }
        });
    }

    //判断是否允许侧滑展开menu菜单
    private class NewsMenuOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            SlidingMenu sm = ((MainActivity) mContext).getSlidingMenu();
            if(position <= 0){
                sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

    private class NewsMenuPagerAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            if (mListMenus != null)
            {
                return mListMenus.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            String url = mListMenus.get(position).url;

            NewsListPageController npc = new NewsListPageController(mContext,url);

            container.addView(npc.getmBaseView());

            npc.initData(mContext);

            return npc.getmBaseView();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mListMenus.get(position).title;
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
