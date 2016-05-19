package com.fjj.wisdomBJ.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.fragment
 * @创建者: 范晶晶
 * @创建时间: 2016/5/18 17:53
 * @描述 fragment基类
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment
{

    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    //创建控件时执行
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return initView();
    }
    //界面创建完成后执行
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //初始化数据
    protected void initData()
    {

    }
    //初始化视图
    protected abstract View initView();
}
