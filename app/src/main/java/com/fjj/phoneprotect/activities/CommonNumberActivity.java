package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;

public class CommonNumberActivity extends Activity {

    ExpandableListView elvphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_number);
        elvphone = (ExpandableListView) findViewById(R.id.elv_commonnumber_phone);
        elvphone.setAdapter(new BaseExpandableListAdapter() {
            /**
             * 设置组个数
             * @return
             */
            @Override
            public int getGroupCount() {
                return 8;
            }
            /**
             * 设置每组有多少孩子
             * @param groupPosition
             * @return
             */
            @Override
            public int getChildrenCount(int groupPosition) {
                switch (groupPosition) {
                    case 0:
                        return 1;
                    case 1:
                        return 2;
                    case 2:
                        return 3;
                    case 3:
                        return 4;
                    case 4:
                        return 5;
                    case 5:
                        return 6;
                    case 6:
                        return 7;
                    case 7:
                        return 8;
                    default:
                        return 0;
                }
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
                TextView textView = new TextView(getApplicationContext());
                textView.setText("我属于" + groupPosition + "组的控件");
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
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("我属于" + groupPosition + "组的控件的" + childPosition + "个孩子");
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

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        });
    }
}
