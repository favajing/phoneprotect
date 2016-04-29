package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.CommonNumberDao;
import com.fjj.phoneprotect.engine.SmsTools;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/22.
 */
public class SmsToolsTest extends ApplicationTestCase<Application> {

    public SmsToolsTest() {
        super(Application.class);
    }

    public void testgetCommonNum() throws Exception {
//        boolean res = SmsTools.backUpSms(getContext(), "backsms.xml");
//        assertEquals(true, res);
    }
}
