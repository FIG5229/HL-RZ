package com.integration.service;

import com.integration.entity.CiLabel;
import com.integration.entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: CiLabelService
 * @Author: ztl
 * @Date: 2020-12-03
 * @Version: 1.0
 * @description:CI标签
 */
public interface CiLabelService {
    List<Map<String, Object>> getCitypeAndItemList();

    List<String> getAttrValuesList(String ciTypeId, String mpCiItem, String searchCondition);

    List<Map<String, Object>> getLabelTree(String labelName);

    Map<String, Object> getLabelById(String labelId);

    boolean addOrUpdateLabel(CiLabel ciLabel);

    boolean deleteLabelById(String id);

    PageResult getLabelCiInfo(String labelId);
}
