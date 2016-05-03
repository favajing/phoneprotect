package com.fjj.phoneprotect;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fjj.phoneprotect.db.dao.NumberAddressDao;
import com.fjj.phoneprotect.domain.AppInfo;
import com.fjj.phoneprotect.engine.AppAdmin;

import java.util.List;


/**
 * Created by Administrator on 2016/4/22.
 */
public class AppAdminTest extends ApplicationTestCase<Application> {

    public AppAdminTest() {
        super(Application.class);
    }

    public void testgetAddress() throws Exception {

        List<AppInfo> infos = AppAdmin.findApp(getContext());
        assertEquals(true, infos.size() > 0);
    }
}
