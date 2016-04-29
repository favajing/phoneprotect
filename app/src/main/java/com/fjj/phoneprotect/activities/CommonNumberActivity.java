package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.dao.CommonNumberDao;

import java.util.ArrayList;

public class CommonNumberActivity extends Activity {

    ExpandableListView elvphone;
    private ArrayList<CommonNumberDao.NumberType> commonNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_number);
        CommonNumberDao dao = new CommonNumberDao();
        commonNum = dao.getCommonNum();
        elvphone = (ExpandableListView) findViewById(R.id.elv_commonnumber_phone);
        elvphone.setAdapter(new BaseExpandableListAdapter() {
            /**
             * 设置组个数
             * @return
             */
            @Override
            public int getGroupCount() {
                return commonNum.size();
            }
            /**
             * 设置每组有多少孩子
             * @param groupPosition
             * @return
             */
            @Override
            public int getChildrenCount(int groupPosition) {
                return commonNum.get(groupPosition).getChildnumbers().size();
            }

            /**
             * 设置组控件样式
             * @param groupPosition
             * @param isExpanded
             * @param convertView
             * @param parent
             * @return
             */
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                TextView textView = (TextView) convertView;
                if (textView == null){
                    textView = new TextView(getApplicationContext());
                }
                textView.setTextColor(Color.RED);
                textView.setTextSize(20);
                textView.setText("        " + commonNum.get(groupPosition).getName());
                return textView;
            }

            /**
             * 设置孩子控件样式
             * @param groupPosition
             * @param childPosition
             * @param isLastChild
             * @param convertView
             * @param parent
             * @return
             */
            @Override
            public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = (TextView) convertView;
                if (textView == null){
                    textView = new TextView(getApplicationContext());
                }
                textView.setTextSize(20);
                textView.setTextColor(Color.RED);
                final ArrayList<CommonNumberDao.Number> childnumbers = commonNum.get(groupPosition).getChildnumbers();
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //定义意图,设置动作,设置数据,开始意图
                        Intent intent = new Intent();
                        intent.setData(Uri.parse("tel://" + childnumbers.get(childPosition).getNum()));//URI 统一资源标识符(范围更加广泛)
                        intent.setAction(Intent.ACTION_CALL);
                        startActivity(intent);
                    }
                });
                textView.setText(childnumbers.get(childPosition).getName() + " : " + childnumbers.get(childPosition).getNum());
                return textView;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return null;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return null;
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            //子类是否可点击
            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        });
    }
}
