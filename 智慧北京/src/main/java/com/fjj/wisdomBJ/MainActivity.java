package com.fjj.wisdomBJ;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fjj.wisdomBJ.fragment.ContentFragment;
import com.fjj.wisdomBJ.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // 2. 设置菜单的布局
        setBehindContentView(R.layout.menu);
        // 菜单的实例
        SlidingMenu sm = getSlidingMenu();
        // 3.设置slidingMeun的mode
        sm.setMode(SlidingMenu.LEFT);
        // 4.如果有右菜单
//         sm.setMode(SlidingMenu.LEFT_RIGHT);
//         sm.setSecondaryMenu(R.layout.menu);//设置右菜单
        // 4.设置菜单宽度
        sm.setBehindWidth(550);
        // mMenu.setBehindOffset(150);
        // 5.设置Touch Mode Above
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //初始化界面
        initFragment();
    }

    private void initFragment()
    {
        

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction                    ft = manager.beginTransaction();

        //分流主界面和菜单界面
        ft.replace(R.id.fl_fragment_content,new ContentFragment(),TAG_CONTENT);
        ft.replace(R.id.fl_fragment_menu,new MenuFragment(),TAG_CONTENT);

        ft.commit();


    }
}
