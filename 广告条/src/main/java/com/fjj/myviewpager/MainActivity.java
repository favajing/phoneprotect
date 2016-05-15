package com.fjj.myviewpager;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {

    ViewPager vpggt;
    TextView tvtitle;
    LinearLayout rlpoint;
    //图片资源
    private int[] ids = {R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e};

    // 图片标题集合
    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀"};

    private List<ImageView> imageViews;
    private int prevposition;
    //通过消息处理器实现自动跳转功能
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            //自动跳转到下一页
            vpggt.setCurrentItem(vpggt.getCurrentItem() + 1);

            handler.sendEmptyMessageDelayed(0,3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlpoint = (LinearLayout) findViewById(R.id.rl_poit);
        vpggt = (ViewPager) findViewById(R.id.vp_ggt);
        tvtitle = (TextView) findViewById(R.id.tv_title);

        //初始化数据,文字,圆点
        prevposition = 0;
        tvtitle.setText(imageDescriptions[0]);
        imageViews = new ArrayList<>();
        for (int id : ids) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
//            imageView.setImageResource(id);
            imageViews.add(imageView);


            //添加圆点
            ImageView view = new ImageView(this);
            //包裹类型
            //导入的包，一定要是改空间的父类的参数
            //设置大小可以在：LayoutParams里面设置和shape资源里面设置
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            view.setLayoutParams(params);
            view.setEnabled(false);
            view.setBackgroundResource(R.drawable.circleselector);
            rlpoint.addView(view);
        }
        rlpoint.getChildAt(0).setEnabled(true);

        //设置适配器
        vpggt.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = imageViews.get(position % imageViews.size());
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
                rlpoint.getChildAt(prevposition).setEnabled(false);
                rlpoint.getChildAt(position%imageViews.size()).setEnabled(true);
                prevposition = position%imageViews.size();
                tvtitle.setText(imageDescriptions[position%imageViews.size()]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置中间位置
        vpggt.setCurrentItem(Integer.MAX_VALUE/2 -  Integer.MAX_VALUE/2%imageViews.size());
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
