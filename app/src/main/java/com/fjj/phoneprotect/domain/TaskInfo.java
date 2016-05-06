package com.fjj.phoneprotect.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by favaj on 2016/5/5.
 */
public class TaskInfo {
    private String taskName;
    private String packagename;
    private Drawable icon;
    private long memsize;
    private boolean isuser;
    private boolean ischecked;

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getMemsize() {
        return memsize;
    }

    public void setMemsize(long memsize) {
        this.memsize = memsize;
    }

    public boolean isuser() {
        return isuser;
    }

    public void setIsuser(boolean isuser) {
        this.isuser = isuser;
    }
}
