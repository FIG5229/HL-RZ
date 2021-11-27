package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.PageResult;
import com.integration.entity.Role;
import com.integration.service.MenuService;
import com.integration.service.RoleService;
import com.integration.utils.MyPagUtile;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
* @Package: com.integration.controller
* @ClassName: RoleController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 角色管理
*/
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 新增角色
     *
     * @param role
     * @param request
     * @return
     */
    @OptionLog(desc = "新增角色", module = "角色模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Object addRole(Role role, String[] list, HttpServletRequest request) {
        Role result = new Role();
        //增加校验
        if (role.getRole_mc() != null) {
            int n = roleService.getRoleNameBymc(role.getRole_mc());
            if (n > 0) {
                PageResult.setMessage("名称已存在", false);
                return result;
            }
        }
        role.setCjr_id(TokenUtils.getTokenUserId());
        result = roleService.addRole(role, list);

        if (result != null) {
            PageResult.setMessage("添加成功", true);
            return result;
        } else {
            PageResult.setMessage("添加失败", false);
            return result;
        }

    }

    /**
     * 根据角色名称模糊查询角色列表
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/findRoleAll", method = RequestMethod.GET)
    public Object findRoleAll(String name) {
        List<Role> list = roleService.findRoleAll(name);
        return list;
    }

    /**
     * 分页查角色
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/findRolePage", method = RequestMethod.GET)
    public Object findRolePage(String name) {
        MyPagUtile.startPage();
        return MyPagUtile.getPageResult(roleService.findRoleAll(name));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @OptionLog(desc = "删除角色", module = "角色模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public Object deleteRole(Long id) {

        int bool = roleService.deleteRole(id);
        if (bool > 0) {
            PageResult.setMessage("删除成功", true);
            return true;
        } else {
            PageResult.setMessage("删除失败", false);
            return false;
        }


    }

    /**
     * 修改角色
     *
     * @param role
     * @param request
     * @return
     */
    @OptionLog(desc = "修改角色", module = "角色模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public Object updateRole(Role role, HttpServletRequest request) {
        Role role1 = roleService.getRoleById(role.getRole_dm());
        if (!role1.getRole_mc().equals(role.getRole_mc())) {
            //增加校验
            if (role.getRole_mc() != null) {
                int n = roleService.getRoleNameBymc(role.getRole_mc());
                if (n > 0) {
                    PageResult.setMessage("名称已存在", false);
                    return false;
                }
            }
        }
        role.setXgr_id(TokenUtils.getTokenUserId());
        int bool = roleService.updateRole(role);
        if (bool > 0) {
            PageResult.setMessage("修改成功", true);
            return true;
        } else {
            PageResult.setMessage("修改失败", false);
            return false;
        }


    }

    /**
     * 根据角色ID获取角色功能菜单列表
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/findRoleMenu", method = RequestMethod.GET)
    public Object findRoleMenu(@RequestParam String roleId) {

        return menuService.MenuRoleAll(roleId);

    }

    /**
     * 修改角色功能菜单
     *
     * @param roleId
     * @param menuList
     * @param request
     * @return
     */
    @OptionLog(desc = "修改角色功能菜单", module = "角色模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateRoleMenu", method = RequestMethod.POST)
    public Object updateRoleMenu(@RequestParam Long roleId,
                                 String[] menuList, String[] cdIds, HttpServletRequest request) {

        String cjr_id = TokenUtils.getTokenUserId();
        boolean result = roleService.updateRoleMenu(roleId, cjr_id,
                menuList, cdIds);
        if (result) {
            PageResult.setMessage("修改成功", true);
            return true;
        } else {
            PageResult.setMessage("修改失败!", false);
            return false;
        }
    }

    /**
     * 根据角色ID获取角色信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findRoleById", method = RequestMethod.GET)
    public Object findRoleById(Long roleId) {

        return roleService.findRoleById(roleId);
    }

}
