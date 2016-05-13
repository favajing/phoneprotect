package com.fjj.phoneprotect.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fjj.phoneprotect.R;

/**
 * Created by Administrator on 2016/4/18.
 */
public class SettingCheckedView extends LinearLayout {

    TextView tvtitle;
    CheckBox ckstate;
    public SettingCheckedView(Context context) {
        super(context);
        initView(context);
    }

    public SettingCheckedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        tvtitle = (TextView) findViewById(R.id.tv_uisetting_title);
        ckstate = (CheckBox) findViewById(R.id.cb_uisetting_state);
        String bigtitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "bigtitle");
        tvtitle.setText(bigtitle);
    }

    private void initView(Context context) {
        this.setOrientation(VERTICAL);
        this.addView(View.inflate(context, R.layout.ui_settion_view,null));
    }

    public boolean isChecked(){
        return ckstate.isChecked();
    }

    public void setChecked(boolean bl){
        ckstate.setChecked(bl);
    }
}
