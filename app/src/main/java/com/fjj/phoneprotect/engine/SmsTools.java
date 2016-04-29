package com.fjj.phoneprotect.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SmsTools {

    /**
     * 回调函数
     */
    public interface IBackUpSms {
        /**
         * 开始备份前执行
         * @param totalcount
         */
        void beforeBack(int totalcount);

        /**
         * 备份中执行
         * @param currentprogress
         */
        void onBack(int currentprogress);
    }

    public static boolean backUpSms(Context context, String backname, IBackUpSms bus) {
        //操作xml
        XmlSerializer serializer = Xml.newSerializer();
        File file = new File(Environment.getExternalStorageDirectory(), backname);
        int progress = 0;
        try {
            FileOutputStream os = new FileOutputStream(file);
            serializer.setOutput(os, "utf-8");
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "info");
            //读取短信内容提供者
            ContentResolver resolver = context.getContentResolver();
            Uri uri = Uri.parse("content://sms/");
            Cursor cursor = resolver.query(uri, new String[]{"address", "date", "body", "type"}, null, null, null);
            //添加属性
            serializer.attribute(null, "count", String.valueOf(cursor.getCount()));
            bus.beforeBack(cursor.getCount());
            //遍历短信
            while (cursor.moveToNext()) {
                serializer.startTag(null, "sms");
                serializer.startTag(null, "address");
                serializer.text(cursor.getString(0));
                serializer.endTag(null, "address");

                serializer.startTag(null, "date");
                serializer.text(cursor.getString(1));
                serializer.endTag(null, "date");

                serializer.startTag(null, "body");
                serializer.text(cursor.getString(2));
                serializer.endTag(null, "body");

                serializer.startTag(null, "type");
                serializer.text(cursor.getString(3));
                serializer.endTag(null, "type");
                serializer.endTag(null, "sms");
                progress ++;
                bus.onBack(progress);
            }
            serializer.endTag(null, "info");
            serializer.endDocument();//写文件的末尾
            cursor.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
