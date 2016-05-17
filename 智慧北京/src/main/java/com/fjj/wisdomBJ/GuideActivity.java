package com.fjj.wisdomBJ;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名: com.fjj.wisdomBJ
 * @类名: GuideActivity
 * @创建者: 范晶晶
 * @创建时间: 2016/5/17 17:13
 * @描述 引导页面
 */

public class GuideActivity extends AppCompatActivity {

    ViewPager vp;
    private List<ImageView> mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化数据
        initData();

        //初始化视图
        initView();


    }

    private void initData() {
        int[] resarr = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

        mImage = new ArrayList<>();

        for (int i : resarr) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(i);
            mImage.add(iv);
        }
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp_guide_view);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mImage.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //获取视图
                ImageView imageView = mImage.get(position);
                //添加到容器
                container.addView(imageView);

                return imageView;
            }
            //删除
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        vp.setAdapter(adapter);
    }
}
