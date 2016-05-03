package com.fjj.phoneprotect.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AppInfo {
    /**
     * 应用名称
     */
    private String appname;
    /**
     * 包名
     */
    private String apppackage;
    /**
     * 应用图标
     */
    private Drawable appicon;
    /**
     * 应用大小
     */
    private long size;

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getApppackage() {
        return apppackage;
    }

    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public boolean isuserapp() {
        return isuserapp;
    }

    public void setIsuserapp(boolean isuserapp) {
        this.isuserapp = isuserapp;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    private boolean isuserapp;
    private boolean inRom;
}
