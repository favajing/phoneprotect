package com.fjj.wisdomBJ.Controller.News;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.wisdomBJ.Bean.NewsListPagerBean;
import com.fjj.wisdomBJ.Controller.NewsController;
import com.fjj.wisdomBJ.DetailActivity;
import com.fjj.wisdomBJ.MainActivity;
import com.fjj.wisdomBJ.R;
import com.fjj.wisdomBJ.UI.RerushListView;
import com.fjj.wisdomBJ.utils.CacheUtils;
import com.fjj.wisdomBJ.utils.LoggerUtils;
import com.fjj.wisdomBJ.utils.ToastUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller.News
 * @创建者: 范晶晶
 * @创建时间: 2016/5/23 17:34
 * @描述 TODO
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class NewsListPageController extends NewsBaseController
{
    private static final String TAG = "NewsListPageController";
    private final String                               mUrl;
    @ViewInject(R.id.vp_newslistpage_pic)
    private       ViewPager                            mVppic;
    @ViewInject(R.id.tv_newslistpage_title)
    private       TextView                             mTvtitle;
    @ViewInject(R.id.ll_newslistpage_point)
    private       LinearLayout                         mLLContine;
    @ViewInject(R.id.lv_newlistpage_news)
    private       RerushListView                       mNewsListView;
    private       NewsListPagerBean                    mNewsListDatas;
    private       BitmapUtils                          mBitmap;
    private       AutoSwitchPicTask                    mAutoSwitchPic;
    private       List<NewsListPagerBean.NewsItemBean> mNewsList;

    public NewsListPageController(Context mContext, String url)
    {
        super(mContext);
        mUrl = url;
    }

    @Override
    public View initView(Context mContext)
    {
        mBitmap = new BitmapUtils(mContext);

        View view = View.inflate(mContext, R.layout.newslistpage_activity, null);

        ViewUtils.inject(this, view);

        //把轮播图片viewpage提出来加到listview中 保持整体
        View view2 = View.inflate(mContext, R.layout.newpic_activity, null);

        ViewUtils.inject(this, view2);

        mNewsListView.addFLView(view2);

        mNewsListView.setOnRefreshView(new NewsListPageonRefreshView());

        return view;
    }

    @Override
    public void initData(Context context)
    {
        //访问网络
        final String url = mContext.getString(R.string.baseurl) + mUrl;

        //读取缓存
        String newscache = CacheUtils.getString(mContext, url);
        Long   aLong     = CacheUtils.getLong(mContext, url + "_date");
        if (!TextUtils.isEmpty(newscache) && System.currentTimeMillis() - aLong < NewsController.TIME_DIFFERENCE)
        {
            processData(newscache);
        } else
        {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    //记录缓存
                    CacheUtils.setString(mContext, url, responseInfo.result);
                    CacheUtils.setLong(mContext, url + "_date", System.currentTimeMillis());
                    processData(responseInfo.result);
                }

                @Override
                public void onFailure(HttpException e, String s)
                {
                    LoggerUtils.e(TAG, "网络失败:" + s);
                }
            });
        }

    }

    //数据加载完成后解析并处理
    private void processData(String result)
    {
        Gson gs = new Gson();
        mNewsListDatas = gs.fromJson(result, NewsListPagerBean.class);
        mNewsList = mNewsListDatas.data.news;
        //设置title
        mTvtitle.setText(mNewsListDatas.data.topnews.get(0).title);
        int index = 0;
        mLLContine.removeAllViews();
        //添加圆点
        for (NewsListPagerBean.NewsTopNewsBean topnew : mNewsListDatas.data.topnews)
        {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = 10;
            iv.setLayoutParams(params);

            iv.setImageResource(index == 0 ? R.drawable.dot_focus : R.drawable.dot_normal);
            mLLContine.addView(iv);
            index++;
        }
        //设置适配器
        mVppic.setAdapter(new NewsListPagePagerAdapter());
        //圆点切换
        mVppic.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                for (int i = 0; i < mLLContine.getChildCount(); i++)
                {
                    ((ImageView) mLLContine.getChildAt(i)).setImageResource(position == i ? R.drawable.dot_focus : R.drawable.dot_normal);
                }
                mTvtitle.setText(mNewsListDatas.data.topnews.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        //自动轮播
        if (mAutoSwitchPic == null)
        {
            mAutoSwitchPic = new AutoSwitchPicTask();
            mAutoSwitchPic.start();
        }

        //按下后停止轮播
        mVppic.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        mAutoSwitchPic.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoSwitchPic.start();
                        break;
                }

                return false;
            }
        });
        mNewsListView.setAdapter(new NewsBaseAdapter());
        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String newsurl = mContext.getString(R.string.baseurl) + mNewsList.get(position).url;
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("newsurl", newsurl);
                mContext.startActivity(intent);
            }
        });
    }
    //设置下拉刷新接口
    private  class NewsListPageonRefreshView implements RerushListView.onRefreshView
    {
        @Override
        public void RefreshView()
        {
            //访问网络
            final String url = mContext.getString(R.string.baseurl) + mUrl;

            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    //记录缓存
                    CacheUtils.setString(mContext, url, responseInfo.result);
                    CacheUtils.setLong(mContext, url + "_date", System.currentTimeMillis());
                    processData(responseInfo.result);
                    //通知rerush更新成功
                    mNewsListView.RerushSecuss();
                }

                @Override
                public void onFailure(HttpException e, String s)
                {
                    LoggerUtils.e(TAG, "网络失败:" + s);
                }
            });
        }
    }

    //新闻listview
    private class NewsBaseAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            if (mNewsList != null)
            {
                return mNewsList.size();
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View       view;
            ViewHolder holder;
            if (convertView != null)
            {
                view = convertView;
                holder = (ViewHolder) view.getTag();

            } else
            {
                view = View.inflate(mContext, R.layout.news_item_activity, null);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.tv_news_title);
                holder.date  = (TextView) view.findViewById(R.id.tv_news_date);
                holder.pic   = (ImageView) view.findViewById(R.id.iv_news_pic);
                view.setTag(holder);
            }
            holder.title.setText(mNewsList.get(position).title);
            holder.date.setText(mNewsList.get(position).pubdate);
            BitmapUtils bt = new BitmapUtils(mContext);
            bt.display(holder.pic, mContext.getString(R.string.baseurl) + mNewsList.get(position).listimage);
            return view;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }
    }

    class ViewHolder
    {
        public TextView  title;
        public TextView  date;
        public ImageView pic;
    }

    //自动轮播类
    class AutoSwitchPicTask extends Handler implements Runnable
    {
        /**
         * 开启任务
         */
        public void start()
        {
            postDelayed(this, 2000);
        }

        /**
         * 停止任务
         */
        public void stop()
        {
            removeCallbacks(this);
        }

        @Override
        public void run()
        {
            int current = mVppic.getCurrentItem();
            if (current == mVppic.getAdapter().getCount() - 1)
            {
                mVppic.setCurrentItem(0);
            } else
            {
                mVppic.setCurrentItem(mVppic.getCurrentItem() + 1);
            }
            postDelayed(this, 2000);
        }
    }

    //viewpager适配器
    private class NewsListPagePagerAdapter extends PagerAdapter
    {
        @Override
        public int getCount()
        {
            if (mNewsListDatas != null)
            {
                return mNewsListDatas.data.topnews.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            NewsListPagerBean.NewsTopNewsBean newspicbean = mNewsListDatas.data.topnews.get(position);

            ImageView iv = new ImageView(mContext);

            iv.setImageResource(R.drawable.pic_item_list_default);

            mBitmap.display(iv, mContext.getString(R.string.baseurl) + newspicbean.topimage);

            container.addView(iv);

            return iv;
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
