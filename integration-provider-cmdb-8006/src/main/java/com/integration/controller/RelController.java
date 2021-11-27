package com.integration.controller;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.PageResult;
import com.integration.entity.Rel;
import com.integration.generator.dao.IomCiDataRelMapper;
import com.integration.generator.dao.IomCiTypeRelMapper;
import com.integration.generator.entity.IomCiDataRel;
import com.integration.generator.entity.IomCiDataRelExample;
import com.integration.generator.entity.IomCiTypeRel;
import com.integration.generator.entity.IomCiTypeRelExample;
import com.integration.service.RelService;
import com.integration.utils.DataUtils;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @Package: com.integration.controller
* @ClassName: RelController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 关系管理
*/
@RestController
public class RelController {
    @Autowired
    public RelService rs;
    @Resource
    private IomCiDataRelMapper iomCiDataRelMapper;

    /**
     * 查询集合
     *
     * @return
     */
    @RequestMapping(value = "/findRelList", method = RequestMethod.GET)
    public List<Rel> findRelList(@RequestParam Map map) {
        List<Rel> relList = new ArrayList<Rel>();
        relList = rs.getAllList(map);
        return relList;
    }

    /**
     * 校验唯一性
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getRekByIdAndName", method = RequestMethod.GET)
    public PageResult getRekByIdAndName(@RequestParam Map map) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        List<Rel> relList = new ArrayList<Rel>();
        Map<String, String> mapId = new HashMap<>();
        if (map.get("id") != null) {
            mapId.put("id", map.get("id").toString());
            relList = rs.getAllListNoLike(mapId);
            if (relList != null && relList.size() > 0) {
                if (relList.get(0).getLine_name().equals(map.get("line_name"))) {
                    return DataUtils.returnPr(true, "SUCCESS", true);
                }
            }
        }
        Map m = new HashMap();
        m.put("line_name", map.get("line_name"));
        m.put("domainId", domainId);
        relList = rs.getAllListNoLike(m);
        if (relList.size() > 0) {
            return DataUtils.returnPr(false, "已存在此关系", false);
        }
        return DataUtils.returnPr(true, "SUCCESS", true);
    }

    /**
     * 根据id获取去单条数据
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/findRelById", method = RequestMethod.GET)
    public Rel findRelById(@RequestParam Map map) {
        Rel rel = new Rel();
        rel = rs.getInfo(map);
        return rel;
    }

    /**
     * 增加关系
     *
     * @param rel
     * @return
     */
    @OptionLog(desc = "增加关系", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addRel", method = RequestMethod.POST)
    public boolean addRel(Rel rel, HttpServletRequest request) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        rel.setId(SeqUtil.nextId() + "");
        rel.setLine_bm(rel.getLine_name());
        rel.setCjr_id(TokenUtils.getTokenUserId());
        rel.setCjsj(DateUtils.getDate());
        rel.setYxbz(1);
        rel.setDomain_id(domainId==null?"-1":domainId);
        int result = rs.insertInfo(rel);
        //成功失败标志
        boolean flag;
        if (result > 0) {
            flag = true;
            PageResult.success("添加成功!");
        } else {
            flag = false;
            PageResult.fail("添加失败!");
        }
        return flag;

    }

    /**
     * 修改关系
     *
     * @param rel
     * @param request
     * @return
     */
    @OptionLog(desc = "修改关系", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateRel", method = RequestMethod.POST)
    public boolean updateRel(Rel rel, HttpServletRequest request) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        String orgDomainId = TokenUtils.getTokenOrgDomainId();
        rel.setLine_bm(rel.getLine_name());
        rel.setXgr_id(TokenUtils.getTokenUserId());
        rel.setXgsj(DateUtils.getDate());
        rel.setYxbz(1);
        rel.setDomain_id(domainId==null?"-1":domainId);

        int result = rs.updateInfo(rel);
        IomCiDataRelExample example=new IomCiDataRelExample();
        if (domainId != null){
            example.createCriteria().andYxbzEqualTo(1).andRelIdEqualTo(rel.getId()).andDomainIdIn(Arrays.asList(domainId.split(",")));
        }else{
            example.createCriteria().andYxbzEqualTo(1).andRelIdEqualTo(rel.getId());
        }
        List<IomCiDataRel> list=iomCiDataRelMapper.selectByExample(example);
        String lineName=rel.getLine_name();
        if(list!=null && list.size()>0) {
        	for(IomCiDataRel iomCiDataRel:list) {
        		iomCiDataRel.setRelName(lineName);
        		iomCiDataRel.setDomainId(domainId==null?"-1":domainId);
        		iomCiDataRelMapper.updateByPrimaryKeySelective(iomCiDataRel);
        	}
        }
        //成功失败标志
        boolean flag;
        if (result > 0) {
            flag = true;
            PageResult.success("修改成功!");
        } else {
            flag = false;
            PageResult.fail("修改失败!");
        }
        return flag;

    }

    @Resource
    IomCiTypeRelMapper iomCiTypeRelMapper;

    /**
     * 删除关系
     *
     * @param relId
     * @return
     */
    @OptionLog(desc = "删除关系 ", module = "目录模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteRelById", method = RequestMethod.POST)
    public boolean deleteRel(String relId) {
        IomCiTypeRelExample iomCiTypeRelExample = new IomCiTypeRelExample();
        com.integration.generator.entity.IomCiTypeRelExample.Criteria criteria = iomCiTypeRelExample.createCriteria();
        criteria.andRelIdEqualTo(relId);
        criteria.andYxbzEqualTo(1);
        List<IomCiTypeRel> iomCiTypeRels = iomCiTypeRelMapper.selectByExample(iomCiTypeRelExample);

        if (iomCiTypeRels != null && iomCiTypeRels.size() > 0) {
            PageResult.success("该关系正在被可视化建模中使用，不能删除!");
            return false;
        }

        Rel rel = new Rel();
        rel.setId(relId);
        rel.setYxbz(0);
        int result = rs.updateInfo(rel);
        //成功失败标志
        boolean flag;
        if (result > 0) {
            flag = true;
            PageResult.success("删除成功!");
        } else {
            flag = false;
            PageResult.fail("删除失败!");
        }
        return flag;
    }

}
