package com.fjj.wisdomBJ.Controller.News;

import android.content.Context;
import android.nfc.Tag;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fjj.wisdomBJ.Bean.NewsListPagerBean;
import com.fjj.wisdomBJ.R;
import com.fjj.wisdomBJ.utils.CacheUtils;
import com.fjj.wisdomBJ.utils.LoggerUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

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
    private final String            mUrl;
    @ViewInject(R.id.vp_newslistpage_pic)
    private       ViewPager         mVppic;
    @ViewInject(R.id.tv_newslistpage_title)
    private       TextView          mTvtitle;
    @ViewInject(R.id.ll_newslistpage_point)
    private       LinearLayout      mLLContine;
    private       NewsListPagerBean mNewsListDatas;
    private       BitmapUtils       mBitmap;

    public NewsListPageController(Context mContext, String url)
    {
        super(mContext);
        mUrl = url;
    }

    @Override
    public View initView(Context mContext)
    {
        View view = View.inflate(mContext, R.layout.newslistpage_activity, null);

        ViewUtils.inject(this, view);

        mBitmap = new BitmapUtils(mContext);

        return view;
    }

    @Override
    public void initData(Context context)
    {
        //访问网络
        String url = mContext.getString(R.string.baseurl) + mUrl;

        //读取缓存
        String newscache = CacheUtils.getString(mContext, url);
        if (!TextUtils.isEmpty(newscache)){
            processData(newscache);
        }else {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
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

    private void processData(String result)
    {
        Gson gs = new Gson();
        mNewsListDatas = gs.fromJson(result, NewsListPagerBean.class);

        //设置title
        mTvtitle.setText(mNewsListDatas.data.topnews.get(0).title);
        int index = 0;
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

        mVppic.setAdapter(new NewsListPagePagerAdapter());
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

    }

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
