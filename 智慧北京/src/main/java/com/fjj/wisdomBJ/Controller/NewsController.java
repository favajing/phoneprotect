package com.fjj.wisdomBJ.Controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.fjj.wisdomBJ.Controller.News.InteractMenuController;
import com.fjj.wisdomBJ.Controller.News.NewsBaseController;
import com.fjj.wisdomBJ.Controller.News.NewsMenuController;
import com.fjj.wisdomBJ.Controller.News.PicMenuController;
import com.fjj.wisdomBJ.Controller.News.TopicMenuController;
import com.fjj.wisdomBJ.Bean.NewsCenterBean;
import com.fjj.wisdomBJ.MainActivity;
import com.fjj.wisdomBJ.R;
import com.fjj.wisdomBJ.utils.CacheUtils;
import com.fjj.wisdomBJ.utils.LoggerUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller
 * @创建者: 范晶晶
 * @创建时间: 2016/5/19 15:16
 * @描述 首页控制器
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class NewsController extends BaseController
{

    private static final String TAG             = "NewsController";
    public static final long   TIME_DIFFERENCE = 2 * 60 * 1000;
    private List<NewsBaseController> mListControllers;
    private NewsCenterBean           mData;
    private FrameLayout              mFramelayout;

    public NewsController(Context context)
    {
        super(context);
    }

    @Override
    protected View initView(Context context)
    {
        return super.initView(context);
    }

    @Override
    protected View initContentView(Context context)
    {
        //添加子视图
        mFramelayout = new FrameLayout(context);

        return mFramelayout;
    }

    @Override
    public void initData(Context context)
    {
        mTitle.setText("新闻中心");
        mMenu.setVisibility(View.VISIBLE);
        final String url = mContext.getString(R.string.url);

        //缓存数据
        String urldata = CacheUtils.getString(mContext, url);
        if (!TextUtils.isEmpty(urldata))
        {
            //指定时间间隔内读取缓存不访问网络
            Long timeDifference = CacheUtils.getLong(mContext, url + "_date");
            if (System.currentTimeMillis() - timeDifference < TIME_DIFFERENCE)
            {
                LoggerUtils.i(TAG, "加载缓存!!!!!!");
                processData(urldata);
                return;
            }
        }

        //获取网络数据
        HttpUtils http = new HttpUtils();
        // RequestParams params = new RequestParams();
        // params.addBodyParameter(name, value);//请求内容中的
        // params.addQueryStringParameter(name, value);//请求行中的
        // params.addHeader(name, value);// 请求消息头

        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                LoggerUtils.i(TAG,"访问了网络!!!!!!");
                String result = responseInfo.result;
                //记录到缓存,时间
                CacheUtils.setString(mContext, url, result);
                CacheUtils.setLong(mContext, url + "_date", System.currentTimeMillis());
                //解析数据
                processData(result);
            }

            @Override
            public void onFailure(HttpException e, String s)
            {
                //请求失败
                LoggerUtils.w(TAG, "网络错误:" + s);
            }
        });
    }

    public void switchContent(int position)
    {
        if (mListControllers != null) {
            mFramelayout.removeAllViews();

            NewsBaseController news = mListControllers.get(position);
            mFramelayout.addView(news.getmBaseView());
            news.initData(mContext);

            mTitle.setText(mData.data.get(position).title);
        }
    }

    private void processData(String result)
    {
        Gson gson = new Gson();
        mData = gson.fromJson(result, NewsCenterBean.class);
        mListControllers = new ArrayList<>();

        for (NewsCenterBean.NewsCenterMenuDomain newsmenu : mData.data) {
            switch (newsmenu.type) {
                case 1:
                    mListControllers.add(new NewsMenuController(mContext, newsmenu.children));
                    break;
                case 10:
                    mListControllers.add(new TopicMenuController(mContext, newsmenu.children));
                    break;
                case 2:
                    mListControllers.add(new PicMenuController(mContext, newsmenu.children));
                    break;
                case 3:
                    mListControllers.add(new InteractMenuController(mContext, newsmenu.children));
                    break;
            }
        }

        //把参数传递给menuFragment
        ((MainActivity) mContext).getMenuFragment().setData(mData.data);

        switchContent(0);
    }

}
