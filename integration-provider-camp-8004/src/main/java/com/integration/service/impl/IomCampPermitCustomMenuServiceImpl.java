package com.integration.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.integration.entity.CustMenuEntity;
import com.integration.generator.dao.IomCampPermitCustomMenuMapper;
import com.integration.generator.entity.IomCampPermitCustomMenu;
import com.integration.generator.entity.IomCampPermitCustomMenuExample;
import com.integration.service.IomCampPermitCustomMenuService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: IomCampPermitCustomMenuServiceImpl
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:该表用于设置允许添加自定义菜单的功能菜单信息
 */
@Transactional
@Service
public class IomCampPermitCustomMenuServiceImpl implements IomCampPermitCustomMenuService {
    @Resource
    private IomCampPermitCustomMenuMapper iomCampPermitCustomMenuMapper;

    /**
     *保存可选结构菜单
     * @param json 菜单数据
     * @return
     *
     * @description: 首先将传进来的数据中的menuId组装，查询用户已有的tab列表，循环该列表是否在传入的
     *               数据中，若不存在则加入删除列表，循环删除自定义常用菜单表
     */
    @Override
    public Integer saveStructureMenuByCzryId(String json) {
        String userId= TokenUtils.getTokenUserId();
        List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(json);
        List<IomCampPermitCustomMenu> deleteList = new ArrayList<IomCampPermitCustomMenu>();
        String iomCampPermitCustomMenuListId = "";
        for (Map<String,String> map : listObjectFir){
            iomCampPermitCustomMenuListId += map.get("menuId")+",";
        }
        //先查询出当前用户所拥有的tab
        IomCampPermitCustomMenuExample exampleSelect=new IomCampPermitCustomMenuExample();
        exampleSelect.createCriteria().andCjrIdEqualTo(userId).andYxbzEqualTo(1);
        //当前用户所拥有的tab列表
        List<IomCampPermitCustomMenu> mapList = iomCampPermitCustomMenuMapper.selectByExample(exampleSelect);
        if (mapList != null){
            for (IomCampPermitCustomMenu maps : mapList){
                if (!iomCampPermitCustomMenuListId.contains(maps.getMenuId())){
                    deleteList.add(maps);
                }
            }
        }
        //循环删除自定义常用菜单表
        for(IomCampPermitCustomMenu list : deleteList){
            iomCampPermitCustomMenuMapper.deleteCustomMenu(list.getMenuId(),userId);
        }
        //新增之前把之前的数据删除---start
        IomCampPermitCustomMenuExample example=new IomCampPermitCustomMenuExample();
        example.createCriteria().andCjrIdEqualTo(userId).andYxbzEqualTo(1);
        iomCampPermitCustomMenuMapper.deleteByExample(example);
        //新增之前把之前的数据删除---end
        Integer i=0;
        for(Map<String,String> map:listObjectFir) {
            String menuId=map.get("menuId");
            String menuName=map.get("menuName");
            IomCampPermitCustomMenu iomCampPermitCustomMenu=new IomCampPermitCustomMenu();
            iomCampPermitCustomMenu.setId(SeqUtil.nextId()+"");
            iomCampPermitCustomMenu.setMenuId(menuId);
            iomCampPermitCustomMenu.setMenuName(menuName);
            iomCampPermitCustomMenu.setCjrId(userId);
            iomCampPermitCustomMenu.setCjsj(new Date());
            iomCampPermitCustomMenu.setYxbz(1);
            i=iomCampPermitCustomMenuMapper.insert(iomCampPermitCustomMenu);
        }
        if(listObjectFir.size()==0){
            i=1;
        }
        return i;
    }

    /**
     *根据菜单ID删除Tab
     * @param menuId 菜单ID
     * @return
     */
    @Override
    public Integer deleteTabByMenuId(String menuId) {
        String userId= TokenUtils.getTokenUserId();
        //根据menuId和userId删除该用户的自定义菜单
        iomCampPermitCustomMenuMapper.deleteCustomMenu(menuId,userId);
        IomCampPermitCustomMenuExample example=new IomCampPermitCustomMenuExample();
        example.createCriteria().andCjrIdEqualTo(userId).andMenuIdEqualTo(menuId);
        //删除该用户的单条菜单
        int i=iomCampPermitCustomMenuMapper.deleteByExample(example);
        return i;
    }

    @Override
    public Integer getMaxSortNum(String userId, String pMenuId) {
        return iomCampPermitCustomMenuMapper.getMaxSortNum(userId,pMenuId);
    }

    @Override
    public boolean addCustMenu(CustMenuEntity custMenuEntity) {
        return iomCampPermitCustomMenuMapper.addCustMenu(custMenuEntity);
    }

    @Override
    public boolean updateCustMenu(CustMenuEntity custMenuEntity) {
        return iomCampPermitCustomMenuMapper.updateCustMenu(custMenuEntity);
    }

    @Override
    public Integer deleteCustMenu(String id) {
        return iomCampPermitCustomMenuMapper.deleteCustMenu(id);
    }
}
