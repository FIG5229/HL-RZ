package com.integration.dao;

import com.integration.entity.IomCampInterfaceMarket;

import java.math.BigDecimal;
import java.util.List;
/**
* @Package: com.integration.dao
* @ClassName: IomCampInterfaceMarketMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 接口管理
*/
public interface IomCampInterfaceMarketMapper {

    int deleteByPrimaryKey(String id);

    int insert(IomCampInterfaceMarket record);

    int insertSelective(IomCampInterfaceMarket record);

    IomCampInterfaceMarket selectByPrimaryKey(String id);

    IomCampInterfaceMarket selectOpenApi(IomCampInterfaceMarket record);

    int updateByPrimaryKeySelective(IomCampInterfaceMarket record);

    int updateByPrimaryKeyWithBLOBs(IomCampInterfaceMarket record);

    int updateByPrimaryKey(IomCampInterfaceMarket record);

    List<IomCampInterfaceMarket> selectList(IomCampInterfaceMarket record);
}