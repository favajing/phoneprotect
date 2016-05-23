package com.fjj.wisdomBJ.Controller.News;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fjj.wisdomBJ.Bean.NewsCenterBean;

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

public class InteractMenuController extends NewsBaseController
{

    private TextView mtv;

    public InteractMenuController(Context mContext, List<NewsCenterBean.NewsDomain> children)
    {
        super(mContext);
        initData(mContext);
    }

    @Override
    public View initView(Context mContext)
    {
        mtv = new TextView(mContext);
        mtv.setTextSize(24);
        mtv.setGravity(Gravity.CENTER);
        mtv.setTextColor(Color.RED);

        return mtv;
    }

    @Override
    public void initData(Context context)
    {
        // 设置实体数据
        mtv.setText("互动菜单对应的页面");
    }
}
