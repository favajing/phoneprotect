package com.fjj.downmenu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etcontent;
    private ListView lv;
    private BaseAdapter adapter;
    private List<String> arr;
    private PopupWindow ppw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcontent = (EditText) findViewById(R.id.et_content);

//        view1 = View.inflate(this, R.layout.item_downmen, null);

        arr = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arr.add("我是谁" + i);
        }

         lv = new ListView(this);
        adapter = new MymyAdapter();
        lv.setAdapter(adapter);
    }

    public void showdownmenu(View view) {
        ppw = new PopupWindow(this);
        ppw.setWidth(etcontent.getWidth());
        //设置点击外边消掉PopupWindow
        //window.setOutsideTouchable(true);
        ppw.setFocusable(true);
        ppw.setHeight(400);
        ppw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //设置视图
        ppw.setContentView(lv);
        ppw.showAsDropDown(etcontent, 0, 0);
    }

    private class MymyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view1;
            ViewHolder holder;
            if (convertView != null) {
                view1 = convertView;
                holder = (ViewHolder) view1.getTag();
            } else {
                view1 = View.inflate(getApplicationContext(), R.layout.item_downmen, null);
                holder = new ViewHolder();
                holder.content = (TextView) view1.findViewById(R.id.tv_content);
                holder.ivdel = (ImageView) view1.findViewById(R.id.iv_del);
                view1.setTag(holder);
            }
            holder.content.setText(arr.get(position));
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etcontent.setText(arr.get(position));
                    ppw.dismiss();
                }
            });
            holder.ivdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arr.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });
            return view1;
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

    class ViewHolder{
        public TextView content;
        public ImageView ivdel;
    }
}
