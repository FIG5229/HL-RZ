package com.integration.controller;

import com.integration.entity.PageResult;
import com.integration.service.CiInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: CiInitializationController
 * @Author: ztl
 * @Date: 2020-10-15
 * @Version: 1.0
 * @description:CI数据初始化
 */
@RestController
@RequestMapping("ciInit")
public class CiInitializationController {

    @Autowired
    private CiInitializationService ciInitializationService;
    /**
     * 初始化导入分类
     *
     * @param multipartFiles
     * @return
     */
    @RequestMapping(value = "importExcel",method = RequestMethod.POST)
    public List<Map<String, Object>> importExcel(@RequestParam(required = false,value = "files") MultipartFile[] multipartFiles) {

       return ciInitializationService.importExcel(multipartFiles);
    }

    /**
     *  读取Excel的指定工作表
     *
     * @param fileName 文件名
     * @param pageNum 页码
     * @param pageSize 条数
     * @param sheetName 工作表名称
     * @return
     */
    @RequestMapping(value = "getSheetDataBySheetName",method = RequestMethod.POST)
    public PageResult getSheetDataBySheetName(String fileName, int pageNum, int pageSize, String sheetName) {

        return ciInitializationService.getSheetDataBySheetName(fileName,pageNum,pageSize,sheetName);
    }

    /**
     * 下载指定工作表的CI数据
     *
     * @param fileName 文件名
     * @param sheetName 工作表名称
     * @return
     */
    @RequestMapping(value = "importCiBySheetName",method = RequestMethod.POST)
    public Map<String,Object> importCiBySheetName(String fileName,String sheetName) {

        return ciInitializationService.importCiBySheetName(fileName,sheetName);
    }


}
