package com.integration.service;

import com.integration.entity.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiInitializationService
 * @Author: ztl
 * @Date: 2020-10-15
 * @Version: 1.0
 * @description:CI数据初始化
 */
public interface CiInitializationService {

    List<Map<String, Object>> importExcel(MultipartFile[] multipartFiles);

    PageResult getSheetDataBySheetName(String fileName, int pageNum, int pageSize, String sheetName);

    Map<String,Object> importCiBySheetName(String fileName, String sheetName);
}
