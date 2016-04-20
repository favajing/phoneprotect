package com.fjj.phoneprotect.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.utils.ContactInfoUtils;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        listView = (ListView) findViewById(R.id.lv_contacts_list);
        final List<ContactInfoUtils.ContactInfo> contactInfos = ContactInfoUtils.getContactInfos(this);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return contactInfos.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView tv = new TextView(getApplicationContext());
                tv.setTextColor(Color.BLACK);
                tv.setHeight(100);
                tv.setTextSize(20);
                tv.setClickable(true);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("phone",tv.getText());
                        setResult(0, intent);
                        finish();
                    }
                });
                tv.setText(contactInfos.get(position).name + ":" + contactInfos.get(position).phone);
                return tv;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
    }
}
