package com.integration.entity;

/**
 * @author hbr
 * @date 2018-12-11 14:23:27
 * @version 1.0
 */
public class Download {
    /**
     * 组名
     */
    private String group;

    /**
     * 文件名
     */
    private String remote;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    @Override
    public String toString() {
        return "Download{" +
                "group='" + group + '\'' +
                ", remote='" + remote + '\'' +
                '}';
    }
}