package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.*;
import com.integration.fegin.MenuSmvService;
import com.integration.generator.dao.IomCampMenuMapper;
import com.integration.generator.dao.IomCampMenuResMapper;
import com.integration.generator.dao.IomCampPermitCustomMenuMapper;
import com.integration.generator.entity.*;
import com.integration.service.CzryService;
import com.integration.service.IomCampPermitCustomMenuService;
import com.integration.service.MenuService;
import com.integration.utils.DataUtils;
import com.integration.utils.DateUtils;
import com.integration.utils.MyPagUtile;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Package: com.integration.controller
* @ClassName: MenuConntroller
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 菜单管理
*/
@RestController
public class MenuConntroller {

    /**
     * 根据配置属性，显示配置菜单页面
     */
    private boolean menuControllFlag = false;

    @Resource
    private MenuService menuService;

    @Resource
    private IomCampMenuMapper iomCampMenuMapper;

    @Resource
    private IomCampMenuResMapper iomCampMenuResMapper;

    @Autowired
    private CzryService czryService;

    @Autowired
    private MenuSmvService menuSmvService;
    
    @Resource
    private IomCampPermitCustomMenuMapper iomCampPermitCustomMenuMapper;

    @Resource
    private IomCampPermitCustomMenuService iomCampPermitCustomMenuService;

    private final static Logger logger = LoggerFactory.getLogger(MenuConntroller.class);

