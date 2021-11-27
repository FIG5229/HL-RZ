package com.integration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.integration.config.IomPlatformParam;
import com.integration.controller.MenuConntroller;
import com.integration.dao.MenuDao;
import com.integration.dao.MenuResDao;
import com.integration.dao.RoleDao;
import com.integration.entity.IomCampLicenseAuth;
import com.integration.entity.Menu;
import com.integration.entity.MenuRes;
import com.integration.generator.dao.IomCampPermitCustomMenuMapper;
import com.integration.generator.entity.IomCampPermitCustomMenuExample;
import com.integration.service.IomCampLicenseAuthService;
import com.integration.service.MenuService;
import com.integration.utils.MD5Utils;
import com.integration.utils.SerialNumberUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
/**
* @Package: com.integration.service.impl
* @ClassName: MenuServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 菜单管理
*/
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuMapper;

    @Resource
    private MenuResDao menuResMapper;

    @Autowired
    private IomCampLicenseAuthService authService;

     /**
      * 验证菜单的盐
      */
    private static String salt = "qwer1234";

    private boolean check = IomPlatformParam.LICENSES_ENABLE;

    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 查询所有菜单集合
     */
    @Override
    public List<Menu> selectMenuList() {
        // TODO Auto-generated method stub
        return menuMapper.selectMenuList();
    }

    /**
     * 查询所有菜单集合
     */
    @Override
    public List<Map> selectMenuListMap() {
        // TODO Auto-generated method stub
        return menuMapper.selectMenuListMap();
    }

    /**
     * 查询菜单集合,返回叶子节点
     */
    @Override
    public List<Map> selectMenuEndLevleListMap() {
        // TODO Auto-generated method stub
        return menuMapper.selectMenuEndLevleListMap();
    }

    /**
     * 根据登录用户角色查询所拥有的菜单集合
     */
    @Override
    public List<Menu> selectMenuListByCzryRole(String CzryId,String type) {
        // TODO Auto-generated method stub

        List<Menu> menus = menuMapper.selectMenuListByCzryRole(CzryId, type);
        List<Menu> newmenus = menuAllowFilter(menus);
        return newmenus;
    }
    
    /**
               * 根据登录用户角色查询所拥有的菜单集合(如果是超级管理员)
     */
    @Override
    public List<Menu> selectMenuListByCzryRoleToSuperAdmin(String type) {
        // TODO Auto-generated method stub

        List<Menu> menus = menuMapper.selectMenuListByCzryRoleToSuperAdmin(type);
        List<Menu> newmenus = menuAllowFilter(menus);
        return newmenus;
    }


    /**
     * 层级菜单
     *
     * @return
     */
    @Override
    public List<Menu> selectMenuListAll(List<Menu> menuList) {
        // 根节点
        List<Menu> rootMenu = new ArrayList<Menu>();
        if (menuList != null && !menuList.isEmpty()) {
            // 获取父节点
            for (Menu nav : menuList) {
                if ("0".equals(nav.getSj_gncd_dm())) {
                    // 父节点是0的，为根节点。
                    if(nav.isDisplay_flag()){
                        rootMenu.add(nav);
                    }
                }
            }
            // 为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法 */
                List<Menu> childList = getChild(nav.getGncd_dm(), menuList);
                // 给根节点设置子节点
                nav.setChildren(childList);
            }
        }
        //验证菜单集合是否被串改
        if(!validate()){
            return Collections.emptyList();
        }

        return rootMenu;
    }


    @Override
    public List<Map> getCustMenuListMap(){
        return menuMapper.getCustMenuListMap(TokenUtils.getTokenUserId());
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Menu> getChild(String id, List<Menu> allMenu) {
        // 子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            // 相等说明：为该根节点的子节点。
            if (nav.getSj_gncd_dm().equals(id)) {
                if(nav.isDisplay_flag()){
                    childList.add(nav);
                }
            }
        }
        // 递归
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getGncd_dm(), allMenu));
        }
        // 如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Menu>();
        }
        return childList;
    }

    /**
     * 删除菜单
     */
    @Override
    public int deleteMenuByKey(String gncdDm) {
        List<Map<String,Object>> getGncdBySjgncd = menuMapper.getGncdBySjgncd(gncdDm);
        List<MenuRes> menuRes = selectMenuResListByDm(gncdDm, null);
        //没有挂载资源和没有子集的节点可以删除
        if((getGncdBySjgncd ==null || getGncdBySjgncd.size() == 0)&&(menuRes ==null || menuRes.size() == 0)){
        	 menuMapper.deleteMenuByKey(gncdDm);
        	 menuMapper.deleteRoleMenuByid(gncdDm);
        	 return 1;
        }
        return 0;
    }

    /**
     * 新增菜单
     */
    @Override
    public int insertMenu(Menu menu) {
        // TODO Auto-generated method stub
        int result = menuMapper.insertMenu(menu);

        return result;
    }

    /**
     * 根据操作人员id查询菜单
     */
    @Override
    public Menu selectMenuByKey(Integer gncdDm) {
        // TODO Auto-generated method stub
        return menuMapper.selectMenuByKey(gncdDm);
    }

    /**
     * 修改菜单
     */
    @Override
    public int updateMenu(Menu menu) {
        // TODO Auto-generated method stub
        int result = menuMapper.updateMenu(menu);
        if(result > 0){
            menuMapper.updateCustomerMenuName(menu);
            menuMapper.updateCampPermitCustomMenuName(menu.getGncd_mc(), menu.getGncd_dm());
        }
        return result;
    }

    /**
     * 根据操作人员查询菜单资源
     */
    @Override
    public List<MenuRes> selectMenuResListByDm(String gncdDm,String search) {
        // TODO Auto-generated method stub
        List<MenuRes> menuRes = menuResMapper.selectMenuResListByDm(gncdDm, search);
        for (MenuRes menuRe : menuRes) {
            if (menuRe.getCjsj() != null){
                String replace = menuRe.getCjsj().replace(".0", "");
                menuRe.setCjsj(replace);
            }
            if (menuRe.getXgsj() != null){
                String replace = menuRe.getXgsj().replace(".0", "");
                menuRe.setXgsj(replace);
            }
        }
        return menuRes;
    }

    /**
     * 根据id删除菜单资源
     */
    @Override
    public int deleteMenuResByKey(Integer resDm) {
        // TODO Auto-generated method stub
        int result = menuResMapper.deleteMenuResByKey(resDm);

        return result;
    }

    /**
     * 添加菜单资源
     */
    @Override
    public int insertMenuRes(MenuRes menuRes) {
        // TODO Auto-generated method stub
        int result = menuResMapper.insertMenuRes(menuRes);

        return result;
    }

    /**
     * 根据菜单资源id查询菜单资源
     */
    @Override
    public MenuRes selectMenuResByKey(Integer resDm) {
        // TODO Auto-generated method stub
        return menuResMapper.selectMenuResByKey(resDm);
    }

    /**
     * 更新菜单资源
     */
    @Override
    public int updateMenuRes(MenuRes menuRes) {
        // TODO Auto-generated method stub
        int result = menuResMapper.updateMenuRes(menuRes);

        return result;
    }

    /**
     * 获取角色所有功能菜单
     */
    @Override
    public Map MenuRoleAll(String roleId) {

        Map<String, List<Menu>> map = new HashMap<String, List<Menu>>();
        List<Menu> listMenu = menuMapper.selectMenuByRoleid(roleId);
        List<Menu> menuAll = selectMenuList();
        map.put("all", selectMenuListAll(menuAll));
        map.put("check", selectMenuList(listMenu));
        return map;
    }

	/**
	 * 获取底层菜单
	 *
	 * @return
	 */
	public List<Menu> selectMenuList(List<Menu> menuList) {
		if (menuList != null && !menuList.isEmpty()) {
			List<String> sjGncdDms = new ArrayList<String>();
			for (Menu menu1 : menuList) {
				for (Menu menu2 : menuList) {
					if (menu1.getGncd_dm().equals(menu2.getSj_gncd_dm())) {
						sjGncdDms.add(menu1.getGncd_dm());
					}
				}
			}
			for (int i = menuList.size()-1; i >= 0; i--) {
				for (String sjGncdDm : sjGncdDms) {
					if (menuList.get(i).getGncd_dm().equals(sjGncdDm)) {
						menuList.remove(i);
						break;
					}
				}
			}
		}
		return menuList;
	}

    /**
     * 根据用户角色查询菜单，不区分菜单类型（业务菜单，基础管理菜单）
     * @param czryId
     * @return
     */
    @Override
    public List<Map> getMenuListByCzryId(String czryId) {
        return menuMapper.getMenuListByCzryId(czryId);
    }

    /**
     * 根据用户角色查询菜单，不区分菜单类型（业务菜单，基础管理菜单）
     * @param czryId
     * @return
     */
    @Override
    public List<Map> getMenuListByCzryId(String czryId, String menuName) {
        return menuMapper.getMenuListByCzryId(czryId, menuName);
    }

    /**
     * 层级菜单
     *
     * @return
     */
    @Override
    public List<Map> selectMenuListAllHump(List<Map> menuList) {
        // 根节点
        List<Map> rootMenu = new ArrayList<Map>();
        if (menuList != null && !menuList.isEmpty()) {
            // 获取父节点
            for (Map map : menuList) {
                if ("0".equals(map.get("sjGncdDm"))) {
                    // 父节点是0的，为根节点。
                    rootMenu.add(map);
                }
            }
            // 为根菜单设置子菜单，getClild是递归调用的
            for (Map map : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法 */
                List<Map> childList = getChildHump(map.get("gncdDm").toString(), menuList);
                // 给根节点设置子节点
                map.put("child" ,childList);
            }
        }
        return rootMenu;
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Map> getChildHump(String id, List<Map> allMenu) {
        // 子菜单
        List<Map> childList = new ArrayList<Map>();
        for (Map map : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            // 相等说明：为该根节点的子节点。
            if (map.get("sjGncdDm").equals(id)) {
                childList.add(map);
            }
        }
        // 递归
        for (Map map : childList) {
            map.put("child", getChildHump(map.get("gncdDm").toString(), allMenu));
        }
        // 如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Map>();
        }
        return childList;
    }

    /**
     * 根据id查询菜单
     * 返回list<Map>
     * @param menuIds
     * @return
     */
    @Override
    public List<Map> getMenuListByIds(String[] menuIds) {
        return menuMapper.getMenuListByIds(menuIds);
    }

    /**
     * 菜单md5字符串
     * @return
     */
    @Override
    public String createMenuStr(){
        List<String> displayList = menuMapper.getDisplayDmList();
        displayList.sort((a,b)->{
            return a.compareTo(b);
        });


        return MD5Utils.encryptPassword(JSONObject.toJSONString(displayList), salt);
    }

    /**
     * 检测授权访问菜单列表是否被改
     * @return
     */
    private boolean validate(){
        List<String> displayList = menuMapper.getDisplayDmList();
        displayList.sort((a,b)->{
            return a.compareTo(b);
        });

        IomCampLicenseAuth record = new IomCampLicenseAuth();
        record = authService.get(record);

        if(StringUtils.isEmpty(record.getMenuStr())){
            return true;
        }

        String target = MD5Utils.encryptPassword(JSONObject.toJSONString(displayList), salt);
        return StringUtils.equalsIgnoreCase(record.getMenuStr(), target);
    }


    private List<Menu> menuAllowFilter(List<Menu> rootMenu){
        if(!check){
            return rootMenu;
        }
        List<String> urlList = new ArrayList<>();
        //接口平台菜单需要放行
        urlList.add("/admin-center");
        logger.debug(String.format("授权模块1：%s", JSONObject.toJSONString(SerialNumberUtil.allowModule())));
        SerialNumberUtil.allowModule().stream().forEach(item->{
            IomCampLicenseAuth.module module = IomCampLicenseAuth.module.valueOf(item);
            urlList.add(module.getPreUrl());
        });
        logger.debug(String.format("授权模块：%s", JSONObject.toJSONString(urlList)));
        List<Menu> newList = new ArrayList();
        if(rootMenu != null){
            rootMenu.forEach(menu->{
                if(isAllowed(menu.getGncd_url(), urlList)){
                    newList.add(menu);
                }

            });
        }
        logger.debug(String.format("最终列表：%s", JSONObject.toJSONString(newList)));
        return newList;
    }

    public boolean isAllowed(String url, List<String> urlList){
        for (String item: urlList) {
            if(url.contains(item)){
                return true;
            }
        }
        return false;
    }

    private List<Map> menuAllowFilterMap(List<Map> rootMenu){
        if(!check){
            return rootMenu;
        }
        List<String> urlList = new ArrayList<>();
        logger.debug(String.format("授权模块1：%s", JSONObject.toJSONString(SerialNumberUtil.allowModule())));
        SerialNumberUtil.allowModule().stream().forEach(item->{
            IomCampLicenseAuth.module module = IomCampLicenseAuth.module.valueOf(item);
            urlList.add(module.getPreUrl());
        });
        logger.debug(String.format("授权模块：%s", JSONObject.toJSONString(urlList)));
        List<Map> newList = new ArrayList();
        if(rootMenu != null){
            rootMenu.forEach(menu->{
                if(isAllowed(menu.get("urlMenu").toString(), urlList)){
                    newList.add(menu);
                }

            });
        }
        logger.debug(String.format("最终列表：%s", JSONObject.toJSONString(newList)));
        return newList;
    }
}
