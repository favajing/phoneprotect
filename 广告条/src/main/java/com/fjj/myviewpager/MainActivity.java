package com.fjj.myviewpager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ViewPager vpggt;
    TextView tvtitle;
    //图片资源
    private int[] ids = { R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e };

    // 图片标题集合
    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀" };

    private List<ImageView> imageViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpggt = (ViewPager) findViewById(R.id.vp_ggt);
        tvtitle = (TextView) findViewById(R.id.tv_title);

        //初始化数据
        tvtitle.setText(imageDescriptions[0]);
        imageViews = new ArrayList<>();
        for (int id : ids) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
//            imageView.setImageResource(id);
            imageViews.add(imageView);
        }

        //设置适配器
        vpggt.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = imageViews.get(position);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        //添加滑动事件,设置文本
        vpggt.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvtitle.setText(imageDescriptions[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