    /**
     * 查询菜单集合
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMenuList", method = RequestMethod.GET)
    public Map<String, Object> selectMenuList(HttpServletRequest request) {
        Map<String, Object> menuMap = new HashMap<String, Object>();
        List<Menu> rootMenu = menuService.selectMenuListAll(menuService.selectMenuList());
        // 输出构建好的菜单数据。
        menuMap.put("success", "true");
        menuMap.put("list", rootMenu);
        return menuMap;

    }

    /**
     * 根据登录用户权限查询菜单集合
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findMenuListByCzryRole", method = RequestMethod.GET)
    public Map<String, Object> selectMenuListByCzryRole(HttpServletRequest request, @RequestParam String type) {
        String CzryId = TokenUtils.getTokenUserId();
        Czry czry = czryService.findCzryById(CzryId);
        String czryDldm = czry.getCzry_dldm();
        Map<String, Object> menuMap = new HashMap<String, Object>();
        List<Menu> rootMenu = new ArrayList<Menu>();
        if ("sysadmin".equals(czryDldm)) {
            rootMenu = menuService.selectMenuListAll(menuService.selectMenuListByCzryRoleToSuperAdmin(type));
        } else {
            rootMenu = menuService.selectMenuListAll(menuService.selectMenuListByCzryRole(CzryId, type));
        }
        List<Map> mapList = menuService.getCustMenuListMap();

        //判断集合是否为空
        if (mapList != null && mapList.size() > 0) {

            //在8015服务中获取自定义菜单地址
            List<String> menuIdList = mapList.stream().map(map -> map.get("ID").toString()).collect(Collectors.toList());
            String[] ids = menuIdList.toArray(new String[menuIdList.size()]);
            List<Map> listMap = menuSmvService.getCustMenuUrlByIds(ids);

            if (listMap != null){
                for (Map<String, Object> map : mapList) {
                    //添加自定义订单
                    listMap.forEach(lm -> {
                        if (lm.get("id").toString().equals(map.get("ID").toString())){
                            map.put("url", lm.get("appUrl"));
                            return;
                        }
                    });
                    rootMenu = this.menuAddObject(rootMenu, map);
                }
            }
        }
        if(menuControllFlag){
            Menu nav = new Menu();
            nav.setGncd_dm("00000000");
            nav.setGncd_mc("菜单配置");
            nav.setSj_gncd_dm("0");
            List<Menu> children = new ArrayList<>();
            Menu child = new Menu();
            child.setGncd_dm("00000001");
            child.setGncd_mc("菜单配置");
            child.setGncd_img("fa fa-dot-circle-o");
            child.setGnfl(1);
            child.setChildren(Collections.EMPTY_LIST);
            children.add(child);
            nav.setChildren(children);
            rootMenu.add(nav);
        }
        // 输出构建好的菜单数据。
        menuMap.put("success", "true");
        menuMap.put("list", rootMenu);
        return menuMap;
    }

    /**
     * 根据登录用户权限查询菜单集合---smv查询结构菜单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findMenuListByCzryRoleToSmv", method = RequestMethod.GET)
    public Map<String, Object> selectMenuListByCzryRoleToSmv(HttpServletRequest request, @RequestParam String type) {
        String CzryId = TokenUtils.getTokenUserId();
        Czry czry = czryService.findCzryById(CzryId);
        String czryDldm = czry.getCzry_dldm();
        Map<String, Object> menuMap = new HashMap<String, Object>();
        List<Menu> rootMenu = new ArrayList<Menu>();
        //筛选已选择的菜单---start
        IomCampPermitCustomMenuExample example=new IomCampPermitCustomMenuExample();
        example.createCriteria().andCjrIdEqualTo(CzryId).andYxbzEqualTo(1);
        List<IomCampPermitCustomMenu> list=iomCampPermitCustomMenuMapper.selectByExample(example);
        //筛选已选择的菜单---end
        if ("sysadmin".equals(czryDldm)) {
        	List<Menu> list1=menuService.selectMenuListByCzryRoleToSuperAdmin(type);
        	for(Menu menu:list1) {
            	String gncdDm=menu.getGncd_dm();
            	for(IomCampPermitCustomMenu iomCampPermitCustomMenu:list) {
            		String menuId=iomCampPermitCustomMenu.getMenuId();
            		if(gncdDm.equals(menuId)) {
            			menu.setCheck(true);
            		}
            	}
            }
            rootMenu = menuService.selectMenuListAll(list1);           
        } else {
        	List<Menu> list2=menuService.selectMenuListByCzryRole(CzryId, type);
        	for(Menu menu:list2) {
            	String gncdDm=menu.getGncd_dm();
            	for(IomCampPermitCustomMenu iomCampPermitCustomMenu:list) {
            		String menuId=iomCampPermitCustomMenu.getMenuId();
            		if(gncdDm.equals(menuId)) {
            			menu.setCheck(true);
            		}
            	}
            }
            rootMenu = menuService.selectMenuListAll(list2);            
        }       
        // 输出构建好的菜单数据。
        menuMap.put("success", "true");
        menuMap.put("list", rootMenu);
        return menuMap;

    }

    /**
     * 菜单增加对象
     *
     * @param rootMenu
     * @return
     */
    public List<Menu> menuAddObject(List<Menu> rootMenu, Map<String, Object> map) {
        boolean b = false;
        String pMenuId = "";
        if (map.get("pMenuId") != null) {
            pMenuId = map.get("pMenuId").toString();
        }
        int menuType = 0;
        if (map.get("menuType") != null) {
            menuType = Integer.parseInt(map.get("menuType").toString());
        }
        String pFuncMenuId = "";
        if (map.get("pFuncMenuId") != null) {
            pFuncMenuId = map.get("pFuncMenuId").toString();
        }
        for (Menu m : rootMenu) {
            //自定义场景页面和外部应用
            if ((1 == menuType || 2 == menuType) && m.getGncd_dm().equals(pMenuId)) {
                b = true;
            }
            //系统功能菜单
            if (3 == menuType && m.getGncd_dm().equals(pFuncMenuId)) {
                b = true;
            }
            if (b) {
                Menu menu = new Menu();
                menu.setYxbz("1");
                menu.setGncd_dm(map.get("ID").toString());
                if (map.get("menuName") != null && !"".equals(map.get("menuName"))) {
                    menu.setGncd_mc(map.get("menuName").toString());
                } else {
                    menu.setGncd_mc("默认");
                }
                if (map.get("url") != null) {
                    menu.setGncd_url(map.get("url").toString());
                }
                if (1 == menuType) {
                        menu.setGncd_url(menu.getGncd_url()+"&name="+ menu.getGncd_mc());
                }
                if (map.get("menuType") != null && !"".equals(map.get("menuType"))) {
                    menu.setGncd_type(Integer.parseInt(map.get("menuType").toString()));
                }
                if (map.get("menuLevel") != null && !"".equals(map.get("menuLevel"))) {
                    menu.setGncd_level(Integer.parseInt(map.get("menuLevel").toString()));
                }
                menu.setChildren(new ArrayList<>());
                m.getChildren().add(menu);
                break;
            }
            if (m.getChildren() != null && m.getChildren().size() > 0) {
                menuAddObject(m.getChildren(), map);
            }
        }
        return rootMenu;
    }

