package com.integration.service.impl;

import com.integration.dao.CzryDao;
import com.integration.dao.CzryRoleDao;
import com.integration.entity.Czry;
import com.integration.entity.CzryRole;
import com.integration.generator.dao.IomCampCzryMapper;
import com.integration.generator.entity.IomCampCzry;
import com.integration.generator.entity.IomCampCzryExample;
import com.integration.service.CzryService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service.impl
* @ClassName: CzryServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 用户管理
*/
@Transactional
@Service
public class CzryServiceImpl implements CzryService {

    @Resource
    private CzryDao czryMapper;
    @Resource
    private CzryRoleDao czryRoleMapper;

    @Resource
    private IomCampCzryMapper iomCampCzryMapper;

    /**
     * 新增用户
     */
    @Override
    public int addCzry(Czry czry, String[] roleIds, String cjrId) {
        // TODO Auto-generated method stub
        czry.setId(SeqUtil.nextId().toString());
        int a = czryMapper.addCzry(czry);
        String czryId = czry.getId();
        //循环插入用户角色关系
        for (String roleId : roleIds) {
            CzryRole cr = new CzryRole();
            cr.setId(SeqUtil.nextId().toString());
            cr.setRole_dm(roleId);
            cr.setCzry_id(czryId);
            cr.setCjsj(DateUtils.getDate());
            cr.setCjr_id(cjrId);
            czryRoleMapper.addCzryRole(cr);
        }
        return a;
    }

    /**
     * 校验输入信息是否正确
     * @param czry
     * @return
     */
    @Override
    public String checkCzry(Czry czry){
    	String czryDldm = czry.getCzry_dldm();
    	String czryMc = czry.getCzry_mc();
    	//如果没有登录名和用户名，则认为不合法
    	if (StringUtils.isNotEmpty(czryDldm)&&StringUtils.isNotEmpty(czryMc)) {
    		IomCampCzryExample iomCampCzryExample = new IomCampCzryExample();
    		com.integration.generator.entity.IomCampCzryExample.Criteria criteria = iomCampCzryExample.createCriteria();
    		criteria.andCzryDldmEqualTo(czryDldm);
    		criteria.andYxbzEqualTo(1);
    		List<IomCampCzry> iomCampCzries = iomCampCzryMapper.selectByExample(iomCampCzryExample);
    		//如果有登录名重名
    		if (iomCampCzries.size()>0) {
    			//如果是该用户的正确，如果不是该用户的认为是非法操作
    			if (!iomCampCzries.get(0).getId().equals(czry.getId())) {
    				return "登录名重复";
				}
			}
		}else {
			return "登录名和用户名是必填的";
		}
    	return null;
    }

    /**
     * 停用用户
     */
    @Override
    public int deleteCzry(String id) {
        // TODO Auto-generated method stub
        //停用用户
        int result = czryMapper.blockUpCzry(id);
        //删除所有用户关系
        if (result > 0) {
            czryRoleMapper.deleteCzryRoleByCzryId(id);
        }
        return result;
    }

    /**
     * 更新用户
     */
    @Override
    public int updateCzry(Czry czry, String[] roleIds, String cjrId) {
        // TODO Auto-generated method stub

        //删除所有用户关系
        czryRoleMapper.deleteCzryRoleByCzryId(czry.getId());
        //循环插入用户角色关系
        String czryId = czry.getId();
        for (String roleId : roleIds) {
            CzryRole cr = new CzryRole();
            cr.setId(SeqUtil.nextId().toString());
            cr.setRole_dm(roleId);
            cr.setCzry_id(czryId);
            cr.setCjsj(DateUtils.getDate());
            cr.setCjr_id(cjrId);
            czryRoleMapper.addCzryRole(cr);
        }
        //修改用户
        int result = czryMapper.updateCzry(czry);
        return result;

    }

    /**
     * 查询用户列表及用户角色
     */
    @Override
    public List<Czry> findCzryList(String org_id, String dept_id, String search, Integer pageCount, Integer pageIndex) {
        // TODO Auto-generated method stub

        List<Czry> czryList = czryMapper.findCzryListPage(org_id, dept_id, search, pageIndex, pageCount);
        for (Czry czry : czryList) {
            List<CzryRole> roleList = czryRoleMapper.findCzryRoleByCzryId(czry.getId());
            czry.setRoleList(roleList);
        }
        return czryList;
    }

    /**
     * 查询用户总数
     *
     * @return
     */
    @Override
    public int findCzryListCount(String org_id, String dept_id, String search) {
        // TODO Auto-generated method stub
        return czryMapper.findCzryListCount(org_id, dept_id, search);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public Czry findCzryById(String id) {
        return czryMapper.findCzryById(id);
    }

    /**
     * 登录
     *
     * @param czrDldm
     * @param czryPass
     * @return
     */
    @Override
    public Czry login(String czrDldm, String czryPass) {

        return czryMapper.login(czrDldm, czryPass);
    }


    /**
     * 修改状态
     *
     * @param id
     * @param statu
     * @return
     */
    @Override
    public int updateStatus(Integer id, Integer statu) {
        // TODO Auto-generated method stub
        return czryMapper.updateStatus(id, statu);
    }

    /**
     * 重置密码
     *
     * @param czry
     * @param
     * @return
     */
    @Override
    public int resetPWDCzry(Czry czry) {
        // TODO Auto-generated method stub
        return czryMapper.updateCzry(czry);
    }

    /**
     * 通过id串查询用户名
     *
     * @param ids
     * @return
     */
    @Override
    public List<IomCampCzry> findCzryByIds(List<String> ids) {
        IomCampCzryExample example = new IomCampCzryExample();
        IomCampCzryExample.Criteria criteria = example.createCriteria();
        if (null != ids && ids.size() > 0) {
            criteria.andIdIn(ids);
        }
        criteria.andYxbzEqualTo(1);
        example.setOrderByClause("sort");
        return iomCampCzryMapper.selectByExample(example);
    }

    /**
     * 通过id串查询用户名 包含已删除用户
     *
     * @param ids
     * @return
     */
    @Override
    public List<IomCampCzry> findCzryByIdsWithDelete(List<String> ids) {
        IomCampCzryExample example = new IomCampCzryExample();
        IomCampCzryExample.Criteria criteria = example.createCriteria();
        if (null != ids && ids.size() > 0) {
            criteria.andIdIn(ids);
        }
        example.setOrderByClause("sort");
        return iomCampCzryMapper.selectByExample(example);
    }

    /**
     * 查询所有操作人员
     * @param nameOrId
     * @return
     */
    @Override
    public List<Map<String, Object>> getCzrysAll(String nameOrId){
        return czryMapper.getCzrysAll(nameOrId);
    }

    /**
     * 根据操作人id，查询操作人
     * @param listStr
     * @return
     */
    @Override
    public List<Map<String, Object>> getCzrysNameById(String[] listStr){
        return czryMapper.getCzrysNameById(listStr);
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 根据登录人ID获取所有配置角色
     */
    @Override
    public List<String> getRoleIdByCzryId(String id) {
        return czryRoleMapper.getRoleIdByCzryId(id);
    }

}
