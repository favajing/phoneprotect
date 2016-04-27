package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.BlackNumberDao;
import com.fjj.phoneprotect.db.dao.NumberAddressDao;

import java.util.Random;


/**
 * Created by Administrator on 2016/4/22.
 */
public class NumberAddressDaoTest extends ApplicationTestCase<Application> {

    public NumberAddressDaoTest() {
        super(Application.class);
    }

    public void testgetAddress() throws Exception {

        String result = NumberAddressDao.getAddress("13512345678");
        assertEquals(true, result.length() > 0);
    }
}
