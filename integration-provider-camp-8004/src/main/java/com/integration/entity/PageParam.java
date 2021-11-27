package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: PageParam
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 分页参数
*/
public class PageParam {
    private int startIndex;
    private int pageSize;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
