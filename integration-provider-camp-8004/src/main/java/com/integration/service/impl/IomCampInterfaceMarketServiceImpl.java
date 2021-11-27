package com.integration.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.dao.IomCampInterfaceMarketMapper;
import com.integration.entity.IomCampInterfaceMarket;
import com.integration.entity.PageResult;
import com.integration.service.IomCampInterfaceMarketService;
import com.integration.utils.DataUtils;
import com.integration.utils.MD5Utils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName IomCampInterfaceMarketServiceImpl
 * @Description //接口市场实现
 * @Author zhangfeng
 * @Date 2020/12/7 14:12
 * @Version 1.0
 **/
@Service
public class IomCampInterfaceMarketServiceImpl implements IomCampInterfaceMarketService {

    @Resource
    private IomCampInterfaceMarketMapper iomCampInterfaceMarketMapper;

    @Value("${iomplatform.interfaceMarket:}")
    private String interfaceMarketUri;

    @Override
    public PageResult save(IomCampInterfaceMarket iomCampInterfaceMarket) {

        PageResult pg = validateSave(iomCampInterfaceMarket);
        if(!pg.isReturnBoolean()){
            return pg;
        }
        iomCampInterfaceMarket.setId(SeqUtil.nextId().toString());
        String cjrId = TokenUtils.getTokenUserId();
        iomCampInterfaceMarket.setCjrId(cjrId);
        iomCampInterfaceMarket.setCjsj(new Date());
        iomCampInterfaceMarket.setYxbz(1);
        String password = iomCampInterfaceMarket.getPassword();
        iomCampInterfaceMarket.setPassword(MD5Utils.encryptPassword(password,password));

        iomCampInterfaceMarketMapper.insert(iomCampInterfaceMarket);
        return DataUtils.returnPr(true, "保存成功");
    }


    @Override
    public PageResult update(IomCampInterfaceMarket iomCampInterfaceMarket) {

        iomCampInterfaceMarket.setPassword(null);
        PageResult pg = validateUpdate(iomCampInterfaceMarket);
        if(!pg.isReturnBoolean()){
            return pg;
        }


        String cjrId = TokenUtils.getTokenUserId();
        iomCampInterfaceMarket.setXgrId(cjrId);
        iomCampInterfaceMarket.setXgsj(new Date());

        iomCampInterfaceMarketMapper.updateByPrimaryKeySelective(iomCampInterfaceMarket);
        return DataUtils.returnPr(true,"修改成功");
    }

    @Override
    public PageResult updatePass(IomCampInterfaceMarket iomCampInterfaceMarket) {
        if(iomCampInterfaceMarket == null){
            return DataUtils.returnPr(false, "参数错误！");
        }
        if(iomCampInterfaceMarket.getId() == null){
            return DataUtils.returnPr(false, "参数错误，没有id！");
        }
        if(StringUtils.isEmpty(iomCampInterfaceMarket.getPassword()) || iomCampInterfaceMarket.getPassword().length() > 20){
            return DataUtils.returnPr(false, "参数错误，密码超出长度限制！");
        }
        String password = iomCampInterfaceMarket.getPassword();
        iomCampInterfaceMarket.setPassword(MD5Utils.encryptPassword(password,password));


        iomCampInterfaceMarketMapper.updateByPrimaryKeySelective(iomCampInterfaceMarket);

        return DataUtils.returnPr(true,"修改密码");
    }

    @Override
    public PageResult deleteById(String id) {
        if(StringUtils.isEmpty(id)){
            return DataUtils.returnPr(false,"参数错误");
        }
        iomCampInterfaceMarketMapper.deleteByPrimaryKey(id);
        return DataUtils.returnPr(true,"删除成功");
    }

    @Override
    public PageResult list(IomCampInterfaceMarket iomCampInterfaceMarket, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<IomCampInterfaceMarket> interfaceMarketList = iomCampInterfaceMarketMapper.selectList(iomCampInterfaceMarket);
        PageInfo pageInfo = new PageInfo(interfaceMarketList);
        return DataUtils.returnPr(pageInfo);
    }

    @Override
    public PageResult list(IomCampInterfaceMarket iomCampInterfaceMarket) {
        List<IomCampInterfaceMarket> interfaceMarketList = iomCampInterfaceMarketMapper.selectList(iomCampInterfaceMarket);
        return DataUtils.returnPr(interfaceMarketList,"获取列表成功");
    }

    @Override
    public PageResult info(String id) {
        if(StringUtils.isEmpty(id)){
            return DataUtils.returnPr(false, "参数错误！");
        }
        IomCampInterfaceMarket interfaceMarket = iomCampInterfaceMarketMapper.selectByPrimaryKey(id);
        if(interfaceMarket == null){
            return DataUtils.returnPr(false, "参数错误，没有此记录！");
        }

        IomCampInterfaceMarket.InterfaceType interfaceType = interfaceMarket.getInterfaceType();
        interfaceMarket.setInterfaceUrlPrefix(interfaceMarketUri + interfaceType.getPrefix());
        return DataUtils.returnPr(interfaceMarket,"获取详情成功");
    }

