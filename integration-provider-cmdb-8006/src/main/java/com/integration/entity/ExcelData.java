package com.integration.entity;

import java.util.List;
/**
* @Package: com.integration.entity
* @ClassName: ExcelData
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: Excell数据类
*/
public class ExcelData {
	
    /**
     * 表头
     */
    private List<String> titles;

    /**
     * 数据
     */
    private List<List<Object>> rows;

    /**
     * 页签名称
     */
    private String name;

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public void setRows(List<List<Object>> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