    /**
     * 根据id查询一个菜单节点信息
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMenuById", method = RequestMethod.GET)
    public Menu findMenuById(String id, HttpServletRequest request) {
        return menuService.selectMenuByKey(Integer.parseInt(id));
    }

    /**
     * 添加菜单节点
     *
     * @param menu
     * @param request
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "添加菜单节点", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addMenu")
    public Object addMenu(Menu menu, HttpServletRequest request) {
        //验证名称唯一校验
        String gncdMc = menu.getGncd_mc();
        String sjGncdDm = menu.getSj_gncd_dm();
        IomCampMenuExample example = new IomCampMenuExample();
        example.createCriteria().andGncdMcEqualTo(gncdMc).andSjGncdDmEqualTo(sjGncdDm).andYxbzEqualTo(1);
        List<IomCampMenu> list = iomCampMenuMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            PageResult.setMessage("同级菜单名称重复！", false);
            return null;
        }
        menu.setGncd_dm(String.valueOf(SeqUtil.nextId()));
        menu.setCjsj(DateUtils.getDate());
        menu.setYxbz("1");
        menu.setCjr_id(TokenUtils.getTokenUserId());
        PageResult.setMessage("添加成功", true);
        return menuService.insertMenu(menu);
    }

    /**
     * 修改菜单节点
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "修改菜单节点", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateMenu")
    public Object updateMenu(Menu menu, HttpServletRequest request) {
        menu.setXgr_id(TokenUtils.getTokenUserId());
        menu.setXgsj(DateUtils.getDate());
        PageResult.setMessage("修改成功!", true);
        return menuService.updateMenu(menu);
    }

    /**
     * 修改菜单节点父id
     *
     * @param menu
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "修改菜单节点父ID", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateMenuParentId")
    public Object updateMenuParentId(Menu menu) {
        PageResult.setMessage("修改成功!", true);
        return menuService.updateMenu(menu);
    }

    /**
     * 删除菜单节点
     *
     * @param gncd_dm
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "删除菜单节点", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteMenu")
    public Object deleteMenu(String gncd_dm) {
        int result = menuService.deleteMenuByKey(gncd_dm);
        if (result > 0) {
            PageResult.setMessage("删除成功!", true);
        } else {
            PageResult.setMessage("请先删除所属资源和子菜单!", false);
        }
        return result;
    }

    /**
     * 根据id查询一个菜单资源信息
     *
     * @param res_dm
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMenuResById", method = RequestMethod.GET)
    public MenuRes findMenuResById(String res_dm, HttpServletRequest request) {
        return menuService.selectMenuResByKey(Integer.parseInt(res_dm));
    }

    /**
     * 根据功能菜单代码查询资源集合
     *
     * @param gncd_dm
     * @param request
     * @return
     */
    @RequestMapping(value = "/findMenuResListByGncdDm", method = RequestMethod.GET)
    public PageResult findMenuResListByGncdDm(String gncd_dm, String search, HttpServletRequest request) {
        List<MenuRes> menuResList = new ArrayList<MenuRes>();
        MyPagUtile.startPage();
        try {
            menuResList = menuService.selectMenuResListByDm(gncd_dm, search);

            return MyPagUtile.getPageResult(menuResList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            return DataUtils.returnPr(false, "查询异常!");
        }

    }

    /**
     * 添加菜单资源
     *
     * @param menuRes
     * @param request
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "添加菜单资源", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addMenuRes", method = RequestMethod.POST)
    public Object addMenuRes(MenuRes menuRes, HttpServletRequest request) {
        String resMc = menuRes.getRes_mc();
        String gncdDm = menuRes.getGncd_dm();
        IomCampMenuResExample example = new IomCampMenuResExample();
        example.createCriteria().andResMcEqualTo(resMc).andYxbzEqualTo(1).andGncdDmEqualTo(gncdDm);
        List<IomCampMenuRes> list = iomCampMenuResMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            PageResult.setMessage("名称不能重复!", false);
            return 0;
        }
        menuRes.setRes_dm(SeqUtil.nextId() + "");
        menuRes.setYxbz("1");
        menuRes.setCjsj(DateUtils.getDate());
        menuRes.setCjr_id(TokenUtils.getTokenUserId());
        PageResult.setMessage("添加成功!", true);
        return menuService.insertMenuRes(menuRes);
    }

    /**
     * 修改菜单资源
     *
     * @param menuRes
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "修改菜单资源", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateMenuRes")
    public Object updateMenuRes(MenuRes menuRes, HttpServletRequest request) {
        menuRes.setXgsj(DateUtils.getDate());
        menuRes.setXgr_id(TokenUtils.getTokenUserId());
        PageResult.setMessage("更新成功!", true);
        return menuService.updateMenuRes(menuRes);
    }

    /**
     * 删除菜单资源
     *
     * @param
     * @return
     */
    @ResponseBody
    @OptionLog(desc = "删除菜单资源", module = "菜单模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteMenuRes")
    public Object deleteMenuRes(String res_dm) {
        MenuRes mr = new MenuRes();
        mr.setRes_dm(res_dm);
        mr.setYxbz("0");
        PageResult.setMessage("删除成功!", true);
        return menuService.updateMenuRes(mr);
    }

    /**
     * 根据登录用户权限查询菜单集合，不区分菜单类型（业务菜单，基础管理菜单）.
     * 返回叶子节点，非树结构, 驼峰命名
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getMenuListByCzryId", method = RequestMethod.POST)
    public List<Map> getMenuListByCzryId(String czryId, String menuName) {
        List<Map> result = new ArrayList<>();
        String czryDldm = czryService.findCzryById(czryId).getCzry_dldm();
        if ("sysadmin".equals(czryDldm)) {
            result = menuService.selectMenuEndLevleListMap();
        } else {
            result = menuService.getMenuListByCzryId(czryId, menuName);
        }
        return result;
    }

    /**
     * 根据登录用户权限查询菜单集合，不区分菜单类型（业务菜单，基础管理菜单）
     * 返回树结构，驼峰命名
     *
     * @param menuName 可为空
     * @param czryId   可为空
     * @return
     */
    @RequestMapping(value = "/getMenuListByCzryIdTree", method = RequestMethod.POST)
    public List<Map> getMenuListByCzryIdTree(@RequestParam("czryId") String czryId, @RequestParam("menuName") String menuName) {
        List<Map> result = new ArrayList<Map>();
        String czryDldm = czryService.findCzryById(czryId).getCzry_dldm();
        if ("sysadmin".equals(czryDldm)) {
            result = menuService.selectMenuListAllHump(menuService.selectMenuListMap());
        } else {
            result = menuService.selectMenuListAllHump(menuService.getMenuListByCzryId(czryId, menuName));
        }
        return result;
    }

    /**
     * 根据id查询菜单
     * 返回list<Map>
     *
     * @param menuIds
     * @return
     */
    @RequestMapping(value = "/getMenuListByIds", method = RequestMethod.POST)
    public List<Map> getMenuListByIds(String[] menuIds) {
        return menuService.getMenuListByIds(menuIds);
    }
    
    /**
     * 保存可选结构菜单-smv
     * 
     *
     * @return
     */
    @RequestMapping(value = "/saveStructureMenuByCzryId", method = RequestMethod.POST)
    public Integer saveStructureMenuByCzryId(@RequestParam String json) {
        Integer i=0;
    	i= iomCampPermitCustomMenuService.saveStructureMenuByCzryId(json);
        return i;
    }
    
    /**
     * 保存可选结构菜单-smv
     * 
     * @return
     */
    @RequestMapping(value = "/querySceneCustomTabByCjryId", method = RequestMethod.GET)
    public List<IomCampPermitCustomMenu> querySceneCustomTabByCjryId() {
    	String userId=TokenUtils.getTokenUserId();
    	List<IomCampPermitCustomMenu> list=iomCampPermitCustomMenuMapper.selectByUserId(userId);
    	return list;
    }

    /**
     *根据菜单ID删除Tab-smv
     *
     * @param menuId 菜单ID
     * @return
     */
    @RequestMapping(value = "/deleteTabByMenuId", method = RequestMethod.POST)
    public Integer deleteTabByMenuId(String menuId) {
        Integer i=0;
        i= iomCampPermitCustomMenuService.deleteTabByMenuId(menuId);
        return i;
    }

    @RequestMapping("/menuStr")
    public PageResult getMenuStr(){
        return DataUtils.returnPr(menuService.createMenuStr());
    }

    /**
     * 查询菜单最大排序
     * @param
     * @return
     */
    @RequestMapping(value = "/getMaxSortNum", method = RequestMethod.POST)
    public Integer getMaxSortNum(String userId,String pMenuId) {
        Integer i=0;
        i= iomCampPermitCustomMenuService.getMaxSortNum(userId,pMenuId);
        return i;
    }

    /**
     * 增加自定义菜单（我的事件台增加菜单）
     * @param custMenuEntity
     * @return
     */
    @RequestMapping(value = "/addCustMenu", method = RequestMethod.POST)
    public boolean addCustMenu(@RequestBody CustMenuEntity custMenuEntity) {
        boolean result= iomCampPermitCustomMenuService.addCustMenu(custMenuEntity);
        return result;
    }

    /**
     * 修改自定义菜单（我的事件台）
     * @param custMenuEntity
     * @return
     */
    @RequestMapping(value = "/updateCustMenu", method = RequestMethod.POST)
    public boolean updateCustMenu(@RequestBody CustMenuEntity custMenuEntity) {
        boolean result= iomCampPermitCustomMenuService.updateCustMenu(custMenuEntity);
        return result;
    }
    /**
     * 删除自定义菜单（我的事件台）
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCustMenu", method = RequestMethod.POST)
    public Integer deleteCustMenu(@RequestParam("id")  String id) {
        Integer result= iomCampPermitCustomMenuService.deleteCustMenu(id);
        return result;
    }
}
