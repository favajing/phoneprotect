package com.fjj.phoneprotect.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/22.
 */
public class BlackNumberDBOpenHelper extends SQLiteOpenHelper {

    public BlackNumberDBOpenHelper(Context context) {
        super(context, "phoneprotect.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //_id数据库的主键,自增长
        //phone 黑名单电话号码
        //mode 拦截模式 1 电话拦截 2短信拦截 3全部拦截
        db.execSQL("create table blacknumberinfo (_id integer primary key autoincrement, phone varchar(20),mode varchar(2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