    private PageResult validateSave(IomCampInterfaceMarket iomCampInterfaceMarket){
        if(iomCampInterfaceMarket == null){
            return DataUtils.returnPr(false, "参数错误！");
        }

        if(iomCampInterfaceMarket.getInterfaceType() == null){
            return DataUtils.returnPr(false, "参数错误，接口类型不合法！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getClientName()) || iomCampInterfaceMarket.getClientName().length() > 20){
            return DataUtils.returnPr(false, "参数错误，名称不合法！");
        }

        if(StringUtils.isNotEmpty(iomCampInterfaceMarket.getClientDesc()) && iomCampInterfaceMarket.getClientDesc().length() > 50){
            return DataUtils.returnPr(false, "参数错误，描述超出长度限制！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getInterfaceUri()) || iomCampInterfaceMarket.getInterfaceUri().length() > 20){
            return DataUtils.returnPr(false, "参数错误，接口uri不合法！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getUsername()) || iomCampInterfaceMarket.getUsername().length() > 20){
            return DataUtils.returnPr(false, "参数错误，用户名超出长度限制！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getPassword()) || iomCampInterfaceMarket.getPassword().length() > 20){
            return DataUtils.returnPr(false, "参数错误，密码超出长度限制！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getInterfaceParamJson())){
            return DataUtils.returnPr(false, "参数错误！");
        }

        IomCampInterfaceMarket temp = new IomCampInterfaceMarket();
        temp.setInterfaceUriLike(iomCampInterfaceMarket.getInterfaceUri());
        List<IomCampInterfaceMarket> interfaceMarketList = iomCampInterfaceMarketMapper.selectList(temp);
        if(interfaceMarketList.size() > 0){
            return DataUtils.returnPr(false, "参数错误，接口url重复！");
        }


        return DataUtils.returnPr(true);
    }


    private PageResult validateUpdate(IomCampInterfaceMarket iomCampInterfaceMarket){
        if(iomCampInterfaceMarket == null){
            return DataUtils.returnPr(false, "参数错误！");
        }
        if(iomCampInterfaceMarket.getInterfaceType() == null){
            return DataUtils.returnPr(false, "参数错误，接口类型不合法！");
        }
        if(iomCampInterfaceMarket.getId() == null){
            return DataUtils.returnPr(false, "参数错误，没有id！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getClientName()) || iomCampInterfaceMarket.getClientName().length() > 20){
            return DataUtils.returnPr(false, "参数错误，名称不合法！");
        }

        if(StringUtils.isNotEmpty(iomCampInterfaceMarket.getClientDesc()) && iomCampInterfaceMarket.getClientDesc().length() > 50){
            return DataUtils.returnPr(false, "参数错误，描述超出长度限制！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getInterfaceUri()) || iomCampInterfaceMarket.getInterfaceUri().length() > 20){
            return DataUtils.returnPr(false, "参数错误，接口uri不合法！");
        }
        if(StringUtils.isNotEmpty(iomCampInterfaceMarket.getUsername()) && iomCampInterfaceMarket.getUsername().length() > 20){
            return DataUtils.returnPr(false, "参数错误，用户名超出长度限制！");
        }



        IomCampInterfaceMarket temp = new IomCampInterfaceMarket();
        temp.setInterfaceUriLike(iomCampInterfaceMarket.getInterfaceUri());
        List<IomCampInterfaceMarket> interfaceMarketList = iomCampInterfaceMarketMapper.selectList(temp);
        if(interfaceMarketList.size() > 0){
            if(iomCampInterfaceMarket.getId().compareTo(interfaceMarketList.get(0).getId()) != 0){
                return DataUtils.returnPr(false, "参数错误，接口url重复！");
            }
        }


        return DataUtils.returnPr(true);
    }


    @Override
    public PageResult selectOpenApi(IomCampInterfaceMarket record) {

        PageResult pg = validateOpenApi(record);
        if(!pg.isReturnBoolean()){
            return pg;
        }
        record.setPassword(MD5Utils.encryptPassword(record.getPassword(), record.getPassword()));

        IomCampInterfaceMarket iomCampInterfaceMarket = iomCampInterfaceMarketMapper.selectOpenApi(record);
        if(iomCampInterfaceMarket == null ){
            return DataUtils.returnPr(false, "没有对应记录！");
        }

        return DataUtils.returnPr(iomCampInterfaceMarket);
    }

    private PageResult validateOpenApi(IomCampInterfaceMarket iomCampInterfaceMarket){
        if(iomCampInterfaceMarket == null){
            return DataUtils.returnPr(false, "参数错误！");
        }
        if(iomCampInterfaceMarket.getInterfaceType() == null){
            return DataUtils.returnPr(false, "参数错误，名称不合法！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getInterfaceUri())){
            return DataUtils.returnPr(false, "参数错误，接口uri不合法！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getUsername())){
            return DataUtils.returnPr(false, "参数错误，用户名不合法！");
        }

        if(StringUtils.isEmpty(iomCampInterfaceMarket.getPassword())){
            return DataUtils.returnPr(false, "参数错误，密码不合法！");
        }

        return DataUtils.returnPr(true);
    }
}
