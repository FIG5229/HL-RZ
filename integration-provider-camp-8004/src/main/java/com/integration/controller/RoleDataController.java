package com.integration.controller;

import com.integration.entity.PageResult;
import com.integration.entity.RoleData;
import com.integration.service.RoleDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.controller
 * @ClassName: RoleDataController
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:角色代码与数据域权限（CI大类）
 */
@RestController
@RequestMapping("/roleData")
public class RoleDataController {

    @Resource
    private RoleDataService roleDataService;
    /**
     *查询数据权限列表
     * @param roleDm 角色代码
     * @param typeName 模糊查询条件
     * @return
     */
    @RequestMapping(value = "/findRoleDataList",method = RequestMethod.GET)
    public List<RoleData> findRoleDataList(String roleDm,String typeName){
        return roleDataService.findRoleDataList(roleDm,typeName);
    }

    /**
     * 保存或更新 角色对应的数据权限
     * @param roleDataList 数据权限列表
     * @param roleDm 角色代码
     * @return 返回是否成功对象
     */
    @RequestMapping(value = "/updateRoleData",method = RequestMethod.POST)
    public Object updateRoleData(String roleDataList,String roleDm ){

        boolean result = roleDataService.updateRoleData(roleDataList,roleDm);
        if (result){
            PageResult.success("修改成功！");
        }else{
            PageResult.fail("修改失败！");
        }
        return result;
    }

    /**
     *根数CI大类ID获取数据权限
     * @param dataId CI大类ID
     * @return
     */
    @RequestMapping(value = "/findRoleDataByDataId",method = RequestMethod.GET)
    public RoleData findRoleDataByDataId(String dataId){

        return  roleDataService.findRoleDataByDataId(dataId);
    }

    /**
     * 获取当前登录用户最高数据权限列表
     * @return
     */
    @RequestMapping(value = "/findRoleHighDataList",method = RequestMethod.GET)
    public List<RoleData> findRoleHighDataList(){

        return roleDataService.findRoleHighDataList();
    }


}
