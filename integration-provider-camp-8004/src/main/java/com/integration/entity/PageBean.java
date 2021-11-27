package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: PageBean
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 分页实体类
*/
public class PageBean<T> {
     /**
      * 当前页
      */
    private int pageNum;

     /**
      * 每页显示的条数
      */
    private int pageSize;

     /**
      * 总记录数
      */
    private int totalNum;

     /**
      * 总页数
      */
    private int totalPage;

     /**
      * 开始索引
      */
    private int startIndex;

     /**
      * 每页条数集合
      */
    private List<T> list;

     /**
      * 分页显示的页数
      */
    private int start;
    private int end;

    public PageBean(int pageNum, int pageSize, int totalNum){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalNum = totalNum;

         /**
          * 总页数
          */
        if (totalNum % pageSize == 0){
            this.totalPage = totalNum/pageSize;
        }else {
            this.totalPage = totalNum/pageSize +1;
        }

         /**
          * 开始索引
          */
        this.startIndex = (pageNum -1)*pageSize;
        this.start = 1;
        this.end = 5;

         /**
          * 页码展示
          */
        if (totalPage <= 5){
            this.end = this.totalPage;
        }else {
            this.start = pageNum - 2;
            this.end = pageNum + 2;

            if (start < 0){
                this.start = 1;
                this.end = 5;
            }

            if (end > this.totalPage){
                this.end = totalPage;
                this.start = end -5;
            }
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
