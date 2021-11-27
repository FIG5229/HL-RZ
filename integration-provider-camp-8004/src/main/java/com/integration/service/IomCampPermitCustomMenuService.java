package com.integration.service;

import com.integration.entity.CustMenuEntity;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: IomCampPermitCustomMenuService
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:该表用于设置允许添加自定义菜单的功能菜单信息
 */
public interface IomCampPermitCustomMenuService {

    /**
     * 保存可选结构菜单
     * @param json 菜单数据
     * @return
     */
    public Integer saveStructureMenuByCzryId(String json);

    /**
     * 根据菜单ID删除Tab
     * @param menuId
     * @return
     */
    public Integer deleteTabByMenuId(String menuId);

    Integer getMaxSortNum(String userId, String pMenuId);

    boolean addCustMenu(CustMenuEntity custMenuEntity);

    boolean updateCustMenu(CustMenuEntity custMenuEntity);

    Integer deleteCustMenu(String id);
}
