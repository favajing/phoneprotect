package com.fjj.wisdomBJ;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.fjj.wisdomBJ.fragment.ContentFragment;
import com.fjj.wisdomBJ.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @包名: com.fjj.wisdomBJ
 * @类名: MainActivity
 * @创建者: 范晶晶
 * @创建时间: 2016/5/18 11:00
 * @描述 TODO
 */
public class MainActivity extends SlidingFragmentActivity
{

    private static final String TAG_CONTENT = "Content";
    private static final String TAG_MENU    = "Menu";
    private SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // 2. 设置菜单的布局
        setBehindContentView(R.layout.menu);
        // 菜单的实例
        mSlidingMenu = getSlidingMenu();
        // 3.设置slidingMeun的mode
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        // 4.如果有右菜单
//         sm.setMode(SlidingMenu.LEFT_RIGHT);
//         sm.setSecondaryMenu(R.layout.menu);//设置右菜单
        // 4.设置菜单宽度
//        mSlidingMenu.setBehindWidth(550);
        mSlidingMenu.setBehindOffset(150);
        // 5.设置Touch Mode Above
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //初始化界面
        initFragment();
    }

    private void initFragment()
    {


        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction                    ft      = manager.beginTransaction();

        //分流主界面和菜单界面
        ft.replace(R.id.fl_fragment_content, new ContentFragment(), TAG_CONTENT);
        ft.replace(R.id.fl_fragment_menu, new MenuFragment(), TAG_MENU);

        ft.commit();
    }

    //获取menuFragment
    public MenuFragment getMenuFragment()
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        return (MenuFragment) manager.findFragmentByTag(TAG_MENU);
    }

    //获取menuFragment
    public ContentFragment getContentFragment()
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        return (ContentFragment) manager.findFragmentByTag(TAG_CONTENT);
    }

    //切换侧滑
    public void toggleSlidingMenu()
    {
        mSlidingMenu.toggle();
    }
}
