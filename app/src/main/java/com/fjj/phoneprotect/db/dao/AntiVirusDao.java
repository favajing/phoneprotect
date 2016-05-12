package com.fjj.phoneprotect.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/5/12.
 */
public class AntiVirusDao {
    /**
     * 判断一个md5信息是否是病毒
     * @param md5 应用程序的md5信息
     * @return null代表扫描安全, 不是null病毒的描述信息.
     */
    public static String isVirus(String md5) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                "/data/data/com.fjj.phoneprotect/files/antivirus.db", null,
                SQLiteDatabase.OPEN_READONLY);
        String desc = null;
        Cursor cursor = db.rawQuery("select desc from datable where md5=?", new String[]{md5});
        if(cursor.moveToNext()){
            desc = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return desc;
    }

    /**
     * 获取数据库的版本号
     */
    public static int getVersion() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                "/data/data/com.itheima.mobilesafe/files/antivirus.db", null,
                SQLiteDatabase.OPEN_READONLY);
        int version = 0;
        Cursor cursor = db.rawQuery("select subcnt from version ", null);
        if(cursor.moveToNext()){
            version = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return version;
    }

    /**
     * 更新数据库的版本号
     */
    public static void setVersion(int newversion) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                "/data/data/com.itheima.mobilesafe/files/antivirus.db", null,
                SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("subcnt", newversion);
        db.update("version", values, null, null);
        db.close();
    }


    /**
     * 添加新的病毒信息
     */
    public static void addVirusInfo(String md5,String type,String desc,String name) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                "/data/data/com.itheima.mobilesafe/files/antivirus.db", null,
                SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("md5", md5);
        values.put("type", type);
        values.put("desc", desc);
        values.put("name", name);
        db.insert("datable", null, values);
        db.close();
    }
}
