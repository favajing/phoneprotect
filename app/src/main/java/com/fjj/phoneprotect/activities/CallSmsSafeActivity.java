package com.fjj.phoneprotect.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fjj.phoneprotect.R;
import com.fjj.phoneprotect.db.BlackNumberDBOpenHelper;
import com.fjj.phoneprotect.db.dao.BlackNumberDao;
import com.fjj.phoneprotect.domain.BlackNumberInfo;
import com.fjj.phoneprotect.utils.ToastUtils;

import java.util.List;

public class CallSmsSafeActivity extends Activity {

    ListView phones;
    LinearLayout loading;
    private MyBaseAdapter adapter;
    BlackNumberDao dao;
    protected List<BlackNumberInfo> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_sms_safe);
        phones = (ListView) findViewById(R.id.lv_callsms_phones);
        loading = (LinearLayout) findViewById(R.id.ll_callsms_loading);
        //获取数据库黑名单
        final BlackNumberDao dao = new BlackNumberDao(this);
        //优化listview加载数据方式
        new Thread(){
            @Override
            public void run() {
                info = dao.findAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //绑定到listview
                        adapter = new MyBaseAdapter();
                        phones.setAdapter(adapter);
                        loading.setVisibility(View.INVISIBLE);
                    }
                });
                super.run();
            }
        }.start();


    }
    //添加黑名单
    public void add(View view) {
        //弹出框,通用化
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View inflate = View.inflate(this, R.layout.dialog_callsms_addblackphone, null);
        final RadioGroup rg = (RadioGroup) inflate.findViewById(R.id.rg_blackgroup);
        final EditText etphone = (EditText) inflate.findViewById(R.id.et_dialogcall_phone);
//        取消事件
        Button no = (Button) inflate.findViewById(R.id.bt_dialoghome_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //确定事件
        Button yes = (Button) inflate.findViewById(R.id.bt_dialoghome_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etphone.getText().toString().trim();
                int mode = rg.getCheckedRadioButtonId();
                String modeid = "";
                switch (mode) {
                    case R.id.rb_phone:
                        modeid = "1";
                        break;
                    case R.id.rb_sms:
                        modeid = "2";
                        break;
                    case R.id.rb_all:
                        modeid = "3";
                        break;
                }
                dao = new BlackNumberDao(CallSmsSafeActivity.this);
                boolean res = dao.add(phone, modeid);
                if (res){
                    //添加成功,更新listview
                    ToastUtils.show(CallSmsSafeActivity.this, "添加成功");
                    BlackNumberInfo object = new BlackNumberInfo();
                    object.setPhone(phone);
                    object.setMode(modeid);
                    info.add(0, object);
                    //用于实时更新listview
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtils.show(CallSmsSafeActivity.this, "添加失败");
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(inflate,0,0,0,0);
        alertDialog.show();
    }

    private class MyBaseAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            viewholder textviews;
            //使用convertview减少android回收垃圾次数
            if (convertView != null) {
                view = convertView;
                //使用tag标签提高查找view效率
                textviews = (viewholder) view.getTag();
            } else {
                view = View.inflate(getApplicationContext(), R.layout.item_callsmssafe_activity, null);
                textviews = new viewholder();
                textviews.phone = (TextView) view.findViewById(R.id.tv_itemcass_phone);
                textviews.mode = (TextView) view.findViewById(R.id.tv_itemcass_mode);
                textviews.ivdel = (ImageView) view.findViewById(R.id.iv_itemcass_del);
                view.setTag(textviews);
            }
            //外部设置控件的值,事件
            textviews.phone.setText(info.get(position).getPhone());
            switch (info.get(position).getMode()) {
                case "1":
                    textviews.mode.setText("电话拦截");
                    break;
                case "2":
                    textviews.mode.setText("短信拦截");
                    break;
                case "3":
                    textviews.mode.setText("全部拦截");
                    break;
            }
            //删除黑名单事件
            textviews.ivdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CallSmsSafeActivity.this);
                    builder.setTitle("警告");
                    builder.setMessage("您确定要删除" + info.get(position).getPhone() + "吗?");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String phone = info.get(position).getPhone();
                            dao = new BlackNumberDao(CallSmsSafeActivity.this);
                            boolean res = dao.delete(phone);
                            if (res) {
                                ToastUtils.show(CallSmsSafeActivity.this, "删除成功");
                                info.remove(position);
                                adapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.show(CallSmsSafeActivity.this, "删除失败");
                            }
                        }
                    });
                    builder.show();
                }
            });
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
        public ImageView ivdel;
    }
}
