package com.integration.service.impl;

import com.integration.dao.CiRltLineDao;
import com.integration.dao.DataRelDao;
import com.integration.dao.InfoDao;
import com.integration.dao.TypeDao;
import com.integration.entity.DataRel;
import com.integration.entity.EmvRequestMessage;
import com.integration.entity.Info;
import com.integration.entity.Type;
import com.integration.generator.entity.IomCiRltLine;
import com.integration.service.CiViewRelationService;
import com.integration.utils.BaseRelUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiViewRelationServiceImpl
 * @Author: sgh
 * @Description: CI视图关系Impl
 * @Date: 2019/10/17 15:52
 * @Version: 1.0
 */
@Transactional
@Service
public class CiViewRelationServiceImpl implements CiViewRelationService {

    @Resource
    private DataRelDao dataRelDao;
    @Autowired
    private InfoDao infoDao;
    @Resource
    private CiRltLineDao ciRltLineDao;
    @Autowired
    private TypeDao typeDao;
    /**
     * @Method findRelByAll
     * @Author sgh
     * @Version 1.0
     * @Description 根据多个属性查询列表
     * @Return java.util.List<com.integration.entity.DataRel>
     * @Exception
     * @Date 2019/10/17
     * @Param [emvRequestMessage, dataRel]
     */
    @Override
    public Map<String, Object> findRelByAll(EmvRequestMessage emvRequestMessage, DataRel dataRel) {
        //调用查询的方法
        BaseRelUtil relUtil = new BaseRelUtil("source_ci_id", "target_ci_id") {
            @Override
            public Object getDate(List<String> ids,List<String> ciCodes) {
                String domainId = TokenUtils.getTokenDataDomainId();
                //判断是否需要查询朋友圈
                if (emvRequestMessage.getYesOrNo() != null && emvRequestMessage.getYesOrNo() == 0) {
                    IomCiRltLine iomCiRltLine = new IomCiRltLine();
                    //根据CI查询CI大类
                    String typeCI = infoDao.findInfoById(ids.get(0),domainId).getCi_type_id();
                    iomCiRltLine.setStartTypeId(typeCI);
                    iomCiRltLine.setRuleId(emvRequestMessage.getRuleId());
                    //根据大类ID查询朋友圈
                    List<IomCiRltLine> ciRltLines = ciRltLineDao.findByStartTypeId(iomCiRltLine);
                    //遍历
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer stringBuffer1 = new StringBuffer();
                    for (IomCiRltLine iomCiRltLine1 : ciRltLines) {
                        stringBuffer.append(iomCiRltLine1.getRltId() + ",");
                        stringBuffer1.append(iomCiRltLine1.getEndTypeId() + ",");
                    }
                    emvRequestMessage.setTypeId(stringBuffer.toString());
                    dataRel.setRel_id(stringBuffer1.toString());
                }
                //封装参数
                String s = String.join(",", ids);
                //判断CI指向是否为空
                if (emvRequestMessage.getPointToT() != null) {
                    //判断指向（0：上，1：下）
                    if (emvRequestMessage.getPointToT() == 0) {
                        dataRel.setSource_ci_id(s);
                    } else if (emvRequestMessage.getPointToT() == 1) {
                        dataRel.setTarget_ci_id(s);
                    } else if (emvRequestMessage.getPointToT() == 4) {
                        dataRel.setSource_ci_id(s);
                        dataRel.setTarget_ci_id(s);
                    }
                }
                if (!StringUtils.isEmpty(emvRequestMessage.getTypeId())) {
                    //判断大类指向是否为空
                    if (emvRequestMessage.getPointTo() != null) {
                        //判断指向（0：上，1：下）
                        if (emvRequestMessage.getPointTo() == 0) {
                            dataRel.setSource_type_id(emvRequestMessage.getTypeId());
                        } else if (emvRequestMessage.getPointTo() == 1) {
                            dataRel.setTarget_ci_id(emvRequestMessage.getTypeId());
                        }
                    }
                }
                List<DataRel> dataRelList = dataRelDao.findRelByAll(emvRequestMessage, dataRel);
                return dataRelList;
            }
        };
        return relUtil.getData(emvRequestMessage.getCiId(),emvRequestMessage.getCiId());
    }

    /**
     * @Method findCIViewRCA
     * @Author sgh
     * @Version 1.0
     * @Description 通过ci获取视图关系告警
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Exception
     * @Date 2019/10/21
     * @Param [emvRequestMessage]
     */
    @Override
    public Map<String, Object> findCIViewRCA(EmvRequestMessage emvRequestMessage) {
       //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        Map map = new HashMap();
        //查询当前的CI信息
        Info info = infoDao.findInfoById(emvRequestMessage.getCiId(),domainId);
        //获取CI大类信息
        Type type = typeDao.findTypeById(info.getCi_type_id(),domainId);
        //调用查询的方法
        BaseRelUtil relUtil = new BaseRelUtil("source_ci_id", "target_ci_id",1) {
            @Override
            public Object getDate(List<String> ids,List<String> ciCodes) {
                DataRel dataRel = new DataRel();
                dataRel.setSource_ci_id(emvRequestMessage.getCiId());
                dataRel.setTarget_ci_id(emvRequestMessage.getCiId());
                return dataRelDao.findRelByAll(emvRequestMessage, dataRel);
            }
        };
        Object dataRelList = relUtil.getData(emvRequestMessage.getCiId(),emvRequestMessage.getCiId()).get("rels");
        //封装MAP
        map.put("info", info);
        map.put("type", type);
        map.put("dataRelList", dataRelList);
        return map;
    }
}
