package com.integration.service.impl;

import com.integration.dao.CiKpiDao;
import com.integration.dao.CiKpiTpIItemDao;
import com.integration.dao.CiKpiTplDao;
import com.integration.entity.*;
import com.integration.service.CiKpiTplService;
import com.integration.service.TypeService;
import com.integration.utils.DateUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-14 03:49:01
 */
@Transactional
@Service
public class CiKpiTplServiceImpl implements CiKpiTplService {

    @Autowired
    private CiKpiTplDao iCiKpiTplDao;

    @Autowired
    private CiKpiTpIItemDao ciKpiTpIItemDao;

    @Autowired
    CiKpiDao ciKpiDao;

    @Autowired
    TypeService typeService;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<CiKpiTplInfo> getAllPage(HashMap<Object, Object> params) {

        return iCiKpiTplDao.getAllPage(params);
    }

    /**
     * 查询总数
     *
     * @param params
     * @return
     */
    @Override
    public int getAllCount(HashMap<Object, Object> params) {

        return iCiKpiTplDao.getAllCount(params);
    }

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Override
    public List<CiKpiTplInfo> getAllList(HashMap<Object, Object> params) {

        return iCiKpiTplDao.getAllList(params);
    }

    /**
     * 查询单条
     *
     * @param params
     * @return
     */
    @Override
    public CiKpiTplInfo getInfo(HashMap<Object, Object> params) {
        return iCiKpiTplDao.getInfo(params);
    }

    /**
     * 修改单条记录
     *
     * @param info
     */
    @Override
    public int updateInfo(CiKpiTplInfo info, String[] tplIdArr, String[] objIdArr, HttpServletRequest request) {
        //修改模板
        int i = iCiKpiTplDao.updateInfo(info);
        //删除指标和对象
        int i1 = ciKpiTpIItemDao.deleteInfo(info.getId());
        //添加指标和对象
        //新增指标组
        int i2 = 0;
        for (String kpiId : tplIdArr) {
            CiKpiTpIItemInfo tplItem = new CiKpiTpIItemInfo();
            //指标组id
            tplItem.setId(SeqUtil.nextId() + "");
            //模板id
            tplItem.setTpl_id(info.getId());
            //对象类型
            tplItem.setObj_type(1);
            //对象id
            tplItem.setObj_id(kpiId);
            //对象的名称
            CiKpiInfo info1 = ciKpiDao.getInfo(kpiId);
            tplItem.setObj_name(info1.getKpi_name());
            //创建时间
            tplItem.setCjsj(DateUtils.getDate());
            //有效标志
            tplItem.setYxbz(1);
            i1 = ciKpiTpIItemDao.insertInfo(tplItem);
        }

        //新增对象组
        int i3 = 0;
        for (String objId : objIdArr) {
            CiKpiTpIItemInfo tplItem = new CiKpiTpIItemInfo();
            //指标组id
            tplItem.setId(SeqUtil.nextId() + "");
            //模板id
            tplItem.setTpl_id(info.getId());
            //对象类型
            tplItem.setObj_type(2);
            //对象id
            tplItem.setObj_id(objId);
            //对象的名称
            Type type = typeService.findTypeById(objId);
            tplItem.setObj_name(type.getCi_type_mc());
            //创建时间
            tplItem.setCjsj(DateUtils.getDate());
            //有效标志
            tplItem.setYxbz(1);
            i2 = ciKpiTpIItemDao.insertInfo(tplItem);
        }
        if (i > 0 && i1 > 0 && i2 > 0 && i3 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 新增单条记录
     *
     * @param info
     */
    @Override
    public int insertInfo(CiKpiTplInfo info, String[] kpiIdArr, String[] objIdArr, HttpServletRequest request) {
        //模板id
        info.setId(SeqUtil.nextId() + "");
        //创建人
        String userId = TokenUtils.getTokenUserId();
        info.setCjr_id(userId);
        //创建时间
        String time = DateUtils.getDate();
        info.setCjsj(time);
        //有效标志
        info.setYxbz(1);
        //新增模板
        int i = iCiKpiTplDao.insertInfo(info);

        //新增指标组
        int i1 = 0;
        for (String kpiId : kpiIdArr) {
            CiKpiTpIItemInfo tplItem = new CiKpiTpIItemInfo();
            //指标组id
            tplItem.setId(SeqUtil.nextId() + "");
            //模板id
            tplItem.setTpl_id(info.getId());
            //对象类型
            tplItem.setObj_type(1);
            //对象id
            tplItem.setObj_id(kpiId);
            //对象的名称
            CiKpiInfo info1 = ciKpiDao.getInfo(kpiId);
            tplItem.setObj_name(info1.getKpi_name());
            //创建时间
            time = DateUtils.getDate();
            tplItem.setCjsj(time);
            //有效标志
            tplItem.setYxbz(1);
            i1 = ciKpiTpIItemDao.insertInfo(tplItem);
        }

        //新增对象组
        int i2 = 0;
        for (String objId : objIdArr) {
            CiKpiTpIItemInfo tplItem = new CiKpiTpIItemInfo();
            //指标组id
            tplItem.setId(SeqUtil.nextId() + "");
            //模板id
            tplItem.setTpl_id(info.getId());
            //对象类型
            tplItem.setObj_type(2);
            //对象id
            tplItem.setObj_id(objId);
            //对象的名称
            Type type = typeService.findTypeById(objId);
            tplItem.setObj_name(type.getCi_type_mc());
            //创建时间
            time = DateUtils.getDate();
            tplItem.setCjsj(time);
            //有效标志
            tplItem.setYxbz(1);
            i2 = ciKpiTpIItemDao.insertInfo(tplItem);
        }
        if (i > 0 && i1 > 0 && i2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取模板和对象的组合体
     *
     * @return
     */
    @Override
    public List<Tpl_Item> getTpl_Item() {
        List<Tpl_Item> itemList = new ArrayList<>();
        //查询所有模板
        List<CiKpiTplInfo> list = iCiKpiTplDao.findAll();
        for (CiKpiTplInfo ciKpiTplInfo : list) {
            Tpl_Item tpl_item = new Tpl_Item();
            tpl_item.setId(ciKpiTplInfo.getId());
            tpl_item.setTpl_name(ciKpiTplInfo.getTpl_name());
            tpl_item.setTpl_alias(ciKpiTplInfo.getTpl_alias());
            tpl_item.setTpl_desc(ciKpiTplInfo.getTpl_desc());
            //通过模板id查询对象组
            List<CiKpiTpIItemInfo> tplById = ciKpiTpIItemDao.getTplById(ciKpiTplInfo.getId());
            List<CiKpiTpIItemInfo> kpiList = new ArrayList<>();
            List<CiKpiTpIItemInfo> objList = new ArrayList<>();
            for (CiKpiTpIItemInfo ciKpiTpIItemInfo : tplById) {
                //指标
                if (ciKpiTpIItemInfo.getObj_type() == 1){
                    kpiList.add(ciKpiTpIItemInfo);
                }
                //对象
                if (ciKpiTpIItemInfo.getObj_type() == 2){
                    objList.add(ciKpiTpIItemInfo);
                }
            }
            tpl_item.setKpiList(kpiList);
            tpl_item.setObjList(objList);
            itemList.add(tpl_item);
        }
        return itemList;
    }

    /**
     * 删除单条记录
     *
     * @param tplId
     */
    @Override
    public int deleteInfo(String tplId) {
        //删除模型
        int i = iCiKpiTplDao.deleteInfo(tplId);
        //删除模型对象
        int i1 = ciKpiTpIItemDao.deleteInfo(tplId);
        if (i > 0 && i1 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}