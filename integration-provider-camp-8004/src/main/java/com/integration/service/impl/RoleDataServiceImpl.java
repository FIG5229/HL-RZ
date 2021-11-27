package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.integration.dao.RoleDataDao;
import com.integration.entity.RoleData;
import com.integration.fegin.TypeService;
import com.integration.service.RoleDataService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName:  integration
 * @Package:  com.integration.service.impl
 * @ClassName:  RoleDataServiceImpl
 * @Author:  ztl
 * @Date:  2020-06-10
 * @Version:  1.0
 * @description: 角色代码与数据域权限（CI大类）
 */
@Transactional
@Service
public class RoleDataServiceImpl  implements RoleDataService {

    @Resource
    private TypeService typeService;
    @Resource
    private RoleDataDao roleDataDao;
    /**
     *查询数据权限列表
     * @param roleDm 角色代码
     * @param typeName 模糊查询条件
     * @return List<RoleData>
     */
    @Override
    public List<RoleData> findRoleDataList(String roleDm, String typeName) {
        List<Map> typeList = typeService.findTypeListHump();
        List<RoleData> roleDataList = new ArrayList<RoleData>();

        for (Map map :typeList){
            RoleData roleData = new RoleData();
            //CI大类ID
            String ciTypeId = null;
            if (typeName != null && !"".equals(typeName)){
                if (map.get("ciTypeMc").toString().contains(typeName)){
                    ciTypeId = map.get("id").toString();
                    }else {
                        continue;
                    }
               }else {
                ciTypeId = map.get("id").toString();
            }
                //CI大类名称
                String ciTypeMc = map.get("ciTypeMc").toString();
                RoleData roleDataRe = roleDataDao.getRoleDataByRoleDmAndDataId(roleDm,ciTypeId);
                roleData.setRoleDm(roleDm);
                roleData.setDataId(ciTypeId);
                if (roleDataRe != null){
                    if (roleDataRe.getAuthValue()==0){
                        roleData=roleDataRe;
                        roleData.setCiTypeMc(ciTypeMc);
                        roleData.setSearchAuth(false);
                        roleData.setEditAuth(false);
                        roleData.setAddAuth(false);
                        roleData.setDeleteAuth(false);
                    }else {
                        roleData=roleDataRe;
                        roleData.setCiTypeMc(ciTypeMc);
                        String[] str =  String.valueOf(roleDataRe.getAuthValue()).split("");
                        if ("1".equals(str[0])){
                            roleData.setSearchAuth(true);
                        }
                        if ("1".equals(str[1])){
                            roleData.setEditAuth(true);
                        }else {
                            roleData.setEditAuth(false);
                        }
                        if ("1".equals(str[2])){
                            roleData.setDeleteAuth(true);
                        }else {
                            roleData.setDeleteAuth(false);
                        }
                        if ("1".equals(str[3])){
                            roleData.setAddAuth(true);
                        }else {
                            roleData.setAddAuth(false);
                        }
                    }

                }else {
                    roleData.setCiTypeMc(ciTypeMc);
                    roleData.setSearchAuth(false);
                    roleData.setEditAuth(false);
                    roleData.setAddAuth(false);
                    roleData.setDeleteAuth(false);
                }
                roleDataList.add(roleData);
            }

        return roleDataList;
    }
    /**
     * 保存或更新 角色对应的数据权限
     * @param roleDataList 数据权限列表
     * @param roleDm 角色代码
     * @return 返回是否成功对象
     */
    @Override
    public boolean updateRoleData(String roleDataList, String roleDm) {
        //前端传入数据权限列表
        List<RoleData> roleData = JSON.parseArray(roleDataList, RoleData.class);
        //需新增数据权限列表
        List<RoleData> roleDataAdd = new ArrayList<RoleData>();
        //需修改数据权限列表
        List<RoleData> roleDataUpdate = new ArrayList<RoleData>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //循环处理传入数据
        for (RoleData rd : roleData){
            String res = "";
            if (!rd.getSearchAuth()){
                rd.setAuthValue(0);
            }else {
                res += "1";
                if (rd.getEditAuth()){
                    res += "1";
                }else{
                    res += "0";
                }
                if (rd.getDeleteAuth()){
                    res += "1";
                }else {
                    res += "0";
                }
                if (rd.getAddAuth()){
                    res += "1";
                }else {
                    res += "0";
                }
                rd.setAuthValue(Integer.parseInt(res));
            }
            if (rd.getId()==null || "".equals(rd.getId())){
                rd.setId(SeqUtil.nextId().toString());
                rd.setCjrId(TokenUtils.getTokenUserId());
                rd.setCjsj(sdf.format(new Date()));
                roleDataAdd.add(rd);
            }else{
                rd.setXgrId(TokenUtils.getTokenUserId());
                rd.setXgsj(sdf.format(new Date()));
                roleDataUpdate.add(rd);
            }
        }
        int result1 = 0;
        int result = 0;
        if (roleDataAdd.size()>0 && roleDataAdd != null){
            result1 = roleDataDao.addRoleData(roleDataAdd);
        }
        if (roleDataUpdate.size()>0 && roleDataUpdate != null){
           result = roleDataDao.updateRoleData(roleDataUpdate);
        }

        return result > 0 || result1 > 0;

    }

    /**
     * 根数CI大类ID获取数据权限
     * @param dataId CI大类ID
     * @return
     */
    @Override
    public RoleData findRoleDataByDataId(String dataId) {
        //根据用户ID和CI大类ID获取该用户所有角色的权限
        List<RoleData> roleDataList = roleDataDao.findRoleDataByDataId(TokenUtils.getTokenUserId(),dataId);
        RoleData roleData = new RoleData();
        boolean searchAuth = false;
        boolean editAuth = false;
        boolean deleteAuth = false;
        boolean addAuth = false;
        for (RoleData rd : roleDataList){
            if (rd.getAuthValue()==0){
                continue;
            }else{
                String[] str = String.valueOf(rd.getAuthValue()).split("");
                if ("1" .equals(str[0]) && !searchAuth){
                    searchAuth = true;
                }
                if ("1".equals(str[1]) && !editAuth){
                    editAuth = true;
                }
                if ("1".equals(str[2]) && !deleteAuth){
                    deleteAuth = true;
                }
                if ("1".equals(str[3]) && !addAuth){
                    addAuth = true;
                }
            }
        }
        roleData.setSearchAuth(searchAuth);
        roleData.setEditAuth(editAuth);
        roleData.setDeleteAuth(deleteAuth);
        roleData.setAddAuth(addAuth);
        roleData.setDataId(dataId);
        return roleData;
    }

    /**
     * 获取当前登录用户最高数据权限列表
     * @return
     */
    @Override
    public List<RoleData> findRoleHighDataList() {
        List<RoleData> roleDataList = new ArrayList<RoleData>();
        //获取CI大类列表
        List<Map> typeList = typeService.findTypeListHump();
        for (Map map :typeList) {
            RoleData roleData = new RoleData();
            //CI大类ID
            String ciTypeId = map.get("id").toString();
            roleData = findRoleDataByDataId(ciTypeId);
            roleDataList.add(roleData);
        }
        return roleDataList;
    }
}

































