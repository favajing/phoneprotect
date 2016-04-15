package com.fjj.phoneprotect.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/4/15.
 */
public class StreamUtils {
    /**
     * 转换输入流至字符串
     *
     * @param is
     * @return 输入流中字符串  解析失败返回null
     */
    public static String readStreamToString(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bt = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(bt)) != -1) {
                bos.write(bt, 0, len);
            }
            is.close();
            String result = bos.toString();
            //智能识别网页编码
            if (result.contains("gb2312")) {
                return bos.toString("gb2312");
            }if (result.contains("gbk")) {
                return bos.toString("gbk");
            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
