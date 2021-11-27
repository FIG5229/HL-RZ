package com.integration.service;

import com.integration.entity.PageResult;
import com.integration.entity.SgccUser;


/**
 * @ClassName SgccUserService
 * @Description //国网用户服务
 * @Author zhangfeng
 * @Date 2021/9/6 16:50
 * @Version 1.0
 **/
public interface SgccUserService {

    PageResult deleteByPrimaryKey(String userId);

    PageResult insert(SgccUser record);

    PageResult selectByPrimaryKey(String userId);

    PageResult updateByPrimaryKey(SgccUser record);

    PageResult listPage(SgccUser record, Integer pageSize, Integer pageNum);

    PageResult loginByUserInfo(SgccUser record);


}
