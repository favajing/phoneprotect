package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.CommonNumberDao;
import com.fjj.phoneprotect.db.dao.NumberAddressDao;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/22.
 */
public class CommonNumberDaoTest extends ApplicationTestCase<Application> {

    public CommonNumberDaoTest() {
        super(Application.class);
    }

    public void testgetCommonNum() throws Exception {
        CommonNumberDao dao = new CommonNumberDao();
        ArrayList<CommonNumberDao.NumberType> commonNum = dao.getCommonNum();
        assertEquals(true, commonNum.size() == 8);
    }
}
