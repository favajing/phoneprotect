package com.fjj.phoneprotect.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/4/22.
 */
public class AppLockInfoDBOpenHelper extends SQLiteOpenHelper {

    public AppLockInfoDBOpenHelper(Context context) {
        super(context, "lockapp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //_id数据库的主键,自增长
        //packagename 锁定程序包名
        db.execSQL("create table lockappinfo (_id integer primary key autoincrement, packagename varchar(32))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
