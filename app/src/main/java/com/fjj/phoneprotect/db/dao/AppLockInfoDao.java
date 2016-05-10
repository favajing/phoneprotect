package com.fjj.phoneprotect.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.fjj.phoneprotect.db.AppLockInfoDBOpenHelper;
import com.fjj.phoneprotect.db.BlackNumberDBOpenHelper;
import com.fjj.phoneprotect.domain.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class AppLockInfoDao {
    private final Context context;
    AppLockInfoDBOpenHelper dbhelper;
    private final String tablename = "lockappinfo";

    public AppLockInfoDao(Context context) {
        this.context = context;
        this.dbhelper = new AppLockInfoDBOpenHelper(context);
    }

    /**
     * 添加锁定程序
     * @param packagename 包名
     * @return
     */
    public boolean add(String packagename) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("packagename", packagename);
        long num = db.insert(tablename, null, values);
        db.close();
        //大吼一声提示数据变化了
        Uri uri = Uri.parse("content://com.fjj.phoneprotect.dbchange");
        context.getContentResolver().notifyChange(uri,null);
        return num != -1;
    }
    /**
     * 删除黑名单号码
     * @param packagename 包名
     * @return 是否删除成功
     */
    public boolean delete(String packagename){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int num = db.delete(tablename, "packagename=?", new String[]{packagename});
        db.close();
        Uri uri = Uri.parse("content://com.fjj.phoneprotect.dbchange");
        context.getContentResolver().notifyChange(uri, null);
        return num != 0;
    }
    /**
     * 查找包名对应的程序是否锁定
     * @param packagename 包名
     * @return
     */
    public boolean isLock(String packagename){
        SQLiteDatabase  db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(tablename, null, "packagename=?", new String[]{packagename}, null, null, null);
        boolean res = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return res;
    }
    /**
     * 查找所有锁定程序
     * @return
     */
    public List<String> FindAll(){
        SQLiteDatabase  db = dbhelper.getReadableDatabase();
        Cursor cursor = db.query(tablename, new String[]{"packagename"}, null, null, null, null, null);
        List<String> res = new ArrayList<>();
        while (cursor.moveToNext()){
            res.add(cursor.getString(0));
        }
        db.close();
        return res;
    }


}
