package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.AppLockInfoDao;
import com.fjj.phoneprotect.db.dao.BlackNumberDao;

import java.util.Random;


/**
 * Created by Administrator on 2016/4/22.
 */
public class AppLockInfoDaoTest extends ApplicationTestCase<Application> {
    AppLockInfoDao dao;
    public AppLockInfoDaoTest() {
        super(Application.class);
    }

    //初始化测试环境
    @Override
    protected void setUp() throws Exception {
        dao  = new AppLockInfoDao(getContext());
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
		boolean result = dao.add("com.fjj.phoneprotect");
		assertEquals(true, result);
//        long basenumber = 13512340000l;
//        Random random = new Random();
//        for(int i =0;i<200;i++){
//            dao.add(String.valueOf(basenumber+i), String.valueOf(random.nextInt(3)+1));
//        }
    }


    public void testFind() throws Exception {
        boolean result = dao.isLock("com.fjj.phoneprotect");
        assertEquals(true, result);

    }

    public void testDelete() throws Exception {
        boolean result = dao.delete("com.fjj.phoneprotect");
        assertEquals(true, result);
    }
}
