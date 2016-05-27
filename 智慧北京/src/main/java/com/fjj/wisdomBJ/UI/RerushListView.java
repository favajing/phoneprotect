package com.fjj.wisdomBJ.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fjj.wisdomBJ.R;


/**
 * Created by favaj on 2016/5/27.
 */
public class RerushListView extends ListView
{


    private RelativeLayout mRlMain;
    private LinearLayout mView;

    public RerushListView(Context context)
    {
        super(context);
        initHeadView();
    }

    //两个不能少
    public RerushListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initHeadView();
    }

    private void initHeadView()
    {
        //通过
        mView = (LinearLayout) View.inflate(getContext(), R.layout.rerush_activity, null);
        mRlMain = (RelativeLayout) mView.findViewById(R.id.rl_main);

        addHeaderView(mView);

        mRlMain.measure(0,0);
        int height = mRlMain.getMeasuredHeight();

        mView.setPadding(0,-height,0,0);
    }

    public void addFLView(View view){
        mView.addView(view);
    }
}
