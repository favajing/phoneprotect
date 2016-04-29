package com.fjj.phoneprotect.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.security.IdentityScope;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/27.
 */
public class CommonNumberDao {
    /**
     * 返回公用电话号码分类及子类号码
     * @return 公用电话号码
     */
    public ArrayList<NumberType> getCommonNum() {
        ArrayList<NumberType> res = new ArrayList<>();
        // 把apk里面的数据库文件 ,拷贝到手机的data/data/包名/files目录
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                "/data/data/com.fjj.phoneprotect/files/commonnum.db", null,
                SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery(
                "select name,idx from classlist order by idx", null);
        while (cursor.moveToNext()) {
            NumberType model = new NumberType();
            model.setName(cursor.getString(cursor.getColumnIndex("name")));
            int idx = cursor.getInt(cursor.getColumnIndex("idx"));
            model.setChildid(idx);
            Cursor childcursor = db.rawQuery("select * from table" + idx + " order by _id", null);
            model.childnumbers = new ArrayList<>();
            while (childcursor.moveToNext()) {
                Number number = new Number();
                number.setName(childcursor.getString(childcursor.getColumnIndex("name")));
                number.setNum(childcursor.getString(childcursor.getColumnIndex("number")));
                model.getChildnumbers().add(number);
            }
            childcursor.close();
            res.add(model);
        }
        cursor.close();
        db.close();
        return res;
    }

    /**
     * 公用号码分类
     */
    public class NumberType {
        public String name;
        public int childid;
        public ArrayList<Number> childnumbers;

        public int getChildid() {
            return childid;
        }

        public void setChildid(int childid) {
            this.childid = childid;
        }

        public ArrayList<Number> getChildnumbers() {
            return childnumbers;
        }

        public void setChildnumbers(ArrayList<Number> childnumbers) {
            this.childnumbers = childnumbers;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 公用号码
     */
    public class Number {
        public String num;
        public String name;
        public int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
