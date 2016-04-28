package com.fjj.phoneprotect.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fjj.phoneprotect.R;

/**
 * Created by Administrator on 2016/4/18.
 */
public class SettingChangeView extends RelativeLayout {


    TextView tvtitle;
    TextView tvdesc;

    public SettingChangeView(Context context) {
        super(context);
        initView(context);
    }

    public SettingChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        tvtitle = (TextView) findViewById(R.id.tv_uichange_title);
        tvdesc = (TextView) findViewById(R.id.tv_uichange_desc);
        String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.fjj.phoneprotect", "chtitle");
        String desc = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.fjj.phoneprotect", "chdesc");
        tvtitle.setText(title);
        tvdesc.setText(desc);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.ui_change_view, this);
    }

    public void setDesc(String desc){
        tvdesc.setText(desc);
    }
}
