package com.fjj.wisdomBJ.Controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Controller
 * @创建者: 范晶晶
 * @创建时间: 2016/5/19 15:16
 * @描述 智慧服务控制器
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class SmartController extends BaseController
{

    private TextView mTv;

    public SmartController(Context context)
    {
        super(context);
    }

    @Override
    protected void initData(Context context)
    {
        super.initData(context);
        mTv = new TextView(context);
        mTv.setTextSize(30);
        mTv.setText("智慧服务内容");
        mContent.addView(mTv);

        mTitle.setText("智慧服务");
        mMenu.setVisibility(View.VISIBLE);
    }
}
