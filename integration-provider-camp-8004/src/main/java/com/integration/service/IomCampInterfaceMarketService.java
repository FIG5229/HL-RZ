package com.integration.service;

import com.integration.entity.IomCampInterfaceMarket;
import com.integration.entity.PageResult;

import java.math.BigDecimal;

/**
 * @ClassName IomCampInterfaceMarketService
 * @Description //接口超市服务
 * @Author zhangfeng
 * @Date 2020/12/7 14:12
 * @Version 1.0
 **/
public interface IomCampInterfaceMarketService {

    /**
     * 新增记录
     * @return
     */
    PageResult save(IomCampInterfaceMarket iomCampInterfaceMarket);


    /**
     * 修改记录
     * @return
     */
    PageResult update(IomCampInterfaceMarket iomCampInterfaceMarket);


    /**
     * 修改密码
     * @return
     */
    PageResult updatePass(IomCampInterfaceMarket iomCampInterfaceMarket);




    /**
     * 删除记录
     * @return
     */
    PageResult deleteById(String id);


    /**
     * 查询记录列表
     * @return
     */
    PageResult list(IomCampInterfaceMarket iomCampInterfaceMarket, Integer pageSize, Integer pageNum);

    /**
     * 查询记录列表
     * @return
     */
    PageResult list(IomCampInterfaceMarket iomCampInterfaceMarket);


    /**
     * 查询记录
     * @return
     */
    PageResult info(String id);

    PageResult selectOpenApi(IomCampInterfaceMarket record);
}
