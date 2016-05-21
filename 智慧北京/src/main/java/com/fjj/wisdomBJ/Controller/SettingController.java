package com.fjj.wisdomBJ.Controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller
 * @创建者: 范晶晶
 * @创建时间: 2016/5/19 15:16
 * @描述 设置控制器
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class SettingController extends BaseController
{

    private TextView mTv;

    public SettingController(Context context)
    {
        super(context);
    }

    @Override
    protected View initContentView(Context context)
    {
        mTv = new TextView(context);
        mTv.setTextSize(30);
        return mTv;
    }

    @Override
    public void initData(Context context)
    {
        super.initData(context);
        mTv.setText("设置内容");
        mTitle.setText("设置");
        mMenu.setVisibility(View.GONE);
    }
}
