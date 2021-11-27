package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.integration.dao.CiAssociatedFieldConfDao;
import com.integration.entity.CiAssociatedFieldConf;
import com.integration.entity.CiAssociatedTrigger;
import com.integration.entity.PageResult;
import com.integration.entity.Type;
import com.integration.feign.CzryService;
import com.integration.feign.DictService;
import com.integration.rabbit.Sender;
import com.integration.service.CiAssociatedFieldConfService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import com.integration.utils.MyPagUtile;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiAssociatedFieldConfServiceImpl
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:关联字段配置实现
 */
@Transactional
@Service
public class CiAssociatedFieldConfServiceImpl implements CiAssociatedFieldConfService {

    @Resource
    private CiAssociatedFieldConfDao ciAssociatedFieldConfDao;
    @Autowired
    private DictService dictService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeItemService typeItemService;
    @Autowired
    private CzryService czryService;
    @Autowired
    private Sender sender;

    /**
     * 保存修改关联字段配置
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    @Override
    public boolean saveFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        boolean result = true;
        if (ciAssociatedFieldConf.getId()!=null && !"".equals(ciAssociatedFieldConf.getId())){
            //修改逻辑
            ciAssociatedFieldConf.setXgrId(TokenUtils.getTokenUserId());
            result = ciAssociatedFieldConfDao.updateFieldConf(ciAssociatedFieldConf);
        }else{
            //新增逻辑
            ciAssociatedFieldConf.setId(SeqUtil.getId());
            ciAssociatedFieldConf.setCjrId(TokenUtils.getTokenUserId());
            ciAssociatedFieldConf.setXgrId(TokenUtils.getTokenUserId());
            ciAssociatedFieldConf.setYxbz(1);
            ciAssociatedFieldConf.setDomainId(domainId==null?"-1":domainId);
            result = ciAssociatedFieldConfDao.addFieldConf(ciAssociatedFieldConf);
        }
        if (result && ciAssociatedFieldConf.isTrigger()){
            //调用MQ消息推送
            sender.sendCiChgNotice(ciAssociatedFieldConf.getSourceCiId(),ciAssociatedFieldConf.getCiTypeId());
        }
        return result;
    }

    /**
     * 确定保存修改关联字段配置
     *
     * 增加给接口平台推送MQ消息
     *
     * @param ciAssociatedFieldConf
     * @return
     */
    @Override
    public boolean confirmFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        boolean result = true;
        if (ciAssociatedFieldConf.getId()!=null && !"".equals(ciAssociatedFieldConf.getId())){
            //修改逻辑
            ciAssociatedFieldConf.setXgrId(TokenUtils.getTokenUserId());
            result = ciAssociatedFieldConfDao.updateFieldConf(ciAssociatedFieldConf);
        }else{
            //新增逻辑
            ciAssociatedFieldConf.setId(SeqUtil.getId());
            ciAssociatedFieldConf.setCjrId(TokenUtils.getTokenUserId());
            ciAssociatedFieldConf.setXgrId(TokenUtils.getTokenUserId());
            ciAssociatedFieldConf.setYxbz(1);
            ciAssociatedFieldConf.setDomainId(domainId==null?"-1":domainId);
             result = ciAssociatedFieldConfDao.addFieldConf(ciAssociatedFieldConf);
        }
        if (result && ciAssociatedFieldConf.isTrigger()){
            //调用MQ消息推送
            sender.sendCiChgNotice(ciAssociatedFieldConf.getSourceCiId(),ciAssociatedFieldConf.getCiTypeId());
        }
        return result;
    }

    /**
     * 删除关联字段配置信息
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteFieldConf(String id) {
        String xgrId = TokenUtils.getTokenUserId();
        return ciAssociatedFieldConfDao.deleteFieldConf(id,xgrId);
    }

    /**
     * 获取关联字段配置列表
     *
     * @param sourceCiId 来源ID
     * @param ciTypeId 大类ID
     * @return
     */
    @Override
    public PageResult findFieldConfList(String sourceCiId, String ciTypeId) {
        PageResult pageResult = new PageResult();
        String domainId = TokenUtils.getTokenOrgDomainId();
        MyPagUtile.startPage();
        //获取关联字段配置详情列表
        List<CiAssociatedFieldConf> fieldConfList = ciAssociatedFieldConfDao.findFieldConfList(sourceCiId,ciTypeId,domainId);
        //获取数据来源列表
        List<Map<String, Object>> sourceList = dictService.findDiceBySjIdHump("258888");
        for (CiAssociatedFieldConf conf:fieldConfList) {
            //对数据来源进行匹配
            for (Map map:sourceList) {
                if (map.get("dictBm").equals(conf.getSourceCiId())){
                    conf.setSourceCiName(String.valueOf(map.get("dictName")));
                    break;
                }
            }
            //根据CI大类获取CI大类信息
            Type type =typeService.findTypeById(conf.getCiTypeId());
            if (type!=null){
                conf.setCiTypeName(type.getCi_type_mc());
            }
            //根据CI大类属性ID获取CI大类属性名称
            List<String> itemNameList = typeItemService.findItemNameByItemId(Arrays.asList(conf.getCiItemId().split(",")));
            if (itemNameList!=null && itemNameList.size()>0){
                conf.setCiItemName(String.join(",",itemNameList));
            }
            //根据修改人ID获取修改人名称
            String xgrId=conf.getXgrId();
            Object resObject = czryService.findCzryByIdFeign(xgrId);
            if (resObject!=null) {
                JSONObject czry = JSONObject.parseObject(JSON.toJSONString(resObject));
                conf.setXgrName(String.valueOf(czry.get("czry_mc")));
            }
        }
        pageResult.setReturnBoolean(true);
        pageResult.setReturnMessage("查询成功！");
        if (fieldConfList!=null && fieldConfList.size()>0){
            pageResult.setTotalResult((int)((com.github.pagehelper.Page)fieldConfList).getTotal());
            pageResult.setTotalPage(((com.github.pagehelper.Page)fieldConfList).getPages());
        }else{
            pageResult.setTotalResult(0);
            pageResult.setTotalPage(0);
        }
        pageResult.setReturnObject(fieldConfList);
        return pageResult;
    }

    /**
     *
     * 获取关联字段配置详情
     *
     * @param id
     * @return
     */
    @Override
    public CiAssociatedFieldConf findFieldConf(String id) {
        //获取关联字段配置详情
        CiAssociatedFieldConf ciAssociatedFieldConf = ciAssociatedFieldConfDao.findFieldConf(id);
        if (ciAssociatedFieldConf!=null){
            //获取数据来源列表
            List<Map<String, Object>> sourceList = dictService.findDiceBySjIdHump("258888");
            for (Map map:sourceList) {
                //对数据来源进行匹配
                if (map.get("dictBm").equals(ciAssociatedFieldConf.getSourceCiId())){
                    ciAssociatedFieldConf.setSourceCiName(String.valueOf(map.get("dictName")));
                    break;
                }
            }
            //根据CI大类获取CI大类信息
            Type type =typeService.findTypeById(ciAssociatedFieldConf.getCiTypeId());
            if (type!=null){
                ciAssociatedFieldConf.setCiTypeName(type.getCi_type_mc());
            }
            //根据CI大类属性ID获取CI大类属性名称
            List<String> itemNameList = typeItemService.findItemNameByItemId(Arrays.asList(ciAssociatedFieldConf.getCiItemId().split(",")));
            if (itemNameList!=null && itemNameList.size()>0){
                ciAssociatedFieldConf.setCiItemName(String.join(",",itemNameList));
            }
            //根据修改人ID获取修改人名称
            String xgrId=ciAssociatedFieldConf.getXgrId();
            Object resObject = czryService.findCzryByIdFeign(xgrId);
            if (resObject!=null) {
                JSONObject czry = JSONObject.parseObject(JSON.toJSONString(resObject));
                ciAssociatedFieldConf.setXgrName(String.valueOf(czry.get("czry_mc")));
            }
        }
        return ciAssociatedFieldConf;
    }

    /**
     * 触发关联字段配置
     *
     * @param id
     */
    @Override
    public void triggerFieldConf(String id) {
        CiAssociatedFieldConf ciAssociatedFieldConf = ciAssociatedFieldConfDao.findFieldConf(id);
        try{
            CiAssociatedTrigger ciAssociatedTrigger = new CiAssociatedTrigger();
            ciAssociatedTrigger.setId(SeqUtil.getId());
            ciAssociatedTrigger.setConfId(id);
            ciAssociatedTrigger.setCjrId(TokenUtils.getTokenUserId());
            ciAssociatedFieldConfDao.addconfTrigger(ciAssociatedTrigger);
        }catch (Exception e){
            e.printStackTrace();
        }
        //调用MQ消息推送
        sender.sendCiChgNotice(ciAssociatedFieldConf.getSourceCiId(),ciAssociatedFieldConf.getCiTypeId());
    }

    /**
     * 接口平台查询关联字段相关信息
     *
     * @param sourceCiId
     * @param ciTypeId
     * @return
     */
    @Override
    public List<CiAssociatedFieldConf> findConfList(String sourceCiId, String ciTypeId) {
        List<String>  sourceCiIdList = new ArrayList<>();
        List<String>  ciTypeIdList = new ArrayList<>();
        if(sourceCiId!=null){
            sourceCiIdList = new ArrayList<>(Arrays.asList(sourceCiId.split(",")));
        }
        if (ciTypeId!=null){
            ciTypeIdList = new ArrayList<>(Arrays.asList(sourceCiId.split(",")));
        }

        List<CiAssociatedFieldConf> confList = ciAssociatedFieldConfDao.findConfList(sourceCiIdList,ciTypeIdList);
        return confList;
    }

    /**
     * 根据大类ID获取数据来源编码
     *
     * @param ciTypeId
     * @return
     */
    @Override
    public List<String> findSourceCiIdByCiTypeId(String ciTypeId) {
        return ciAssociatedFieldConfDao.findSourceCiIdByCiTypeId(ciTypeId);
    }
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 给接口平台推送CI变化状态
     */
    @Override
    public void sendCiChangeMsg(String ciTypeId) {
        List<String> sourceCiIdList = new ArrayList<>();
        if (ciTypeId!=null && !"".equals(ciTypeId)){
            sourceCiIdList = findSourceCiIdByCiTypeId(ciTypeId);
            if (sourceCiIdList!=null && sourceCiIdList.size()>0){
                //调用MQ消息推送
                sender.sendCiChgNotice(String.join(",",sourceCiIdList),null);
            }
        }
    }


}
