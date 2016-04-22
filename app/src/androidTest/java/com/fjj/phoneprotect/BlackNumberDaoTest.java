package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.BlackNumberDao;

import java.util.Random;


/**
 * Created by Administrator on 2016/4/22.
 */
public class BlackNumberDaoTest extends ApplicationTestCase<Application> {
    BlackNumberDao dao;
    public BlackNumberDaoTest() {
        super(Application.class);
    }

    //初始化测试环境
    @Override
    protected void setUp() throws Exception {
        dao  = new BlackNumberDao(getContext());
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        dao  = null;
        super.tearDown();
    }



    public void testAdd() throws Exception {
        // this XXXActivity.this getApplicationContext()
        // getContext() 为了让测试代码可以执行,测试框架提供的虚拟的假的上下文.
//		boolean result = dao.add("13512345678", "1");
//		assertEquals(true, result);
        long basenumber = 13512340000l;
        Random random = new Random();
        for(int i =0;i<200;i++){
            dao.add(String.valueOf(basenumber+i), String.valueOf(random.nextInt(3)+1));
        }
    }

    public void testUpdate() throws Exception {
        boolean result = dao.update("13512345678", "2");
        assertEquals(true, result);
    }

    public void testFind() throws Exception {
        String result = dao.find("13512345678");
        assertEquals("2", result);

    }

    public void testDelete() throws Exception {
        boolean result = dao.delete("13512345678");
        assertEquals(true, result);
    }
}
