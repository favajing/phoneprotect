package com.fjj.wisdomBJ.Controller.News;

import android.content.Context;
import android.view.View;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller.News
 * @创建者: 范晶晶
 * @创建时间: 2016/5/20 17:43
 * @描述 新闻控制器积累
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public abstract class NewsBaseController
{
    protected View    mBaseView;
    protected Context mContext;

    public NewsBaseController(Context mContext)
    {
        this.mContext = mContext;
        mBaseView = initView(mContext);
    }

    public View getmBaseView()
    {
        return mBaseView;
    }

    public Context getmContext()
    {
        return mContext;
    }

    public abstract View initView(Context mContext);

    public void initData(Context context)
    {
    }
}
