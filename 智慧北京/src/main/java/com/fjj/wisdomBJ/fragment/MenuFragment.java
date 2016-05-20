package com.fjj.wisdomBJ.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.wisdomBJ.Domain.NewsCenterDomain;
import com.fjj.wisdomBJ.MainActivity;
import com.fjj.wisdomBJ.R;

import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.fragment
 * @创建者: 范晶晶
 * @创建时间: 2016/5/18 17:57
 * @描述 分流菜单Fragment
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class MenuFragment extends BaseFragment
{

    private ListView                                    mListView;
    private List<NewsCenterDomain.NewsCenterMenuDomain> mData;
    private int                                         mCurrentPosition;//当前选中的菜单
    private MenuFragmentAdapter                         adapter;

    @Override
    protected View initView()
    {
        mListView = new ListView(mActivity);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        //去掉listview点击后的背景颜色
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;

        mCurrentPosition = 0;
        mListView.setLayoutParams(params);
        return mListView;
    }

    /**
     * 根据数据填充视图
     *
     * @param datas
     */
    public void setData(List<NewsCenterDomain.NewsCenterMenuDomain> datas)
    {
        mData = datas;
        adapter = new MenuFragmentAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new MenuFragmentOnItemClickListener());

    }

    private class MenuFragmentOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //切换content
            ((MainActivity) mActivity).getContentFragment().switchContent(position);

            //关闭侧滑
            ((MainActivity) mActivity).toggleSlidingMenu();
            //切换选中标记
            mCurrentPosition = position;
            adapter.notifyDataSetChanged();

        }
    }

    private class MenuFragmentAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View      view   = View.inflate(mActivity, R.layout.menu_item_activity, null);
            ImageView ivmenu = (ImageView) view.findViewById(R.id.iv_menu_arr);
            TextView  title  = (TextView) view.findViewById(R.id.tv_menu_title);
            //通过enabled来切换是否被选中
            ivmenu.setEnabled(position == mCurrentPosition);
            title.setEnabled(position == mCurrentPosition);
            title.setText(mData.get(position).title);

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
}
