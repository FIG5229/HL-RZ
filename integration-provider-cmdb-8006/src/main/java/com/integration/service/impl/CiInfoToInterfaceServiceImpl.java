package com.integration.service.impl;

import com.integration.dao.CiInfoToInterfaceDao;
import com.integration.entity.CiVersion;
import com.integration.service.CiInfoToInterfaceService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: CiInfoToInterfaceServiceImpl
 * @Author: ztl
 * @Date: 2020-09-11
 * @Version: 1.0
 * @description:为接口平台提供CI数据
 */
@Service
public class CiInfoToInterfaceServiceImpl implements CiInfoToInterfaceService {

    @Autowired
    private CiInfoToInterfaceDao ciInfoToInterfaceDao;
    /**
     * 查询CI信息
     * @return
     */
    @Override
    public List<Map<String, Object>> findCiInfo() {
        List<Map<String,String>> citypeList = ciInfoToInterfaceDao.findCiType();
        List<Map<String, Object>> returnResult = new ArrayList<>();
        if (citypeList != null && citypeList.size()>0){
            for (Map<String,String> map:citypeList) {
                if (!"".equals(map.get("MP_CI_ITEM")) && map.get("MP_CI_ITEM") != null){
                    List<Map<String, Object>> retsult = ciInfoToInterfaceDao.findCiInfo(map.get("CI_TYPE_ID"),map.get("MP_CI_ITEM"));
                    if (retsult != null && retsult.size()>0){
                        returnResult.addAll(retsult);
                    }
                }
            }
            return returnResult;
        }
        return new ArrayList<>();
    }

    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 修改CI版本
     */
    @Override
    public boolean updateCiVesion() {
        CiVersion ciVersion = new CiVersion();
        int count = ciInfoToInterfaceDao.findCiVersionCount();
        if (count>0){
            ciVersion.setCjr_id(TokenUtils.getTokenUserId());
            ciVersion.setVersion(String.valueOf(System.currentTimeMillis()));
            ciVersion.setCjsj(DateUtils.getDate());
            ciVersion.setYxbz(1);
            boolean result = ciInfoToInterfaceDao.updateCiVersion(ciVersion);
        }else {
            ciVersion.setId(SeqUtil.nextId() + "");
            ciVersion.setCjr_id(TokenUtils.getTokenUserId());
            ciVersion.setVersion(String.valueOf(System.currentTimeMillis()));
            ciVersion.setCjsj(DateUtils.getDate());
            ciVersion.setYxbz(1);
            boolean result = ciInfoToInterfaceDao.insertCiVersion(ciVersion);
        }
        return true;
    }
}
