package com.fjj.wisdomBJ.Controller;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.wisdomBJ.R;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller
 * @创建者: 范晶晶
 * @创建时间: 2016/5/19 15:10
 * @描述 控制器基类
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public abstract class BaseController
{
    private   View           mBaseView;
    private   Context        mContext;
    protected ImageButton    mMenu;
    protected TextView       mTitle;
    protected RelativeLayout mContent;

    public BaseController(Context context)
    {
        mContext = context;
        mBaseView = initView(context);
        initData(context);
    }

    public View getmBaseView()
    {
        return mBaseView;
    }

    public Context getmContext()
    {
        return mContext;
    }

    protected View initView(Context context)
    {
        View view = View.inflate(context, R.layout.basecontroller_view, null);
        mMenu = (ImageButton) view.findViewById(R.id.ib_base_menu);
        mTitle = (TextView) view.findViewById(R.id.tv_base_title);
        mContent = (RelativeLayout) view.findViewById(R.id.rl_base_content);
        return view;
    }

    protected void initData(Context context)
    {
    }
}
