package com.fjj.wisdomBJ.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.fragment
 * @创建者: 范晶晶
 * @创建时间: 2016/5/18 17:57
 * @描述 TODO
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class MenuFragment extends BaseFragment
{
    @Override
    protected View initView()
    {
        TextView tv = new TextView(mActivity);
        tv.setText("菜单");
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
