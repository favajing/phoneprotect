package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.BlackNumberDBOpenHelper;
import com.fjj.phoneprotect.db.dao.BlackNumberDao;
import com.fjj.phoneprotect.domain.BlackNumberInfo;

import java.util.List;

public class CallSmsSafeActivity extends Activity {

    ListView phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_sms_safe);
        phones = (ListView) findViewById(R.id.lv_callsms_phones);
        BlackNumberDao dao = new BlackNumberDao(this);
        final List<BlackNumberInfo> info = dao.findAll();
        phones.setAdapter(new MyBaseAdapter(info));
    }

    private class MyBaseAdapter extends BaseAdapter {
        private final List<BlackNumberInfo> info;

        public MyBaseAdapter(List<BlackNumberInfo> info) {
            this.info = info;
        }

        @Override
        public int getCount() {
            return info.size();
        }
        /**
         * @param position
         *            位置
         * @param convertView
         *            历史回收的view对象. 当某个view对象被完全移除屏幕的时候
         *            1.尽量的复用converview(历史缓存的view),减少view对象创建的个数
         *            2. 尽量的减少子孩子id的查询次数 . 定义一个viewholder
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            viewholder textviews;
            if (convertView != null) {
                view = convertView;
                textviews = (viewholder) view.getTag();
            } else {
                view = View.inflate(getApplicationContext(), R.layout.item_callsmssafe_activity, null);
                textviews = new viewholder();
                textviews.phone = (TextView) view.findViewById(R.id.tv_itemcass_phone);
                textviews.mode = (TextView) view.findViewById(R.id.tv_itemcass_mode);
                view.setTag(textviews);
            }
            textviews.phone.setText(info.get(position).getPhone());
            switch (info.get(position).getMode()) {
                case "1":
                    textviews.mode.setText("电话拦截");
                    break;
                case "2":
                    textviews.mode.setText("短信拦截");
                    break;
                case "3":
                    textviews.mode.setText("电话拦截");
                    break;
            }
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    class viewholder{
        public TextView phone ;
        public TextView mode;
    }
}
