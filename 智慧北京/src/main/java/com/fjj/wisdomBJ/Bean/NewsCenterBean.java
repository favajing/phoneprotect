package com.fjj.wisdomBJ.Bean;

import java.util.List;

/**
 * @项目名: mobilesafe
 * @包名: com.fjj.wisdomBJ.Domain
 * @创建者: 范晶晶
 * @创建时间: 2016/5/20 15:00
 * @描述 TODO
 * @svn版本: $$Rev$$
 * @更新人: $$Author$$
 * @更新时间: $$Date$$
 * @更新描述:
 */

public class NewsCenterBean
{
    public List<NewsCenterMenuDomain> data;
    public List<Integer>        extend;
    public int                  retcode;

    public class NewsCenterMenuDomain
    {
        public List<NewsDomain>   children;
        public int    id;
        public String title;
        public int    type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;
    }

    public class NewsDomain
    {
        public int    id;
        public String title;
        public int    type;
        public String url;
    }
}
