package com.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: integration-provider-alarm-8005
 * @Package: com.integration.entity
 * @ClassName: CustMenuEntity
 * @Author: ztl
 * @Date: 2021-02-23
 * @Version: 1.0
 * @description:菜单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustMenuEntity {
    private String id;
    private String menuId;
    private String menuName;
    private String menuType;
    private String pMenuId;
    private String pFuncMenuId;
    private String url;
    private String menuLevel;
    private String sort;
    private String cjrId;
    private String cjsj;
    private String xgrId;
    private String xgsj;
    private String yxbz;
    /**
     * 以下为新增已添加场景中所需字段 start
     */
    private String layoutMode;
    private String gncdDm;
    private String gncdMc;
    private String gncdUrl;
    private String userName;
    private String userPass;
    private String isLogin;
    /**
     * 以下为新增已添加场景中所需字段 end
     */
}
